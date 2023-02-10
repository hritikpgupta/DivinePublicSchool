package hg.divineschool.admin

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hg.divineschool.admin.data.auth.AuthRepository
import hg.divineschool.admin.data.auth.AuthRepositoryImpl
import hg.divineschool.admin.data.dashboard.DashboardRepository
import hg.divineschool.admin.data.dashboard.DashboardRepositoryImpl
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @DPSContext
    @Provides
    fun provideApplicationContext(application: Application): Context = application

    @Provides
    fun provideFirebaseDb(): FirebaseFirestore = FirebaseFirestore.getInstance()
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideDashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository = impl

}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DPSContext