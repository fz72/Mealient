package gq.kirmanak.mealient.datasource.v0.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRecipeNoteV0(
    @SerialName("title") val title: String = "",
    @SerialName("text") val text: String = "",
)