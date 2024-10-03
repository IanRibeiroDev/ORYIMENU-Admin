package br.edu.ifpb.pdm.oriymenu.model.data

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AdminDAO {

    private val db = Firebase.firestore
    private val dbEntityName = "admin"

    /**
     * Find an admin by its id
     * @param id the id of the admin
     * @param callback function that will receive the admin
     */
    fun findById(id: String, callback: (Admin?) -> Unit) {
        db.collection(dbEntityName).document(id).get()
            .addOnSuccessListener { document ->
                val admin = document.toObject(Admin::class.java)
                callback(admin)
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    /**
     * Find an admin by its email
     * @param email the email of the admin
     * @param callback function that will receive the admin
     */
    fun findByEmail(email: String, callback: (Admin?) -> Unit) {
        db.collection(dbEntityName).whereEqualTo("email", email).get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    callback(null)
                } else {
                    val admin = documents.documents[0].toObject(Admin::class.java)
                    callback(admin)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    /**
     * Generates a formatted string with the main details of the admin's address.
     * @param address The address object containing the details of the admin's address.
     * @return A string containing the formatted address.
     *
     * Example:
     * Input: Address(zipCode = "62704", street = "Main St", number = "123", neighborhood = "Downtown", city = "Springfield", state = "IL")
     * Output: "123 Main St, Downtown, Springfield, IL - 62704"
     */
    private fun formatAdminAddress(address: Address): String {
        return "${address.complemento} ${address.logradouro}, ${address.localidade}, ${address.uf} - ${address.cep}"
    }


    /**
     * Save an admin in the Firebase database
     * @param admin the admin to be saved
     * @param callback function that will receive a
     * boolean indicating if the admin was saved
     */
    fun saveAdmin(admin: Admin, address: Address, callback: (Boolean) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        // Create user in Firebase Authentication
        auth.createUserWithEmailAndPassword(admin.email, admin.password)
            .addOnSuccessListener { authResult ->
                // Prepare admin data to save in Firestore
                val formattedAddress = formatAdminAddress(address)

                val adminData = Admin(
                    id = authResult.user!!.uid, // Set the Firebase UID as the document ID
                    name = admin.name,
                    email = admin.email,
                    password = "", // Do not store the password in Firestore
                    address = formattedAddress // Add the formatted address to Firestore
                )

                // Save admin data in Firestore under 'admin' collection
                firestore.collection(dbEntityName).document(authResult.user!!.uid)
                    .set(adminData)
                    .addOnSuccessListener {
                        callback(true) // Call callback with true when operation succeeds
                    }
                    .addOnFailureListener {
                        callback(false) // Call callback with false if Firestore save fails
                    }
            }
            .addOnFailureListener {
                callback(false) // Call callback with false if Authentication fails
            }
    }

    /**
     * Authenticate an admin using email and password.
     * @param email The admin's email.
     * @param password The admin's password.
     * @param callback Function that will receive a boolean indicating if authentication was successful.
     */
    fun authenticateAdmin(email: String, password: String, callback: (Boolean) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                callback(true) // Authentication successful
            }
            .addOnFailureListener {
                callback(false) // Authentication failed
            }
    }

    /**
     * Send a password reset email to the admin.
     * @param email The email of the admin.
     * @param callback Function that will receive a boolean indicating if the reset email was sent successfully.
     */
    fun sendPasswordResetEmail(email: String, callback: (Boolean) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                callback(true) // Reset email sent successfully
            }
            .addOnFailureListener {
                callback(false) // Failed to send reset email
            }
    }
    
}
