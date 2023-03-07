package fr.openium.mastertp1_2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.openium.mastertp1_2.Dao.MemeDao
import fr.openium.mastertp1_2.model.Meme

@Database(entities = [Meme::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun memeDao(): MemeDao

}