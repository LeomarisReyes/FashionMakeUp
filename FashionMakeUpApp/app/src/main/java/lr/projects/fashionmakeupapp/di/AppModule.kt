package lr.projects.fashionmakeupapp.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lr.projects.fashionmakeupapp.BuildConfig
import lr.projects.fashionmakeupapp.data.local.ProductDao
import lr.projects.fashionmakeupapp.data.local.ProductDatabase
import lr.projects.fashionmakeupapp.data.remote.network.MakeUpService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Reusable
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Reusable
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Reusable
    @Provides
    fun provideMakeUpApi(retrofit: Retrofit): MakeUpService {
        return retrofit.create(MakeUpService::class.java)
    }

    @Singleton
    @Provides
    fun provideProductDatabase(@ApplicationContext appContext: Context): ProductDatabase {
        return Room.databaseBuilder(
            appContext,
            ProductDatabase::class.java,
            "product_db"
        ).build()
    }

    @Reusable
    @Provides
    fun provideProductDao(db: ProductDatabase) : ProductDao {
        return db.dao
    }
}