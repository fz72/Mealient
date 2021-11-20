package gq.kirmanak.mealient.data

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    companion object {
        @Provides
        fun createDb(@ApplicationContext context: Context): AppDb {
            return Room.databaseBuilder(context, AppDb::class.java, "app.db").build()
        }

        @Provides
        fun createSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }
    }
}