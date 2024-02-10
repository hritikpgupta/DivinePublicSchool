package hg.divineschool.admin.data.models

data class PendingInvoice(var remarks: List<String>, var invoice: Invoice){
    constructor() : this(emptyList(), Invoice())    }

