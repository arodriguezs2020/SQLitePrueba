package es.alvarorodriguez.sqliteprueba.data.local

import es.alvarorodriguez.sqliteprueba.data.model.CarEntity

class LocalCarDataSource(private val carsDao: CarsDao) {

    suspend fun getCars(): List<CarEntity> = carsDao.getCars()
    suspend fun deleteCarById(id: Int) = carsDao.deleteCarById(id)
    suspend fun updateCar(car: CarEntity) = carsDao.updateCar(car)
    suspend fun saveCar(car: CarEntity) = carsDao.saveCar(car)
}