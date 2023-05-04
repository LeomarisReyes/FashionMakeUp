package lr.projects.fashionmakeupapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import lr.projects.fashionmakeupapp.model.ProductColor

class Converter {
     @TypeConverter
     fun listToJson(value: List<String>?): String? {
          return Gson().toJson(value)
     }

     @TypeConverter
     fun jsonToList(value: String): List<String>? {
          return Gson().fromJson(value, Array<String>::class.java)?.toList()
     }

     @TypeConverter
     fun listToJsonProductColor(value: List<ProductColor>?): String? {
          return Gson().toJson(value)
     }

     @TypeConverter
     fun jsonToListProductColor(value: String): List<ProductColor>? {
          return Gson().fromJson(value, Array<ProductColor>::class.java)?.toList()
     }
}