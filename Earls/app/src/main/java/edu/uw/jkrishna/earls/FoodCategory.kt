package edu.uw.jkrishna.earls

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

enum class FoodCategory(@StringRes val nameResource: Int,
                        @DrawableRes val imageResource: Int) {

    PIZZA(R.string.pizza, R.drawable.pizza),
    SANDWICHES(R.string.sandwiches, R.drawable.sandwiches),
    BURGERS(R.string.burgers, R.drawable.burgers),
    WINGS(R.string.wings, R.drawable.wings),
    FRIES(R.string.fries, R.drawable.fries);

    companion object {
        fun getFoodCategories() = FoodCategory.values()
    }
}