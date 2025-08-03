package hu.bme.aut.kliensalkalmazasok.sporttracker.fragment

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutListDatabase
import hu.bme.aut.kliensalkalmazasok.sporttracker.databinding.FragmentWeeklyStatsBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class WeeklyStatsFragment : Fragment() {

    private var _binding: FragmentWeeklyStatsBinding? = null
    private val binding get() = _binding!!

    private val calendarFrom = Calendar.getInstance()
    private val calendarTo = Calendar.getInstance()
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeeklyStatsBinding.inflate(inflater, container, false)

        setupDatePickers()

        calendarFrom.add(Calendar.DAY_OF_YEAR, -6)
        updateDateFields()
        loadStatsBetween(calendarFrom.timeInMillis, calendarTo.timeInMillis)

        binding.btnLoad.setOnClickListener {
            try {
                val fromDate = dateFormatter.parse(binding.etFromDate.text.toString())
                val toDate = dateFormatter.parse(binding.etToDate.text.toString())

                if (fromDate != null && toDate != null) {
                    if (fromDate.time > toDate.time) {
                        Toast.makeText(requireContext(), "A 'From' dátum nem lehet későbbi mint a 'To'.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    loadStatsBetween(fromDate.time, toDate.time)
                } else {
                    Toast.makeText(requireContext(), "Hibás dátumformátum.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Hiba történt a dátum feldolgozásakor.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun setupDatePickers() {
        binding.etFromDate.setOnClickListener {
            showDatePicker(calendarFrom) {
                updateDateFields()
            }
        }

        binding.etToDate.setOnClickListener {
            showDatePicker(calendarTo) {
                updateDateFields()
            }
        }
    }

    private fun showDatePicker(calendar: Calendar, onDateSet: () -> Unit) {
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendar.set(year, month, day)
                onDateSet()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateDateFields() {
        binding.etFromDate.setText(dateFormatter.format(calendarFrom.time))
        binding.etToDate.setText(dateFormatter.format(calendarTo.time))
    }

    private fun loadStatsBetween(fromMillis: Long, toMillis: Long) {
        thread {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = fromMillis
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val endCalendar = Calendar.getInstance().apply {
                timeInMillis = toMillis
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }

            val durations = mutableListOf<BarEntry>()
            val calories = mutableListOf<BarEntry>()
            val xLabels = mutableListOf<String>()
            var index = 0
            var totalDuration = 0
            var totalCalories = 0

            while (calendar.timeInMillis <= endCalendar.timeInMillis) {
                val dayStart = calendar.timeInMillis
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                val dayEnd = calendar.timeInMillis

                val items = WorkoutListDatabase.getDatabase(requireContext())
                    .workoutItemDao().getItemsBetween(dayStart, dayEnd)
                    .filter { it.isCompleted }

                val dailyDuration = items.sumOf { it.duration }
                val dailyCalories = items.sumOf {
                    when (it.type) {
                        WorkoutItem.Type.RUNNING -> it.duration * 10
                        WorkoutItem.Type.SWIMMING -> it.duration * 8
                        WorkoutItem.Type.WEIGHTLIFTING -> it.duration * 6
                    }
                }

                durations.add(BarEntry(index.toFloat(), dailyDuration.toFloat()))
                calories.add(BarEntry(index.toFloat(), dailyCalories.toFloat()))

                val label = SimpleDateFormat("MM.dd", Locale.getDefault()).format(dayStart)
                xLabels.add(label)

                totalDuration += dailyDuration
                totalCalories += dailyCalories
                index++
            }

            requireActivity().runOnUiThread {
                setupBarChart(durations, calories, xLabels)
                binding.tvTotalDuration.text = "Total Duration: $totalDuration minutes"
                binding.tvTotalCalories.text = "Total Calories: $totalCalories kcal"
            }
        }
    }

    private fun setupBarChart(durationEntries: List<BarEntry>, calorieEntries: List<BarEntry>, labels: List<String>) {
        val durationDataSet = BarDataSet(durationEntries, "Duration (min)").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            axisDependency = YAxis.AxisDependency.LEFT
            setDrawValues(false)
        }

        val calorieDataSet = BarDataSet(calorieEntries, "Calories (kcal)").apply {
            color = Color.RED
            valueTextColor = Color.BLACK
            axisDependency = YAxis.AxisDependency.RIGHT
            setDrawValues(false)
        }

        val barData = BarData(durationDataSet, calorieDataSet)

        val groupSpace = 0.2f
        val barSpace = 0.05f
        val barWidth = 0.35f
        barData.barWidth = barWidth

        val groupCount = labels.size
        val groupWidth = barData.getGroupWidth(groupSpace, barSpace)

        binding.barChart.apply {
            data = barData

            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(labels)
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                labelRotationAngle = -45f
                setDrawGridLines(false)
                axisMinimum = 0f
                axisMaximum = groupWidth * groupCount
            }

            axisLeft.axisMinimum = 0f
            axisRight.axisMinimum = 0f
            axisRight.isEnabled = true

            description.isEnabled = false
            legend.isEnabled = true

            barData.groupBars(0f, groupSpace, barSpace)

            // Dinamikusan állítsuk be a látható X tartományt
            if (groupCount <= 7) {
                setVisibleXRangeMaximum(7f)
            } else {
                setVisibleXRangeMaximum(groupCount.toFloat())
            }

            invalidate()
            notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}


