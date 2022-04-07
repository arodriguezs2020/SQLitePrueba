package es.alvarorodriguez.sqliteprueba.repository

import es.alvarorodriguez.sqliteprueba.data.local.LocalCarDataSource
import es.alvarorodriguez.sqliteprueba.data.model.CarEntity

class CarRepositoryImpl(private val dataSourceLocal: LocalCarDataSource): CarRepository {
    override suspend fun getCars(): List<CarEntity> = dataSourceLocal.getCars()
    override suspend fun deleteCarById(id: Int) = dataSourceLocal.deleteCarById(id)
    override suspend fun updateCar(car: CarEntity) = dataSourceLocal.updateCar(car)
    override suspend fun saveCar(car: CarEntity) = dataSourceLocal.saveCar(car)
}