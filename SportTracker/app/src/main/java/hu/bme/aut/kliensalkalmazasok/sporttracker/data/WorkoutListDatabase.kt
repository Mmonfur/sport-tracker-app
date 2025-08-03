package hu.bme.aut.kliensalkalmazasok.sporttracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WorkoutItem::class], version = 1)
@TypeConverters(value = [WorkoutItem.Type::class])
abstract class WorkoutListDatabase : RoomDatabase() {
    abstract fun workoutItemDao(): WorkoutItemDao

    companion object {
        fun getDatabase(applicationContext: Context): WorkoutListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                WorkoutListDatabase::class.java,
                "workout-list"
            ).build()
        }
    }
}
