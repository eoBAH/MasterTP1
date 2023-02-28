package fr.openium.mastertp1_2.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class MemeResponse (
    var success: Boolean,
    var data: MemeList
)