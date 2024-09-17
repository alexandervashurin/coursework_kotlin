package queues

import trucks.Truck


// queues.LoadingQueue.kt
class LoadingQueue {
    private val queue = mutableListOf<Truck>()

    fun addTruck(truck: Truck) {
        queue.add(truck)
    }

    fun getNextTruck(): Truck? {
        return if (queue.isNotEmpty()) queue.removeAt(0) else null
    }
}
