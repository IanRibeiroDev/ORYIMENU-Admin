package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.firestore.DocumentId

class Feedback(
    @DocumentId
    val id: String = "",
    val description: String,
    val pathToImage: String,
    val dish: Dish)
