package es.alvarorodriguez.sqliteprueba.data.local

import androidx.room.*
import es.alvarorodriguez.sqliteprueba.data.model.CarEntity

@Dao
interface CarsDao {

    @Query(value = "SELECT * FROM CarEntity")
    suspend fun getCars(): List<CarEntity>

    @Query("DELETE FROM CarEntity WHERE id = :id")
    suspend fun deleteCarById(id: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCar(car: CarEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCar(car: CarEntity)
}