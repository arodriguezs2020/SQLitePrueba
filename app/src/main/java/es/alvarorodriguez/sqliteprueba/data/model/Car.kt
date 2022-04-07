package es.alvarorodriguez.sqliteprueba.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class CarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    @ColumnInfo(name = "marca")
    val marca: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "precio")
    val precio: String = "",
    @ColumnInfo(name = "color")
    val color: String = "BLUE"
)