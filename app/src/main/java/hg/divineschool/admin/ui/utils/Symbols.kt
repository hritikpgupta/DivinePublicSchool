package hg.divineschool.admin.ui.utils

import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.MonthFee
import hg.divineschool.admin.data.utils.convertToBookList

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

fun String.getClassBook(): List<Book> {
    return if (this == classNames[0]) {
        FeeStructure.FEE_STRUCT.pgBooks.convertToBookList()
    } else if (this == classNames[1]) {
        FeeStructure.FEE_STRUCT.lnBooks.convertToBookList()
    } else if (this == classNames[2]) {
        FeeStructure.FEE_STRUCT.unBooks.convertToBookList()
    } else if (this == classNames[3]) {
        FeeStructure.FEE_STRUCT.classOneBooks.convertToBookList()
    } else if (this == classNames[4]) {
        FeeStructure.FEE_STRUCT.classTwoBooks.convertToBookList()
    } else if (this == classNames[5]) {
        FeeStructure.FEE_STRUCT.classThreeBooks.convertToBookList()
    } else if (this == classNames[6]) {
        FeeStructure.FEE_STRUCT.classFourBooks.convertToBookList()
    } else if (this == classNames[7]) {
        FeeStructure.FEE_STRUCT.classFiveBooks.convertToBookList()
    } else if (this == classNames[8]) {
        FeeStructure.FEE_STRUCT.classSixBooks.convertToBookList()
    } else if (this == classNames[9]) {
        FeeStructure.FEE_STRUCT.classSevenBooks.convertToBookList()
    } else if (this == classNames[10]) {
        FeeStructure.FEE_STRUCT.classEightBooks.convertToBookList()
    } else {
        emptyList()
    }
}

fun String.updateBookPrice(book: Book): List<Book> {
    if (this == classNames[0]) {
        val map = FeeStructure.FEE_STRUCT.pgBooks.toMutableMap()
        map[book.bookName] = book.bookPrice
        FeeStructure.FEE_STRUCT.pgBooks = map
        return FeeStructure.FEE_STRUCT.pgBooks.convertToBookList()

    } else if (this == classNames[1]) {
        FeeStructure.FEE_STRUCT.lnBooks.convertToBookList()
    } else if (this == classNames[2]) {
        FeeStructure.FEE_STRUCT.unBooks.convertToBookList()
    } else if (this == classNames[3]) {
        FeeStructure.FEE_STRUCT.classOneBooks.convertToBookList()
    } else if (this == classNames[4]) {
        FeeStructure.FEE_STRUCT.classTwoBooks.convertToBookList()
    } else if (this == classNames[5]) {
        FeeStructure.FEE_STRUCT.classThreeBooks.convertToBookList()
    } else if (this == classNames[6]) {
        FeeStructure.FEE_STRUCT.classFourBooks.convertToBookList()
    } else if (this == classNames[7]) {
        FeeStructure.FEE_STRUCT.classFiveBooks.convertToBookList()
    } else if (this == classNames[8]) {
        FeeStructure.FEE_STRUCT.classSixBooks.convertToBookList()
    } else if (this == classNames[9]) {
        FeeStructure.FEE_STRUCT.classSevenBooks.convertToBookList()
    } else if (this == classNames[10]) {
        FeeStructure.FEE_STRUCT.classEightBooks.convertToBookList()
    }




    return emptyList()
}

fun String.convertClassNameToBookField(): String {
    return if (this == classNames[0]) {
        "pgBooks"
    } else if (this == classNames[1]) {
        "lnBooks"
    } else if (this == classNames[2]) {
        "unBooks"
    } else if (this == classNames[3]) {
        "classOneBooks"
    } else if (this == classNames[4]) {
        "classTwoBooks"
    } else if (this == classNames[5]) {
        "classThreeBooks"
    } else if (this == classNames[6]) {
        "classFourBooks"
    } else if (this == classNames[7]) {
        "classFiveBooks"
    } else if (this == classNames[8]) {
        "classSixBooks"
    } else if (this == classNames[9]) {
        "classSevenBooks"
    } else if (this == classNames[10]) {
        "classEightBooks"
    } else {
        ""
    }
}

