package com.example.model
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class Test {
    //way1==prepareModularApp->runMedicalApp-PerhapsFilingApp->readyForDriver-User-App->migrationToBackend->migrationToAI
    //way2==prepareModularApp->readAndroidDocForMigrationToNewCompany->migrationToNewCompany->startNewProjectAndLearnNewSkill
    //way3==prepareModularApp->readAndroidDocForMigrationToNewCompany->migrationToNewCompanyWithRemoteTime-> ---
    //15EYD==study,backend,fitness,
    //way4==prepareModularApp->migrationToBackend->startForDriver-User-App
    //way5=youCanDecidedForImproveFARADIDCompanyAndUseItForOwn
    //target-koja-transportation(barsan)
    val context:Context?=null
    val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = context?.getSystemService<ConnectivityManager>()
        if (connectivityManager == null) {
            channel.trySend(false)
            channel.close()
            return@callbackFlow
        }

        fun goals():String {
            val android = "betterAndroidDeveloperWithDoBestProject"
            val backend = "learnBackendConceptWithLaravelAndPythonLanguage"
            val time="dependOnGoalsHaveFlexibilityTime"
            val money="upperThan26m"
            val artificialIntelligence ="learnPythonAndThenConceptOfAiAndThenUseThemInRealProject"
            val company="learnAndBetterExperienceAndFindBestSolutionForCreateOwnCompany"
            return android+backend+artificialIntelligence+money+time+company
        }

        fun point():String{
            val p1="problemStartNewProjectOfDriver-UserBecauseOfBackendDeveloperAndDon'tHaveApi"
            val p2="problemStartMedicalProjectWithoutSupport"
            val p3="ifYouWantCreateMedicalProjectYouShouldDoBestResearch"
            val p4="ifYouAcceptBackendDutyInFARADIDCompanyLostVeryTimeButLearnBackendPerfectly"
            val p5=""
            return p1+p2+p3+p4+p5
        }

        fun type1(prepareModularApp: Boolean,prepareForBackEndProject: Boolean,prepareForIosApp:Boolean) {
            var salaryOfThisYears: String
            if (prepareModularApp) salaryOfThisYears="26-30"
            if (prepareForIosApp) salaryOfThisYears="30-35"
            if(prepareForBackEndProject) salaryOfThisYears="30-37"
        }
    }
}

//topper salary in iran == 50m(advancedWork)
//foreign project == 50-150m (*getP & language & advancedWork)
//migration to another country == 2-8$(*startFromZero && findWork && language & advancedWork)
//selfCompany == ??
//financialCoin == 50-200 (* after2-3years && advancedWork && language)
/////Faradid=topper salary==30m or 26m