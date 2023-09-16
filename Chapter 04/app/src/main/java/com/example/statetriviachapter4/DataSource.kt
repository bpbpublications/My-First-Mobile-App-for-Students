package com.example.statetriviachapter4

import com.example.statetriviachapter4.models.State

fun getStateData(): List<State> {
    val states = mutableListOf<State>()
    states.add(State(name = "Rajasthan", title = "Land of Kings"))
    states.add(State(name = "Kerala", title = "Gods' Own Country"))
    states.add(State(name = "Punjab", title = "Land of Five Rivers"))
    states.add(State(name = "Bihar", title = "Land of Buddha"))
    states.add(State(name = "Goa", title = "Party capital of India"))
    states.add(State(name = "Uttar Pradesh", title = "Land of the Taj Mahal"))
    states.add(State(name = "Tamil Nadu", title = "Land of Temples"))
    states.add(State(name = "Maharashtra", title = "Gateway of India"))
    states.add(State(name = "Gujarat", title = "Land of the Mahatma"))
    states.add(State(name = "Madhya Pradesh", title = "Heart of India"))
    states.add(State(name = "Jammu and Kashmir", title = "Paradise on Earth"))
    return states
}