package fr.openium.mastertp1_2.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class MemeResponse (
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: MemeList
)