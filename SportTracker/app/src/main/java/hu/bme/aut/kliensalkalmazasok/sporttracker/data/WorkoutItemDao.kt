package hu.bme.aut.kliensalkalmazasok.sporttracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WorkoutItemDao {
    @Query("SELECT * FROM workoutitem")
    fun getAll(): List<WorkoutItem>

    @Query("SELECT * FROM workoutitem WHERE is_completed = :isCompleted")
    fun getItemByIsCompleted(isCompleted: Boolean): List<WorkoutItem>

    @Query("SELECT * FROM workoutitem WHERE date BETWEEN :from AND :to")
    fun getItemsBetween(from: Long, to: Long): List<WorkoutItem>

    @Insert
    fun insert(workoutItems: WorkoutItem): Long

    @Update
    fun update(workoutItem: WorkoutItem)

    @Delete
    fun deleteItem(workoutItem: WorkoutItem)
}
