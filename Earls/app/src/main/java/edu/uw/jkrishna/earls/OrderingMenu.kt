package edu.uw.jkrishna.earls

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_ordering_menu.*
import kotlinx.android.synthetic.main.fragment_food.view.*
import kotlinx.android.synthetic.main.list_food_item.view.*
import java.lang.Integer.parseInt

var order: HashMap<Food, Int> = HashMap<Food, Int>()

class OrderingMenu : AppCompatActivity() {

    private lateinit var viewModel: FoodViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordering_menu)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java)

        val pagerAdapter = MenuPagerAdapter(supportFragmentManager)

        container.let {
            it.adapter = pagerAdapter
            it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
            tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(it))
        }

        val type = intent.type
        if (type != null) {
            val foodCategory = FoodCategory.valueOf(type)
            val position = pagerAdapter.getPositionForCategory(foodCategory)
            container.currentItem = position
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

    inner class MenuPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val category = getMenuCategoryForPosition(position)
            val menuForCategory = viewModel.getFoodForCategory(category)
            return FoodFragment.newInstance(menuForCategory)
        }

        override fun getCount() = FoodCategory.getFoodCategories().size

        fun getMenuCategoryForPosition(position: Int) = when (position) {
            0    -> FoodCategory.PIZZA
            1    -> FoodCategory.SANDWICHES
            2    -> FoodCategory.BURGERS
            3    -> FoodCategory.WINGS
            4    -> FoodCategory.FRIES
            else -> throw NotImplementedError("Unknown category for position")
        }

        fun getPositionForCategory(category: FoodCategory) = when (category) {
            FoodCategory.PIZZA    -> 0
            FoodCategory.SANDWICHES    -> 1
            FoodCategory.BURGERS -> 2
            FoodCategory.WINGS  -> 3
            FoodCategory.FRIES   -> 4
        }
    }

    class FoodViewModel(application: Application) : AndroidViewModel(application) {

        fun getFoodForCategory(category: FoodCategory): List<Food> {
            return MenuUtils.getFoodCategoryOfFood(category)
        }
    }

    class FoodFragment : Fragment() {


        private val adapter = FoodAdapter()

        var foodList: List<Food>? = null
            set(value) {
                field = value
                adapter.submitList(value)
            }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View {
            val view = inflater.inflate(R.layout.fragment_food, container, false)

            view.food_list.adapter = adapter
            activity?.applicationContext
            return view
        }

        private class FoodAdapter :
                ListAdapter<Food, FoodAdapter.FoodViewHolder>(DIFF_CALLBACK) {



            override fun onCreateViewHolder(parent: ViewGroup, flags: Int): FoodViewHolder {
                val view = parent.inflate(R.layout.list_food_item)
                return FoodViewHolder(view)
            }

            override fun onBindViewHolder(viewHolder: FoodViewHolder, position: Int) {
                val item = getItem(position)
                viewHolder.bind(item)
            }

            inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                private val nameTextView = view.name
                private val priceTextView = view.price
                private val descriptionTextView = view.description
                private val buttonAdd = view.btn_add
                private val quantity = view.quantity
                private val context = view.context



                fun bind(food: Food) {
                    nameTextView.text = food.name
                    priceTextView.text = "$" + food.price + 0
                    descriptionTextView.text = food.description
                    buttonAdd.setOnClickListener {
                        if (quantity.text.toString() != " ") {
                            if (parseInt(quantity.text.toString()) == 0) {
                                order.remove(food)
                                Toast.makeText(context, "Removed " +
                                        food.name + " from cart", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Added " + quantity.text.toString() + " " +
                                        food.name + " to cart", Toast.LENGTH_LONG).show()
                                order.put(food, parseInt(quantity.text.toString()))
                            }
                        }
                    }
                }
            }

            companion object {

                val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Food>() {
                    override fun areItemsTheSame(oldItem: Food,
                                                 newItem: Food) = oldItem === newItem

                    override fun areContentsTheSame(oldItem: Food,
                                                    newItem: Food) = oldItem === newItem
                }
            }

            fun ViewGroup.inflate(@LayoutRes
                                  resource: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(
                    context).inflate(resource, this, attachToRoot)
        }


        companion object {
            fun newInstance(food: List<Food>) = FoodFragment().apply {
                foodList = food
            }
        }
    }

}
