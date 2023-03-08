package hg.divineschool.admin.data.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Invoice(
    var invoiceNumber: String = "",
    var date: String = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " at " + LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("HH:mm")),
    var tuitionFeeMonthList: String = "",
    var bookList: String = "",
    var supplementsList: String = "",
    var admissionFee: Int = 0,
    var annualCharge: Int = 0,
    var computerFee: Int = 0,
    var examFee: Int = 0,
    var lateFee: Int = 0,
    var tuitionFee: Int = 0,
    var transportFee: Int = 0,
    var supplementaryFee: Int = 0,
    var bookFee: Int = 0,
    var total: Int = 0,
    var className: String = "",
    var studentName: String = "",
    var guardianName: String = "",
    var address: String = "",
    var rollNumber: Int = 0,
    var placeName: String = "",
)
