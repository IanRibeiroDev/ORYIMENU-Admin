package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.firestore.DocumentId

data class Dish(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val meal: String = "",
    val description: String = "",
    val pathToImage: String = "",
    // FIXME: this field will be removed in the future as it is only for testing purposes
    var feedback: List<String> = emptyList()
) {
    enum class Meal { BREAKFAST, LUNCH }
}
