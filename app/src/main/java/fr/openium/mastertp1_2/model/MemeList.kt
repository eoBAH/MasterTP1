package fr.openium.mastertp1_2.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class MemeList (
    @SerializedName("memes") val memesList: ArrayList<Meme>
    )