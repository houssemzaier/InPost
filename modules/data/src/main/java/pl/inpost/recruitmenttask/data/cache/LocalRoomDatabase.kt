package pl.inpost.recruitmenttask.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import pl.inpost.recruitmenttask.data.cache.dao.ShipmentDao
import pl.inpost.recruitmenttask.data.cache.model.ShipmentTable

@Database(
    entities = [ShipmentTable::class],
    version = 1,
    exportSchema = true,
)
abstract class LocalRoomDatabase : RoomDatabase() {

    abstract fun shipmentDao(): ShipmentDao

    companion object {
        @Volatile
        private var INSTANCE: LocalRoomDatabase? = null

        internal fun getDatabase(context: Context): LocalRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context,
                    LocalRoomDatabase::class.java,
                    "OfflineCachingDB",
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}
