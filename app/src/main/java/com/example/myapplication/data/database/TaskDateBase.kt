package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TaskDateBase: RoomDatabase(){
    abstract fun taskDao(): TaskDao

    companion object{
        @Volatile
        private var INSTANCE: TaskDateBase? = null

        fun getInstance(context: Context): TaskDateBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDateBase::class.java,
                    "data_base.db"
                ).build()
                INSTANCE = instance
                instance

            }
        }
    }
}