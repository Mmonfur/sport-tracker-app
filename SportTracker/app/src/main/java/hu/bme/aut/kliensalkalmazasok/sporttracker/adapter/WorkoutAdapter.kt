package hu.bme.aut.kliensalkalmazasok.sporttracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.kliensalkalmazasok.sporttracker.R
import hu.bme.aut.kliensalkalmazasok.sporttracker.data.WorkoutItem
import hu.bme.aut.kliensalkalmazasok.sporttracker.databinding.ItemWorkoutListBinding
import hu.bme.aut.kliensalkalmazasok.sporttracker.fragment.WorkoutListFragment
import hu.bme.aut.kliensalkalmazasok.sporttracker.util.toDateString

class WorkoutAdapter(private val listener: WorkoutListFragment) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private val items = mutableListOf<WorkoutItem>()
    // adott lista elemet megjelenítő View + ViewHolder létrehozása
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WorkoutViewHolder(
        ItemWorkoutListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    // adatok megjelenítésének logikája
    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workoutItem = items[position]

        holder.binding.ivIcon.setImageResource(getImageResource(workoutItem.type))
        holder.binding.cbIsCompleted.isChecked = workoutItem.isCompleted
        holder.binding.tvDate.text = workoutItem.date.toDateString() // <-- Szép dátum formázás
        holder.binding.tvDescription.text = workoutItem.description
        holder.binding.tvType.text = workoutItem.type.name
        holder.binding.tvDuration.text = "${workoutItem.duration} Mins"

        holder.binding.cbIsCompleted.setOnCheckedChangeListener { _, isChecked ->
            workoutItem.isCompleted = isChecked
            listener.onItemChanged(workoutItem)
        }

        holder.binding.ibRemove.setOnClickListener {
            val item = items[holder.adapterPosition]
            listener.onItemDeleted(item)
        }

        holder.binding.ibEdit.setOnClickListener {
            val item = items[holder.adapterPosition]
            listener.onItemClicked(item)
        }
    }

    @DrawableRes
    private fun getImageResource(type: WorkoutItem.Type): Int {
        return when (type) {
            WorkoutItem.Type.RUNNING -> R.drawable.run
            WorkoutItem.Type.WEIGHTLIFTING -> R.drawable.dumbbell
            WorkoutItem.Type.SWIMMING -> R.drawable.swimming
        }
    }

    fun addItem(item: WorkoutItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(workoutItems: List<WorkoutItem>) {
        items.clear()
        items.addAll(workoutItems)
        notifyDataSetChanged()
    }

    fun deleteItem(item: WorkoutItem) {
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun getItemCount(): Int = items.size

    interface WorkoutItemClickListener {
        fun onItemChanged(item: WorkoutItem)
        fun onItemDeleted(item: WorkoutItem)
        fun onItemClicked(item: WorkoutItem)
    }

    inner class WorkoutViewHolder(val binding: ItemWorkoutListBinding) :
        RecyclerView.ViewHolder(binding.root)
}
