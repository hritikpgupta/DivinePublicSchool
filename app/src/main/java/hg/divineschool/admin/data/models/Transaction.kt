package hg.divineschool.admin.data.models

import com.google.firebase.Timestamp


data class Transaction(
var amount : Long,
var className : String,
var scholarNumber : String,
var invoiceNumber: String ,
var studentName : String,
var timestamp: Timestamp

)
