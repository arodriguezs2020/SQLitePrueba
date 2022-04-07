package es.alvarorodriguez.sqliteprueba.repository

import es.alvarorodriguez.sqliteprueba.data.model.CarEntity

interface CarRepository {
    suspend fun getCars(): List<CarEntity>
    suspend fun deleteCarById(id: Int)
    suspend fun updateCar(car: CarEntity)
    suspend fun saveCar(car: CarEntity)
}