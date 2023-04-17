fun main() {

    val executeProgram = true

    var parkingLots: Array<Car?>? = null

    cycle@ while (executeProgram) {
        val userInputArguments = readln().split(" ").toTypedArray()


        when {
            userInputArguments.first() == "exit" -> {
                return
            }
            parkingLots == null && userInputArguments.first() != "create"-> {
                println("Sorry, a parking lot has not been created.")
                continue@cycle
            }
            userInputArguments.first() == "create"->{
                val slots = userInputArguments[1].toInt()
                parkingLots = Array(slots){null}
                println("Created a parking lot with $slots spots.")
                continue@cycle
            }
            userInputArguments.first() == "park" -> {
                if (!parkingLots!!.contains(null)) {
                    println("Sorry, the parking lot is full.")
                }
                for (parkingIndex in parkingLots.indices) {
                    if (parkingLots[parkingIndex] == null) {
                        val carToBeParked =
                            Car(registrationNumber = userInputArguments[1], color = userInputArguments[2])
                        parkingLots[parkingIndex] = carToBeParked
                        println("${carToBeParked.color} car parked in spot ${parkingIndex + 1}.")
                        continue@cycle
                    }
                }
            }
            userInputArguments.first() == "leave" -> {
                val parkingId = userInputArguments[1].toInt() - 1
                if (parkingLots?.get(parkingId) == null) {
                    println("There is no car in spot ${parkingId + 1}.")
                } else {
                    parkingLots[parkingId] = null
                    println("Spot ${parkingId + 1} is free.")
                }
            }
            userInputArguments.first() == "status" -> {
                printStatus(parkingLots)
            }
            userInputArguments.first() == "reg_by_color" -> {
                val filteredCars = parkingLots?.filter {
                    val selectedColor = try { userInputArguments[1] }catch (_:Exception){null}
                    it?.color.equals(selectedColor, ignoreCase = true)
                }
                if (filteredCars.isNullOrEmpty()){
                    println("No cars with color ${try {
                        userInputArguments[1]} catch (_:Exception){"-"}} were found.")
                }else{
                    val result = mutableListOf<String?>()
                    filteredCars.forEach{
                        result.add(it?.registrationNumber)
                    }
                    println(result.joinToString(", "))
                }
            }
            userInputArguments.first() == "spot_by_color" -> {
                val filteredCars = parkingLots?.filter {
                    val selectedColor = try { userInputArguments[1] }catch (_:Exception){null}
                    it?.color.equals(selectedColor, ignoreCase = true)
                }
                if (filteredCars.isNullOrEmpty()){
                    println("No cars with color ${try {
                        userInputArguments[1]} catch (_:Exception){"-"}} were found.")
                }else{
                    val result = mutableListOf<Int>()
                    for (spot in parkingLots?.indices!!){
                        filteredCars.forEach {currentCar ->
                            if (parkingLots[spot] == currentCar) result.add(spot+1)
                        }

                    }
                    println(result.joinToString(", "))
                }
            }
            userInputArguments.first() == "spot_by_reg" -> {
                val foundCar = parkingLots?.find {
                    val selectedRegistrationNumber = try { userInputArguments[1] }catch (_:Exception){null}
                    it?.registrationNumber == selectedRegistrationNumber
                }
                if (foundCar == null){
                    println("No cars with registration number ${try {
                        userInputArguments[1]} catch (_:Exception){"-"}} were found.")
                }else{
                    for (spot in parkingLots?.indices!!){
                        if (foundCar == parkingLots[spot]){
                            println(spot+1)
                        }
                    }
                }
            }
        }
    }

}

fun printStatus(parkingLots: Array<Car?>?) {
    if (parkingLots?.all { it == null }!!){
        println("Parking lot is empty.")
    }else{
        parkingLots.indices.forEach {
            val currentCar = parkingLots[it]
            if (currentCar != null){
                println("${it+1} ${currentCar.registrationNumber} ${currentCar.color}")
            }
        }
    }
}

data class Car(
    val registrationNumber: String = "",
    val color: String = ""
)
