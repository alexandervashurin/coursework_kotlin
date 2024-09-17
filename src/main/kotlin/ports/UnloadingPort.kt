package ports


import items.Storage
import kotlinx.coroutines.delay
import trucks.Truck

// UnloadingPort.kt
class UnloadingPort(private val storage: Storage) {
    private var truck: Truck? = null

    suspend fun startUnloading(truck: Truck) {
        this.truck = truck
        // Логика разгрузки
        println("Started unloading truck: $truck")
        // Имитация разгрузки
        for (item in truck.items) {
            storage.addItems(listOf(item))
            delay(item.loadingTime.toLong())
        }
        finishUnloading()
    }

    private fun finishUnloading() {
        truck = null
        println("Finished unloading")
    }

    fun isFree(): Boolean {
        return truck == null
    }
}
