package hg.divineschool.admin.data.dashboard.settings

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.Log_Tag
import hg.divineschool.admin.ui.utils.defaultTuitionFeeList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject



class SettingRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : SettingRepository {

    override suspend fun migrateClassEightUser(): Resource<Boolean> {
         try {

/*            moveAndDeleteClassEightStudentFirst()
            moveAndDeleteStudentsFromOneClassToAnother("classSeven", "classEight")
            moveAndDeleteStudentsFromOneClassToAnother("classSix", "classSeven")
            moveAndDeleteStudentsFromOneClassToAnother("classFive", "classSix")
            moveAndDeleteStudentsFromOneClassToAnother("classFour", "classFive")
            moveAndDeleteStudentsFromOneClassToAnother("classThree", "classFour")
            moveAndDeleteStudentsFromOneClassToAnother("classTwo", "classThree")
            moveAndDeleteStudentsFromOneClassToAnother("classOne", "classTwo")
            moveAndDeleteStudentsFromOneClassToAnother("classUpperNursery", "classOne")
            moveAndDeleteStudentsFromOneClassToAnother("classLowerNursery", "classUpperNursery")
            moveAndDeleteStudentsFromOneClassToAnother("classPlayGroup", "classLowerNursery")*/
            return Resource.Success(true)
        } catch (e: java.lang.Exception) {
            return Resource.Failure(e)
        }
    }



}