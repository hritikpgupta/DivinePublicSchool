package hg.divineschool.admin.data.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Invoice(
    var invoiceNumber: String = "",
    var date: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
    var time: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
    var tuitionFeeMonthList: String = "",
    var bookList: String = "",
    var supplementsList: String = "",
    var developmentFee: Long = 0,
    var annualCharge: Long = 0,
    var computerFee: Long = 0,
    var examFee: Long = 0,
    var lateFee: Long = 0,
    var tuitionFee: Long = 0,
    var transportFee: Long = 0,
    var supplementaryFee: Long = 0,
    var bookFee: Long = 0,
    var total: Long = 0,
    var className: String = "",
    var studentName: String = "",
    var scholarNumber: Long = 0,
    var guardianName: String = "",
    var address: String = "",
    var rollNumber: Long = 0,
    var placeName: String = "",
) : java.io.Serializable
