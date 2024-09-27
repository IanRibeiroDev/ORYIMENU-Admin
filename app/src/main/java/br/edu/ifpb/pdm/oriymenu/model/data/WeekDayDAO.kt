package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects

class WeekDayDAO {

    private val db = Firebase.firestore
    private val dayOfWeekRef = db.collection("dayOfWeek")

    /**
     * Get all days of the week and their dishes from the database
     * @param callback function that will receive the list of days of the week
     */
    fun findAll(callback: (List<WeekDay>) -> Unit) {
        dayOfWeekRef.get()
            .addOnSuccessListener { dayOfWeek ->
                val daysOfWeek = dayOfWeek.toObjects<WeekDay>()
                callback(daysOfWeek)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun findByDayOfWeek(name: String, callback: (WeekDay?) -> Unit) {
        dayOfWeekRef.whereEqualTo("name", name).get()
            .addOnSuccessListener { weekDay ->
                val day = weekDay.toObjects<WeekDay>().firstOrNull()
                callback(day)
            }
            .addOnFailureListener {
                callback(null)
            }
    }

}
