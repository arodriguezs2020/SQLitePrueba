package es.alvarorodriguez.sqliteprueba.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import es.alvarorodriguez.sqliteprueba.core.Result
import es.alvarorodriguez.sqliteprueba.data.model.CarEntity
import es.alvarorodriguez.sqliteprueba.repository.CarRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class CarViewModel(private val repo: CarRepository): ViewModel() {

    fun getCars() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Result.Loading())
        kotlin.runCatching {
            repo.getCars()
        }.onSuccess { listCars ->
            emit(Result.Success(listCars))
        }.onFailure { error ->
            emit(Result.Failure(Exception(error.message)))
        }
    }

    fun deleteCarById(id: Int) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.deleteCarById(id)))
        }catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun updateCar(car: CarEntity) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.updateCar(car)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun saveCar(car: CarEntity) = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.saveCar(car)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class CarViewModelFactory(private val repo: CarRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CarRepository::class.java).newInstance(repo)
    }
}