import items.Storage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ports.LoadingPort
import ports.UnloadingPort
import queues.LoadingQueue
import queues.UnloadingQueue
import trucks.Truck
import trucks.TruckGenerator

fun main() {
    val storage = Storage()
    val unloadingPorts = List(8) { UnloadingPort(storage) }
    val loadingPorts = List(2) { LoadingPort(storage) }
    val unloadingQueue = UnloadingQueue()
    val loadingQueue = LoadingQueue()
    val truckGenerator = TruckGenerator()
    val truckChannel = Channel<Truck>()

    // Генерация грузовиков для разгрузки
    runBlocking {
        launch {
            while (true) {
                delay(60000) // Генерация грузовика каждую минуту
                val truck = truckGenerator.generateTruck()
                truckChannel.send(truck)
                println("Generated truck for unloading: $truck")
            }
        }

        // Обработка грузовиков из канала
        launch {
            for (truck in truckChannel) {
                unloadingQueue.addTruck(truck)
            }
        }

        // Разгрузка грузовиков
        launch {
            while (true) {
                for (port in unloadingPorts) {
                    if (port.isFree()) {
                        val truck = unloadingQueue.getNextTruck()
                        if (truck != null) {
                            port.startUnloading(truck)
                            println("Started unloading truck: $truck")
                            // После разгрузки добавляем грузовик в очередь на загрузку
                            loadingQueue.addTruck(truck)
                        }
                    }
                }
                delay(1000) // Проверка каждую секунду
            }
        }

        // Загрузка грузовиков
        launch {
            while (true) {
                for (port in loadingPorts) {
                    if (port.isFree()) {
                        val truck = loadingQueue.getNextTruck()
                        if (truck != null) {
                            port.startLoading(truck)
                            println("Started loading truck: $truck")
                        }
                    }
                }
                delay(1000) // Проверка каждую секунду
            }
        }
    }
}


