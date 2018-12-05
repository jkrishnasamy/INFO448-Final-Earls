package edu.uw.jkrishna.earls

data class Food(val category: FoodCategory, val name: String, val description: String, val price: Float)

object MenuUtils {

    fun getMenu() = listOf(
            Food(FoodCategory.PIZZA, "Cheese Pizza",
                    "Plain old pizza", 10.00f),
            Food(FoodCategory.PIZZA, "Hawaiin Pizza",
                    "Ham and pineapple", 13.00f),
            Food(FoodCategory.PIZZA, "Veggie Lovers",
                    "Mushroom, onions, olives & tomatoes", 14.00f),
            Food(FoodCategory.PIZZA, "Meat Lovers",
                    "Pepperoni, sausage & meatball", 14.00f),
            Food(FoodCategory.PIZZA, "White Pizza",
                    "Garlic butter sauce, spinach, basil, parmesan & Feta", 14.00f),
            Food(FoodCategory.PIZZA, "The Chuck Norris",
                    "Ranch dressing, chicken, onion & buffalo sauce", 15.00f),
            Food(FoodCategory.PIZZA, "BBQ Chicken",
                    "BBQ Sauce, chicken & onions", 15.00f),
            Food(FoodCategory.SANDWICHES,
                    "Meatball Parmigiano",
                    "Paul's meatballs & marinara with whole milk mozzarella on a grinder roll",
                    8.00f),
            Food(FoodCategory.SANDWICHES, "Philly Cheese Steak",
                    "Sliced roast beef with Paul's special saute of onions & mushrooms with provolone cheese on a hoagie roll", 8.00f),
            Food(FoodCategory.SANDWICHES, "Grilled Cheese & Fries",
                    "White American cheese on a rustic sourdough bread", 6.00f),
            Food(FoodCategory.SANDWICHES, "BLT",
                    "Classic BLT on sourdough", 7.00f),
            Food(FoodCategory.BURGERS, "Cheese burgers",
                    "Classic 1/4 LB. cheese burgers", 6.00f),
            Food(FoodCategory.BURGERS, "Swiss Burger",
                    "Classic 1/4 LB. cheese burgers Paul's special saute of onions & mushrooms with swiss cheese", 7.00f),
            Food(FoodCategory.BURGERS, "Hot Chipotle Burger",
                    "Classic 1/4 cheese burgers with hot sauce", 7.00f),
            Food(FoodCategory.BURGERS, "Veggie Burger",
                    "A vegetarian alternative to our classic 1/4 LB. cheese burgers", 7.00f),
            Food(FoodCategory.WINGS,
                    "Buffalo Wings",
                    "Our famous tender bone-in wings with our classic buffalo sauce",
                    10.00f),
            Food(FoodCategory.WINGS,
                    "Honey BBQ Wings",
                    "Our famous tender bone-in wings with our classic honey bbq sauce",
                    10.00f),
            Food(FoodCategory.WINGS, "Dave's Sauce Wings",
                    "Our famous tender bone-in wings with our secret extra-hot sauce", 10.00f),
            Food(FoodCategory.WINGS, "Paul's Italian Garlic",
                    "Our famous tender bone-in wings with our special house sauce", 10.00f),
            Food(FoodCategory.FRIES, "Waffle Fries",
                    "A serving of our famous waffle fries", 6.00f),
            Food(FoodCategory.FRIES, "Cheese Fries",
                    "A serving of our famous waffle fries topped with melted cheddar", 11.00f),
            Food(FoodCategory.PIZZA, "Bacon Cheese Fries",
                    "A serving of our famous waffle fries topped with melted cheddar and bacon", 12.00f)
    )

    fun getFoodCategoryOfFood(category: FoodCategory) = getMenu().filter { it.category == category }

}