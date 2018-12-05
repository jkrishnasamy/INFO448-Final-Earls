package edu.uw.jkrishna.earls

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.support.v7.util.DiffUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.category_list_item.view.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryList.adapter = CategoryAdapter().apply {
            val foodCategories = FoodCategory.getFoodCategories().asList()

            submitList(foodCategories)
        }

        fab_cart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
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

    private class CategoryAdapter
        : ListAdapter<FoodCategory, CategoryAdapter.CategoryViewHolder>(CategoryAdapter.DIFF_CALLBACK) {

        override fun onCreateViewHolder(parent: ViewGroup, flags: Int): CategoryViewHolder {
            val view = parent.inflate(R.layout.category_list_item)
            return CategoryViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: CategoryViewHolder, position: Int) {
            val item = getItem(position)
            viewHolder.bind(item)
        }

        inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val name = view.name
            private val image = view.image

            init {
                view.setOnClickListener { v ->
                    val position = adapterPosition
                    val foodCategory = getItem(position)
                    val context = v.context

                    val intent = Intent(context, OrderingMenu::class.java)
                            .setType(foodCategory.name)
                    context.startActivity(intent)
                }
            }

            fun bind(foodCategory: FoodCategory) {
                name.setText(foodCategory.nameResource)
                image.setImageResource(foodCategory.imageResource)
            }
        }

        companion object {
            @JvmField
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FoodCategory>() {
                override fun areItemsTheSame(oldItem: FoodCategory,
                                             newItem: FoodCategory) = oldItem == newItem

                override fun areContentsTheSame(p0: FoodCategory, p1: FoodCategory) = true
            }
        }

        fun ViewGroup.inflate(@LayoutRes
                              resource: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(
                context).inflate(resource, this, attachToRoot)
    }
}
