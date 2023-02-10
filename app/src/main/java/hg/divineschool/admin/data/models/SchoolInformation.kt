package hg.divineschool.admin.data.models

import com.google.firebase.firestore.GeoPoint

data class SchoolInformation(
    var contact: Long = 0,
    var email: String = "",
    var endsAt: String = "",
    var estd: Long = 0,
    var isOpen: Boolean = true,
    var location: GeoPoint = GeoPoint(25.0183, 83.0318),
    var logo: String = "",
    var name: String = "",
    var ssid: String = "",
    var startsAt: String = "",
    var website: String = ""
)
