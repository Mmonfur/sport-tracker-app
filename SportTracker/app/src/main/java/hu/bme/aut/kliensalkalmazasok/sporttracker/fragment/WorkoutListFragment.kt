package hu.bme.aut.kliensalkalmazasok.sporttracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.kliensalkalmazasok.sporttracker.R
import hu.bme.aut.kliensalkalmazasok.sporttracker.adapter.WorkoutAdapter
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutListDatabase
import hu.bme.aut.kliensalkalmazasok.sporttracker.databinding.FragmentWorkoutListBinding
import hu.bme.aut.kliensalkalmazasok.sporttracker.fragment.CreateNewWorkoutItemFragment.Companion.KEY_NEW_WORKOUT_ITEM
import kotlin.concurrent.thread

class WorkoutListFragment : Fragment(), WorkoutAdapter.WorkoutItemClickListener {

    private var _binding: FragmentWorkoutListBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: WorkoutListDatabase
    private lateinit var adapter: WorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)

        database = WorkoutListDatabase.getDatabase(requireContext())

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = WorkoutAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.workoutItemDao().getAll()
            requireActivity().runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: WorkoutItem) {
        thread {
            database.workoutItemDao().update(item)
            Log.d("WorkoutListFragment", "WorkoutItem update was successful")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabNewItem.setOnClickListener {
            findNavController().navigate(
                WorkoutListFragmentDirections.actionWorkoutListFragmentToCreateNewWorkoutItemFragment()
            )
        }

        binding.fabChart.setOnClickListener {
            findNavController().navigate(
                WorkoutListFragmentDirections.actionWorkoutListFragmentToChartFragment()
            )
        }

        binding.fabWeeklyStats.setOnClickListener {
            findNavController().navigate(
                WorkoutListFragmentDirections.actionWorkoutListFragmentToWeeklyStatsFragment()
            )
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.run {
            getLiveData<WorkoutItem>(KEY_NEW_WORKOUT_ITEM).observe(viewLifecycleOwner) {
                createWorkoutItem(it)
                remove<WorkoutItem>(KEY_NEW_WORKOUT_ITEM)
            }
        }

        initRecyclerView()
    }

    private fun createWorkoutItem(newItem: WorkoutItem) {
        thread {
            val insertId = database.workoutItemDao().insert(newItem)
            newItem.id = insertId
            requireActivity().runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    override fun onItemClicked(item: WorkoutItem) {
        val bundle = Bundle().apply {
            putSerializable("workout_item", item)
        }
        findNavController().navigate(R.id.action_list_to_edit, bundle)
    }

    override fun onItemDeleted(item: WorkoutItem) {
        thread {
            database.workoutItemDao().deleteItem(item)
            requireActivity().runOnUiThread {
                adapter.deleteItem(item)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
