package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects

class DishDAO {

    private val db = Firebase.firestore
    private val dbEntityName = "dish"

    /**
     * Save a dish in the database
     * @param dish the dish to be saved
     * @param callback function that will receive a
     * boolean indicating if the dish was saved
     */
    fun save(dish: Dish, callback: (Boolean) -> Unit) {
        db.collection("dish").add(dish)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    /**
     * Update a dish in the database
     * @param dish the dish to be updated
     * @param callback function that will receive a
     * boolean indicating if the dish was updated
     */
    fun update(dish: Dish, callback: (Boolean) -> Unit) {
        db.collection(dbEntityName).document(dish.id).set(dish)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    /**
     * Get all dishes from the database
     * @param callback function that will receive the list of dishes
     */
    fun findAll(callback: (List<Dish>) -> Unit) {
        db.collection(dbEntityName).get()
            .addOnSuccessListener { document ->
                val dishes = document.toObjects<Dish>()
                callback(dishes)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    /**
     * Search for a dish by its id
     * @param id the id of the dish
     * @param callback function that will receive the dish
     */
    fun findById(id: String, callback: (Dish?) -> Unit) {
        db.collection(dbEntityName).document(id).get()
            .addOnSuccessListener { document ->
                val dish = document.toObject(Dish::class.java)
                callback(dish)
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    /**
     * Delete a dish from the database
     * @param dish the dish to be deleted
     * @param callback function that will receive a
     * boolean indicating if the dish was deleted
     */
    fun delete(dish: Dish, callback: (Boolean) -> Unit) {
        db.collection(dbEntityName).document(dish.id).delete()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

}
