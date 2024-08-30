package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.firestore.DocumentId

data class Dish(
    @DocumentId
    var id: String = "",
    val name: String = "",
    val meal: String = "",
    val description: String = "",
    val pathToImage: String = "",
    // FIXME: The type of feedback should be changed to a custom type
    var feedback: List<String> = emptyList()
) {
    enum class Meal { BREAKFAST, LUNCH }
}
