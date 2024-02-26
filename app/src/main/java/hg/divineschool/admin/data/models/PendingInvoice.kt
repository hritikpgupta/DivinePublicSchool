package hg.divineschool.admin.data.models

data class PendingInvoice(var remarks: List<String>, var invoice: Invoice, var duesCleared : Boolean = false){
    constructor() : this(emptyList(), Invoice()) }

