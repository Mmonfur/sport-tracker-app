package hu.bme.aut.kliensalkalmazasok.sporttracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.kliensalkalmazasok.sporttracker.R
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutListDatabase
import hu.bme.aut.kliensalkalmazasok.sporttracker.databinding.FragmentChartBinding
import kotlin.concurrent.thread

class ChartFragment : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChartBinding.inflate(inflater, container, false)

        loadChart(true)

        return binding.root
    }

    private fun loadChart(isCompleted: Boolean) {

        thread {
            var runningDuration = 0
            var weightliftingDuration = 0
            var swimmingDuration = 0

            for (item in WorkoutListDatabase.getDatabase(requireContext()).workoutItemDao()
                .getItemByIsCompleted(isCompleted)) {
                when (item.type) {
                    WorkoutItem.Type.RUNNING -> runningDuration += item.duration
                    WorkoutItem.Type.WEIGHTLIFTING -> weightliftingDuration += item.duration
                    WorkoutItem.Type.SWIMMING -> swimmingDuration += item.duration
                }
            }
            requireActivity().runOnUiThread {
                setupChart(runningDuration.toFloat(), weightliftingDuration.toFloat(), swimmingDuration.toFloat())

                if (isCompleted)
                    binding.chartHoliday.centerText = getString(R.string.label_completed_items)
                else
                    binding.chartHoliday.centerText = getString(R.string.label_not_completed_items)

            }
        }
    }

    private fun setupChart(
        runningDuration: Float,
        weightliftingDuration: Float,
        swimmingDuration: Float
    ) {
        val entries = mutableListOf<PieEntry>()

        if (runningDuration > 0f) {
            entries.add(PieEntry(runningDuration, "Running"))
        }

        if (weightliftingDuration > 0f) {
            entries.add(PieEntry(weightliftingDuration, "Weightlifting"))
        }

        if (swimmingDuration > 0f) {
            entries.add(PieEntry(swimmingDuration, "Swimming"))
        }

        val dataSet = PieDataSet(entries, getString(R.string.label_workout_items))
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = PieData(dataSet)
        binding.chartHoliday.data = data
        binding.chartHoliday.invalidate()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MenuHost).addMenuProvider(
            myMenuProvider,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    private val myMenuProvider = object : MenuProvider {

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_chart, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.completedButton -> {
                    loadChart(true)
                    true
                }

                R.id.notCompletedButton -> {
                    loadChart(false)
                    true
                }

                else -> false
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
