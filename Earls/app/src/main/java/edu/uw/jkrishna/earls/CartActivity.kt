package edu.uw.jkrishna.earls

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cart.*
import com.google.firebase.database.FirebaseDatabase

data class Order(val orderId: String?, val orderItems: String, val address: String, val name: String, val phoneNumber: String, val price: Float)


class CartActivity : AppCompatActivity() {

    var totalPriceAsFloat: Float = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        var total = 0f
        order.forEach { key, i ->
            val subTotal = i * key.price
            orderPrint.text = orderPrint.text.toString() + i + "x " + key.name + " -$" + subTotal + "0" + System.lineSeparator()
            total += subTotal
        }

        totalPriceAsFloat = total
        totalPrice.text = "$" + total.toString() + "0"

        placeOrder.setOnClickListener {
            if(phoneNumer != "Not Set" || userAddress != "Not Set" || userName != "Not Set"){
                sendToFireBase()
            }
            else{
                Toast.makeText(this, "Please go to settings to add delivery details", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun sendToFireBase() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("orders")
        val orderId: String? = myRef.push().key

        val order = Order(orderId, orderPrint.text.toString(), userAddress, userName, phoneNumer, totalPriceAsFloat)

        myRef.child(orderId!!).setValue(order).addOnCompleteListener {
            Toast.makeText(this, "Order Sent", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, Settings::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
