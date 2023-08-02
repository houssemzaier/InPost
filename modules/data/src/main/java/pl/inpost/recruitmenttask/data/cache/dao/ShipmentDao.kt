package pl.inpost.recruitmenttask.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.cache.model.ShipmentTable

@Dao
interface ShipmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: List<ShipmentTable>)

    @Query("DELETE FROM shipment_table")
    suspend fun deleteAll()

    @get:Query("SELECT * FROM shipment_table")
    val all: Flow<List<ShipmentTable>>

    @Query("SELECT * FROM shipment_table")
    suspend fun getAll(): List<ShipmentTable>

    @Query("SELECT * FROM `shipment_table` where number = :number")
    fun getBy(number: String): Flow<ShipmentTable>

    @Query("DELETE FROM `shipment_table` where number = :number")
    suspend fun deleteBy(number: String)

    @Update(entity = ShipmentTable::class)
    fun update(entity: ShipmentTable)
}
