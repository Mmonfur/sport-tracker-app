package hu.bme.aut.kliensalkalmazasok.sporttracker.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.bme.aut.kliensalkalmazasok.sporttracker.R
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutListDatabase
import hu.bme.aut.kliensalkalmazasok.sporttracker.databinding.FragmentEditWorkoutItemBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.concurrent.thread
import android.widget.Toast
import hu.bme.aut.kliensalkalmazasok.sporttracker.util.toDateString

class EditWorkoutItemFragment : Fragment() {

    private var _binding: FragmentEditWorkoutItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var workoutItem: WorkoutItem
    private var selectedDateInMillis: Long = System.currentTimeMillis()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditWorkoutItemBinding.inflate(inflater, container, false)
        @Suppress("DEPRECATION")
        workoutItem = requireArguments().getSerializable(ARG_WORKOUT_ITEM) as WorkoutItem
        selectedDateInMillis = workoutItem.date

        setupSpinner()
        setupDatePicker()
        fillFields()

        binding.btnSave.setOnClickListener {
            updateItemFromFields()
            thread {
                WorkoutListDatabase.getDatabase(requireContext()).workoutItemDao().update(workoutItem)
                requireActivity().runOnUiThread {
                    Toast.makeText(context, "Workout updated", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }

        return binding.root
    }

    private fun setupSpinner() {
        binding.spType.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            WorkoutItem.Type.values().map { it.name }
        )
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance().apply { timeInMillis = selectedDateInMillis }
        binding.etDate.setText(calendar.timeInMillis.toDateString())

        binding.etDate.setOnClickListener {
            DatePickerDialog(requireContext(), { _, year, month, day ->
                calendar.set(year, month, day)
                selectedDateInMillis = calendar.timeInMillis
                binding.etDate.setText(calendar.timeInMillis.toDateString())
                updateCalories()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun fillFields() {
        binding.etDescription.setText(workoutItem.description)
        binding.etDuration.setText(workoutItem.duration.toString())
        binding.cbCompleted.isChecked = workoutItem.isCompleted
        binding.spType.setSelection(workoutItem.type.ordinal)
        updateCalories()
    }

    private fun updateItemFromFields() {
        workoutItem.date = selectedDateInMillis
        workoutItem.description = binding.etDescription.text.toString()
        workoutItem.duration = binding.etDuration.text.toString().toIntOrNull() ?: 0
        workoutItem.type = WorkoutItem.Type.getByOrdinal(binding.spType.selectedItemPosition) ?: WorkoutItem.Type.RUNNING
        workoutItem.isCompleted = binding.cbCompleted.isChecked
    }

    private fun updateCalories() {
        val duration = binding.etDuration.text.toString().toIntOrNull() ?: workoutItem.duration
        val type = WorkoutItem.Type.getByOrdinal(binding.spType.selectedItemPosition) ?: workoutItem.type
        val calories = when (type) {
            WorkoutItem.Type.RUNNING -> duration * 10
            WorkoutItem.Type.SWIMMING -> duration * 8
            WorkoutItem.Type.WEIGHTLIFTING -> duration * 6
        }
        binding.tvCalories.text = "Calories burned: $calories kcal"
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ARG_WORKOUT_ITEM = "workout_item"

        fun newInstance(item: WorkoutItem): Bundle {
            return Bundle().apply {
                putSerializable(ARG_WORKOUT_ITEM, item)
            }
        }
    }
}
