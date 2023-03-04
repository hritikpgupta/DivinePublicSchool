package hg.divineschool.admin.data.models

data class FeeStructure(
    var admissionCharge: Int = 0,
    var annualCharge: Int = 0,
    var beltPrice: Int = 0,
    var computerFeeJunior: Int = 0,
    var computerFeeSenior: Int = 0,
    var diaryFee: Int = 0,
    var examFee: Int = 0,
    var idAndFeeCardPrice: Int = 0,
    var tieFeeJunior: Int = 0,
    var tieFeeSenior: Int = 0,
    var pgTuition: Int = 0,
    var lnTuition: Int = 0,
    var unTuition: Int = 0,
    var classOneTuition: Int = 0,
    var classTwoTuition: Int = 0,
    var classThreeTuition: Int = 0,
    var classFourTuition: Int = 0,
    var classFiveTuition: Int = 0,
    var classSixTuition: Int = 0,
    var classSevenTuition: Int = 0,
    var classEightTuition: Int = 0,
    var transportPlaces: Map<*, *> = emptyMap<Any,Any>(),
    var pgBooks: Map<*, *> = emptyMap<Any,Any>(),
    var lnBooks: Map<*, *> = emptyMap<Any,Any>(),
    var unBooks: Map<*, *> = emptyMap<Any,Any>(),
    var classOneBooks: Map<*, *> = emptyMap<Any,Any>(),
    var classTwoBooks: Map<*, *> = emptyMap<Any,Any>(),
    var classThreeBooks: Map<*, *> = emptyMap<Any,Any>(),
    var classFourBooks: Map<*, *> = emptyMap<Any,Any>(),
    var classFiveBooks: Map<*, *> = emptyMap<Any,Any>(),
    var classSixBooks: Map<*, *> = emptyMap<Any,Any>(),
    var classSevenBooks: Map<*, *> = emptyMap<Any,Any>(),
    var classEightBooks: Map<*, *> = emptyMap<Any,Any>(),
) {

    companion object{
        var FEE_STRUCT = FeeStructure()
    }
}
