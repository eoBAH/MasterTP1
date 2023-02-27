package fr.openium.mastertp1_2.model

import kotlinx.serialization.SerialName

class MemeResponse {
    var success: Boolean? = null
    var data: MemeList? = null
    var errorMessage: String? = null
}