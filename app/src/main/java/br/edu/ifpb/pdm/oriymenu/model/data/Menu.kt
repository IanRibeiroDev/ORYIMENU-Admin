package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Menu(
    @DocumentId
    val id: String = "",
    val date: Date? = null,
    var dishes: List<Int> = emptyList()
)
