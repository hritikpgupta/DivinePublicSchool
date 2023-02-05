package hg.divineschool.admin.data.models

import com.google.firebase.firestore.GeoPoint

data class SchoolInformation(
    val contact: Int = 0,
    val email: String = "",
    val endsAt: String = "",
    val estd: Int = 0,
    val isOpen: Boolean = true,
    val location: GeoPoint = GeoPoint(25.0183, 83.0318),
    val logo: String = "",
    val name: String = "",
    val ssid: String = "",
    val startsAt: String = "",
    val website: String = ""
)
