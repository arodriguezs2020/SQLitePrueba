package es.alvarorodriguez.sqliteprueba.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.alvarorodriguez.sqliteprueba.data.model.CarEntity

@Database(entities = [CarEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun carDao(): CarsDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "car_table"
            ).build()
            return INSTANCE!!
        }
    }
}