package hu.bme.aut.kliensalkalmazasok.sporttracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlin.jvm.JvmStatic

@Entity(tableName = "workoutitem")
data class WorkoutItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "date") var date: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "type") var type: Type,
    @ColumnInfo(name = "duration") var duration: Int,
    @ColumnInfo(name = "is_completed") var isCompleted: Boolean
) : java.io.Serializable {
    enum class Type {
        RUNNING, WEIGHTLIFTING, SWIMMING;

        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Type? {
                var ret: Type? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(type: Type): Int {
                return type.ordinal
            }
        }
    }
}
