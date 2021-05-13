package com.example.rtotest.dataGenerator

import com.example.rtotest.model.PracticeQuestion

fun listPracticeQueAns(size: Int): List<PracticeQuestion> {
    val list = mutableListOf<PracticeQuestion>()

    for (i in 1..size) {
        val que = when {
            i % 3 == 1 -> "You can overtake a vehicle in front ___"
            i % 3 == 2 -> "Vehicle proceeding from the opposite direction" +
                    " should be allowed to pass through ___"
            else -> "Driving of a vehicle may overtake ___"
        }

        val options = when {
            i % 3 == 1 -> listOf(
                    "Through the right side of the vehicle.",
                    "Through the left side.",
                    "Through the left side if the road is wide.")
            i % 3 == 2 -> listOf(
                    "Your right side.",
                    "Your left side.",
                    "The convenient side.")
            else -> listOf(
                    "while driving down hill.",
                    "If the road is sufficiently wide.",
                    "When the driver of the vehicle in front shows the signal to overtake.")
        }

        val ans = when {
            i % 3 == 1 -> 1
            i % 3 == 2 -> 1
            else -> 3
        }

        list.add(PracticeQuestion(i, que, options, ans))
    }
    return list
}