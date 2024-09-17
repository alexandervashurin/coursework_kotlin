package items

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// Storage.kt
class Storage {
    private val items = mutableListOf<Item>()
    private val mutex = Mutex()

    suspend fun addItems(items: List<Item>) {
        mutex.withLock {
            this.items.addAll(items)
        }
    }

    suspend fun getItems(type: String, maxWeight: Int): List<Item> {
        val selectedItems = mutableListOf<Item>()
        var currentWeight = 0

        mutex.withLock {
            for (item in items.filter { it.type == type }) {
                if (currentWeight + item.weight <= maxWeight) {
                    selectedItems.add(item)
                    currentWeight += item.weight
                } else {
                    break
                }
            }

            // Удаляем выбранные товары из склада
            items.removeAll(selectedItems)
        }

        return selectedItems
    }
}
