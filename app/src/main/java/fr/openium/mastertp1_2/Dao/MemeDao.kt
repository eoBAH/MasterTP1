package fr.openium.mastertp1_2.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.openium.mastertp1_2.model.Meme

@Dao
interface MemeDao {

    @Query("SELECT * FROM Meme")
    fun getAll(): List<Meme>

    @Insert
    fun insertAll(list: ArrayList<Meme>)

    @Delete
    fun delete(meme: Meme)


}