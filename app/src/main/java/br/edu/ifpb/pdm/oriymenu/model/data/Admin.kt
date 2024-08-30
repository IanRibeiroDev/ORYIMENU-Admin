package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.firestore.DocumentId

class Admin(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = ""
) {
}
