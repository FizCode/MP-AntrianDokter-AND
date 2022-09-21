package stmik.mp.hafiz.antriandokter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import stmik.mp.hafiz.antriandokter.data.local.auth.UserDAO
import stmik.mp.hafiz.antriandokter.data.local.auth.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun userDAO(): UserDAO

    companion object {
        private const val DB_NAME = "SecondHand.db"

        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): LocalDatabase {
            return Room.databaseBuilder(context, LocalDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}