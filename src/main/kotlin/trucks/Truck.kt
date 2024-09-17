package trucks

import items.Item


// Truck.kt
data class Truck(val type: TruckType,
                 var items: MutableList<Item> = mutableListOf(),
                 var cargoType: String? = null) {

    val capacity: Int
        get() = type.capacity

    fun addItem(item: Item) {
        items.add(item)
    }
}