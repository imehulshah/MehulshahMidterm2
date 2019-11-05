package com.mesh.mehulshahmidterm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_order_list.*

class OrderList : AppCompatActivity() {
    // connect to firestore
    var db = FirebaseFirestore.getInstance()

    // classes to store and pass query data
    //private var adapter: OrderAdapter? = null

    // Kotlin equivalent of Java ArrayList class. We decided we don't need this after all
    private var OrderList = mutableListOf<Order>()

    // classes to store and pass query data
    private var adapter: OrderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        // recycler will have linear layout
        recyclerViewOrders.layoutManager = LinearLayoutManager(this)

        // query
        val query = db.collection("orders").orderBy("fname", Query.Direction.ASCENDING)

        // pass the query results to the recycler adapter for display
        val options = FirestoreRecyclerOptions.Builder<Order>().setQuery(query, Order::class.java).build()
        adapter = OrderAdapter(options)

        // bind the adapter to the recyclerview (adapter means the datasource)
        recyclerViewOrders.adapter = adapter
    }

    // start listening for changes if the the activity starts / restarts
    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    // stop listening for data changes if the activity gets stopped
    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    // inner classes needed to read and bind the data
    private inner class OrderViewHolder internal constructor(private val view: View) :
        RecyclerView.ViewHolder(view) {

        // not needed if we bind to the item template directly on the onBindViewHolder method below
//        internal fun displayArtist(artistName: String, artistGenre: String) {
//            // populate the corresponding textViews in the layout template inflated when adapter created below
//            val textViewName = view.findViewById<TextView>(R.id.textViewName)
//            val textViewGenre = view.findViewById<TextView>(R.id.textViewGenre)
//
//            textViewName.text = artistName
//            textViewGenre.text = artistGenre
//        }
    }

    private inner class OrderAdapter internal constructor(options: FirestoreRecyclerOptions<Order>) :
        FirestoreRecyclerAdapter<Order,
                OrderViewHolder>(options) {
        override fun onBindViewHolder(p0: OrderViewHolder, p1: Int, p2: Order) {

            p0.itemView.findViewById<TextView>(R.id.textViewFirstName).text = p2.FName
            p0.itemView.findViewById<TextView>(R.id.textViewLastName).text = p2.LName
            p0.itemView.findViewById<TextView>(R.id.textViewChoice).text = p2.SChoices
            p0.itemView.findViewById<TextView>(R.id.textViewQuantity).text = p2.SQuantity
            p0.itemView.findViewById<TextView>(R.id.textViewType).text = p2.STypes

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
            return OrderViewHolder(view)
        }
    }
}
