package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.firestore.DocumentId

data class WeekDay(
    @DocumentId
    val id: String = "",
    val name: String = "",
    var dishes: List<String> = emptyList()
)
