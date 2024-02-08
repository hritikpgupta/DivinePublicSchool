package hg.divineschool.admin.di

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hg.divineschool.admin.data.auth.AuthRepository
import hg.divineschool.admin.data.auth.AuthRepositoryImpl
import hg.divineschool.admin.data.dashboard.DashboardRepository
import hg.divineschool.admin.data.dashboard.DashboardRepositoryImpl
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import hg.divineschool.admin.data.dashboard.settings.SettingRepositoryImpl
import hg.divineschool.admin.data.dashboard.student.*
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @DPSContext
    @Provides
    fun provideApplicationContext(application: Application): Context = application

    @Provides
    fun provideFirebaseDb(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    fun provideStorageReference(storage: FirebaseStorage): StorageReference {
        return storage.reference
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideDashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository = impl

    @Provides
    fun provideStudentListRepository(impl: StudentListRepositoryImpl): StudentListRepository = impl
    @Provides
    fun provideRegisterStudentRepository(impl: RegisterStudentRepositoryImpl): RegisterStudentRepository = impl
    @Provides
    fun provideStudentInvoiceRepository(impl: StudentInvoiceRepositoryImpl): StudentInvoiceRepository = impl
    @Provides
    fun provideSettingsRepository(impl: SettingRepositoryImpl): SettingRepository = impl
    @Provides
    fun providePendingInvoiceRepository(impl: PendingInvoiceRepositoryImpl): PendingInvoiceRepository = impl

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DPSContext