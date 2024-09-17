package trucks

import items.Item
import items.ItemCategory
import items.ItemType
import kotlin.random.Random


// TruckGenerator.kt
class TruckGenerator {
    fun generateTruck(): Truck {
        // Логика генерации грузовика
        val type = TruckType.entries.toTypedArray().random()
        val cargoType = if (Random.nextBoolean()) "Food" else "Non-Food"
        val items = generateItems(type.capacity, cargoType)
        return Truck(type = type, items = items, cargoType = cargoType)
    }

    private fun generateItems(capacity: Int, cargoType: String): MutableList<Item> {
        val items = mutableListOf<Item>()
        val itemTypes = if (cargoType == "Food") {
            listOf(ItemType.FOOD)
        } else {
            listOf(ItemType.LARGE, ItemType.MEDIUM, ItemType.SMALL)
        }
        var currentWeight = 0

        while (currentWeight < capacity) {
            val itemType = itemTypes.random()
            val itemWeight = itemType.weight
            val itemCategory = ItemCategory.entries.random()
            if (currentWeight + itemWeight <= capacity) {
                items.add(Item(type = itemType.name, category = itemCategory.displayName, weight = itemWeight, loadingTime = 5))
                currentWeight += itemWeight
            } else {
                break
            }
        }

        return items
    }
}

