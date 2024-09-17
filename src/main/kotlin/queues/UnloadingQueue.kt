package queues

import trucks.Truck

// queues.UnloadingQueue.kt
class UnloadingQueue {
    private val queue = mutableListOf<Truck>()

    fun addTruck(truck: Truck) {
        queue.add(truck)
    }

    fun getNextTruck(): Truck? {
        return if (queue.isNotEmpty()) queue.removeAt(0) else null
    }
}
