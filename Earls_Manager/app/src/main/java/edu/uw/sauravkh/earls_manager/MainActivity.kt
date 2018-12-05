package edu.uw.sauravkh.earls_manager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*
import android.widget.ArrayAdapter
import android.view.View
import android.view.ViewGroup

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import java.util.*
import kotlin.concurrent.fixedRateTimer


class MainActivity : AppCompatActivity() {

    private lateinit var ref:DatabaseReference
    private lateinit var orderList:MutableList<Order>
    private lateinit var listview: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fixedRateTimer = fixedRateTimer(name = "hello-timer",
                initialDelay = 100, period = 3000) {
            updateList()
        }
        try {
            Thread.sleep(1000)
        } finally {
            fixedRateTimer.cancel();
        }


    }

    private fun updateList() {
        orderList = mutableListOf()


        ref = FirebaseDatabase.getInstance().getReference("orders")

        listview = findViewById(R.id.list_view)

        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {

                Log.v("SomethingBetter", p0.toString())

                if(p0!!.exists()){
                    for(h in p0.children) {
                        val order = h.getValue(Order::class.java)
                        orderList.add(order!!)
                    }
                    val adapter = OrderAdapter(applicationContext, R.layout.order_item, orderList)
                    listview.adapter = adapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })

        listview.post( { listview.setSelection(listview.getCount() - 1) })

    }


}

data class Order(
        val orderId: String?,
        val orderItems: String,
        val address: String,
        val name: String,
        val phoneNumber: String,
        val price: Float
) {
    constructor() : this("","","","","",0.0.toFloat())
}

class OrderAdapter(val mCtx:Context, val layoutResId: Int, val orderList:List<Order> )
    : ArrayAdapter<Order>(mCtx, layoutResId, orderList) {
    override fun getView(position:Int, convertView:View?, parent:ViewGroup?):View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResId, null)

        val name = view.findViewById<TextView>(R.id.name)
//        val orderId = view.findViewById<TextView>(R.id.order_id)
        val address = view.findViewById<TextView>(R.id.address)
        val phoneNumber = view.findViewById<TextView>(R.id.phone)
        val price = view.findViewById<TextView>(R.id.price)
        val orderItems = view.findViewById<TextView>(R.id.order)

        val order  = orderList[position]

        Log.v("SomethingBetter", order.toString())

        name.text = "Name: " + order.name
//        orderId.text = order.orderId
        address.text = "Address: " + order.address
        phoneNumber.text = "Phone Number: " + order.phoneNumber
        orderItems.text = "Order: " + order.orderItems


        price.text = "Total Price: " + order.price.toString()

        return view

    }
}