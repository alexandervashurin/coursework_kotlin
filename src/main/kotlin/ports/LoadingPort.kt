package ports

import items.Storage
import kotlinx.coroutines.delay
import trucks.Truck

// LoadingPort.kt
class LoadingPort(private val storage: Storage) {

    private var truck: Truck? = null

    suspend fun startLoading(truck: Truck) {
        this.truck = truck
        // Логика загрузки
        println("Started loading truck: $truck")
        // Имитация загрузки
        val itemsToLoad = if (truck.cargoType == "Food") {
            storage.getItems("Food", truck.capacity)
        } else {
            storage.getItems("Non-Food", truck.capacity)
        }
        for (item in itemsToLoad) {
            truck.addItem(item)
            delay(item.loadingTime.toLong())
        }
        finishLoading()
    }

    private fun finishLoading() {
        truck = null
        println("Finished loading")
    }

    fun isFree(): Boolean {
        return truck == null
    }
}
