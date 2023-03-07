package fr.openium.mastertp1_2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "Meme")
data class Meme (
    @PrimaryKey   val id: Int,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "url") val url: String? = null
)
