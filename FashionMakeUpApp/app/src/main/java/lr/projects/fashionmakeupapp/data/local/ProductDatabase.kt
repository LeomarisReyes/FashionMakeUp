package lr.projects.fashionmakeupapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract val dao: ProductDao
}
