package hg.divineschool.admin.ui.utils

import hg.divineschool.admin.data.models.MonthFee

const val INR = "₹"

const val Log_Tag = "Dps"

val defaultTuitionFeeList = listOf(
    MonthFee(isPaid = false, month = "January", monthIndex = 1),
    MonthFee(isPaid = false, month = "February", monthIndex = 2),
    MonthFee(isPaid = false, month = "March", monthIndex = 3),
    MonthFee(isPaid = false, month = "April", monthIndex = 4),
    MonthFee(isPaid = false, month = "May", monthIndex = 5),
    MonthFee(isPaid = false, month = "June", monthIndex = 6),
    MonthFee(isPaid = false, month = "July", monthIndex = 7),
    MonthFee(isPaid = false, month = "August", monthIndex = 8),
    MonthFee(isPaid = false, month = "September", monthIndex = 9),
    MonthFee(isPaid = false, month = "October", monthIndex = 10),
    MonthFee(isPaid = false, month = "November", monthIndex = 11),
    MonthFee(isPaid = false, month = "December", monthIndex = 12),
)
val monthList = listOf(
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December"
)

val classNames = listOf(
    "Play Group",
    "Lower Nursery",
    "Upper Nursery",
    "Class One",
    "Class Two",
    "Class Three",
    "Class Four",
    "Class Five",
    "Class Six",
    "Class Seven",
    "Class Eight"
)
fun String.convertClassNameToPath(): String {
    return if (this == classNames[0]) {
        "classPlayGroup"
    } else if (this == classNames[1]) {
        "classLowerNursery"
    } else if (this == classNames[2]) {
        "classUpperNursery"
    } else if (this == classNames[3]) {
        "classOne"
    } else if (this == classNames[4]) {
        "classTwo"
    } else if (this == classNames[5]) {
        "classThree"
    } else if (this == classNames[6]) {
        "classFour"
    } else if (this == classNames[7]) {
        "classFive"
    } else if (this == classNames[8]) {
        "classSix"
    } else if (this == classNames[9]) {
        "classSeven"
    } else if (this == classNames[10]) {
        "classEight"
    } else {
        ""
    }
}

