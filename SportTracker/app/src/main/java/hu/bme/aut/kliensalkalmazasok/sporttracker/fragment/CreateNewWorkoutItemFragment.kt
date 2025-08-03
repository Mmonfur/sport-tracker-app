package hu.bme.aut.kliensalkalmazasok.sporttracker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.kliensalkalmazasok.sporttracker.R
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem
import hu.bme.aut.kliensalkalmazasok.sporttracker.databinding.FragmentCreateNewWorkoutItemBinding
import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateNewWorkoutItemFragment : Fragment() {

    private var _binding: FragmentCreateNewWorkoutItemBinding? = null
    private val binding get() = _binding!!

    private var selectedDateInMillis: Long = System.currentTimeMillis() // alapértelmezett érték

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewWorkoutItemBinding.inflate(inflater, container, false)

        binding.spType.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )

        setupDatePicker()

        binding.btnCreate.setOnClickListener {
            if (isValid()) {
                binding.tilDate.error = null // ha előzőleg volt hiba, töröljük
                findNavController().run {
                    previousBackStackEntry?.savedStateHandle?.set(
                        KEY_NEW_WORKOUT_ITEM,
                        getWorkoutItem()
                    )
                    navigateUp()
                }
            } else {
                binding.tilDate.error = getString(R.string.date_required)
            }
        }


        return binding.root
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()

        binding.etDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDateInMillis = calendar.timeInMillis

                    // Dátum szövegként a mezőbe
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    binding.etDate.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun isValid(): Boolean {
        binding.tilDate.error = null  // először töröljük az esetleges hibát

        if (binding.etDate.text.isNullOrEmpty()) {
            binding.tilDate.error = getString(R.string.date_required)
            return false
        }

        return true
    }

    //Új WorkoutItem összeállítása
    private fun getWorkoutItem() = WorkoutItem(
        date = selectedDateInMillis,
        description = binding.etDescription.text.toString(),
        duration = binding.etDuration.text.toString().toIntOrNull() ?: 0,
        type = WorkoutItem.Type.getByOrdinal(binding.spType.selectedItemPosition)
            ?: WorkoutItem.Type.RUNNING,
        isCompleted = binding.cbCompleted.isChecked
    )

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val KEY_NEW_WORKOUT_ITEM = "KEY_NEW_WORKOUT_ITEM"
    }
}
