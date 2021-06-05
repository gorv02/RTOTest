package com.example.rtotest.dataGenerator

import com.example.rtotest.model.TrafficSigns

fun listTrafficIcons(size: Int): List<TrafficSigns> {
    val list = mutableListOf<TrafficSigns>()
    for (i in 1..size) {
        when {
            i % 7 == 1 -> list.add(
                TrafficSigns(
                    "https://homepages.cae.wisc.edu/~ece533/images/boat.png",
                    "Sample 1"
                )
            )
            i % 7 == 2 -> list.add(
                TrafficSigns(
                    "https://homepages.cae.wisc.edu/~ece533/images/cat.png",
                    "Sample 2"
                )
            )
            i % 7 == 3 -> list.add(
                TrafficSigns(
                    "https://homepages.cae.wisc.edu/~ece533/images/fruits.png",
                    "Sample 3"
                )
            )
            i % 7 == 4 -> list.add(
                TrafficSigns(
                    "https://homepages.cae.wisc.edu/~ece533/images/girl.png",
                    "Sample 4"
                )
            )
            i % 7 == 5 -> list.add(
                TrafficSigns(
                    "https://homepages.cae.wisc.edu/~ece533/images/pool.png",
                    "Sample 5"
                )
            )
            i % 7 == 6 -> list.add(
                TrafficSigns(
                    "https://homepages.cae.wisc.edu/~ece533/images/tulips.png",
                    "Sample 6"
                )
            )
            else -> list.add(
                TrafficSigns(
                    "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
                    "Sample 7"
                )
            )
        }
    }
    return list
}