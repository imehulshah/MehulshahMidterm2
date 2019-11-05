package com.mesh.mehulshahmidterm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentReference
import com.google.android.gms.tasks.OnSuccessListener
//import javax.swing.UIManager.put
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
//import com.google.firestore.v1.StructuredQuery



class MainActivity : AppCompatActivity() {

    // connect to firestore
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonViewAll.setOnClickListener {
            val intent = Intent(applicationContext, OrderList::class.java)
            startActivity(intent)
        }

        buttonOrder.setOnClickListener {
            // get the user inputs
            val fname = editTextFirstname.text.toString().trim()
            val lname = editTextLastname.text.toString().trim()

            // validate name input
            if (TextUtils.isEmpty(fname)) {
                Toast.makeText(this, "First Name is Required", LENGTH_LONG).show()
            }

            if (TextUtils.isEmpty(lname)) {
                Toast.makeText(this, "Last Name is Required", LENGTH_LONG).show()
            }
            else {
                // store selected Genre
                val types = spinnerTypes.selectedItem.toString()
                val choices = spinnerChoices.selectedItem.toString()
                val quantity = spinnerQuantity.selectedItem.toString()

                // get new document with unique id
                val tbl = db.collection("orders")
                val id = tbl.document().getId()

                // create and populate new Artist object
                val order = Order( id, fname, lname, types, choices, quantity)

                // or call empty constructor and populate properties individually
//                var artist = Artist()
//                artist.artistId = id
//                artist.artistName = name
//                artist.artistGenre = genre

                // save to the db
                tbl.document(id).set(order)

                // clean up
                editTextFirstname.setText("")
                editTextLastname.setText("")
                spinnerQuantity.setSelection(0)
                spinnerChoices.setSelection(0)
                spinnerTypes.setSelection(0)

                var totalCost = 0
                when(choices.toString()){
                    "Small" -> totalCost = 10 * quantity.toInt()
                    "Medium" -> totalCost = 15 * quantity.toInt()
                    "Large" -> totalCost = 20 * quantity.toInt()
                }

                Toast.makeText(this, "Order Added with total ${totalCost}", LENGTH_LONG).show()
            }
            buttonReset.setOnClickListener {
                editTextFirstname.setText("")
                editTextLastname.setText("")
                spinnerQuantity.setSelection(0)
                spinnerChoices.setSelection(0)
                spinnerTypes.setSelection(0)

            }


            // Create a new user with a first and last name
            /*val user = HashMap()
            user.put("first", "Ada")
            user.put("last", "Lovelace")
            user.put("born", 1815)*/

            /* val data = HashMap<String, Any>()
             data.put("first", "Another")
             data.put("last", "Person")
             data.put("born", "1989")*/


            // Add a new document with a generated ID
            /*db.collection("members")
             .add(data)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this,
                        "DocumentSnapshot added with ID: " + documentReference.id,
                        Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }*/
        }
    }

}

