package com.example.moviemagic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*


class MovieViewModel : ViewModel() {
    // num of tickets
    private var _tickets: MutableLiveData<Int> = MutableLiveData()
    val tickets: LiveData<Int> get() = _tickets

    // day/date selection
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    // time selection
    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    // seat location selection
    private val _seatLoc = MutableLiveData<String>()
    val seatLoc: LiveData<String> = _seatLoc

    // cost of tickets
    private val _cost = MutableLiveData<Double>(0.00)
    val cost: LiveData<Double> = _cost


    // list of dates
    val dateList = getDateOptions()

    // list of seat locations
    val seatLocationList = getSeatLocations()

    // setter methods
    fun setTickets(num: Int) {
        _tickets.value = num
    }

    fun setDate(day: String) {
        _date.value = day
    }

    fun setTime(time: String) {
        _time.value = time
    }

    fun setSeatLoc(loc: String) {
        _seatLoc.value = loc
        calculateCost()
    }


    /* Generates a list of dates starting with the current date and the following three dates */
    private fun getDateOptions(): List<String> {
        // create a list of strings to hold the dates
        val options = mutableListOf<String>()
        // Format the date to be displayed in the radio buttons
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        // Get the current date
        val calendar = Calendar.getInstance()
        // Create a list of dates starting with the current date and the following 3 dates
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    /* Generates a list of seat location with price displayed */
    private fun getSeatLocations(): List<String> {
        val options = mutableListOf<String>()
        options.add("Front Row (100.00)")
        options.add("Middle Row (200.00)")
        options.add("Balcony (300.00)")
        options.add("Premium (600.00)")
        return options
    }

    /* Calculates the cost of the tickets
    based on the number of tickets and the seat location */
    fun calculateCost() {
        var cost = 100.00
        if (_seatLoc.value != null) {
            cost = when (seatLoc.value) {
                seatLocationList[0] -> 100.00
                seatLocationList[1] -> 200.00
                seatLocationList[2] -> 300.00
                seatLocationList[3] -> 600.00
                else -> 0.00
            }
        }
        _cost.value = cost * _tickets.value!!
    }


}