package pt.baptista.android.training.hangmangame

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.Date
/*
Datatypes In SQLite:

NULL. The value is a NULL value.
INTEGER. The value is a signed integer, stored in 0, 1, 2, 3, 4, 6, or 8 bytes depending on the magnitude of the value.
REAL. The value is a floating point value, stored as an 8-byte IEEE floating point number.
TEXT. The value is a text string, stored using the database encoding (UTF-8, UTF-16BE or UTF-16LE).
BLOB. The value is a blob of data, stored exactly as it was input.
*/
@Entity(tableName = "history")
@TypeConverters(Converters::class)
data class HangmanHistoryItem(var word: String, var right: String, var wrong: String, var date: Date) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface HangmanHistoryDao {
    @Insert
    fun insertItem(item: HangmanHistoryItem)

    @Query("DELETE from history")
    fun deleteAllItems()

    @Query("SELECT * from history ORDER BY date DESC")
    fun getAllItems(): LiveData<List<HangmanHistoryItem>>
}

@Database(entities = [HangmanHistoryItem::class], version = 2)
abstract class HangmanHistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HangmanHistoryDao
}

class Converters{
    @TypeConverter
    fun toDate(timestamp:Long) = Date(timestamp)
    @TypeConverter
    fun fromDate(date:Date) = date.time
}