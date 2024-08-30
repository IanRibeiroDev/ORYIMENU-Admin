package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FeedbackDAO() {

    private val db = Firebase.firestore

    /**
     * Save a feedback in the database
     * @param feedback the feedback to be saved
     * @param callback function that will receive the saved feedback
     */
    fun save(feedback: Feedback, callback: (Feedback?) -> Unit) {
        db.collection("feedback").add(feedback)
            .addOnSuccessListener { documentReference ->
                if (documentReference != null) {
                    callback(feedback)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

}
