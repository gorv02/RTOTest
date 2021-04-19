package com.example.rtotest.dataGenerator

import com.example.rtotest.model.PracticeQuestion

fun listPracticeQueAns(size: Int): List<PracticeQuestion>{
    val list = mutableListOf<PracticeQuestion>()

    for (i in 1..size){
        val que = when{
            i%3 == 1 -> "A piece of ice is dropped in a vesel containing kerosene. " +
                    "When ice melts, the level of kerosene will"
            i%3 == 2 -> "An artificial Satellite revolves round the Earth in circular" +
                    " orbit, which quantity remains constant?"
            else -> "If electrical conductivity increases with the increase of " +
                    "temperature of a substance, then it is a?"
        }

        val options = when{
            i%3 == 1 -> listOf("Rise","Fall","Remain Same","NOTA")
            i%3 == 2 -> listOf("Angular Momentum" , "Linear Velocity",
            "Angular Displacement", "NOTA")
            else -> listOf("Conductor" , "Semiconductor",
            "Insulator" , "Carborator")
        }

        val ans = when{
            i%3 == 1 -> 2
            i%3 == 2 -> 1
            else -> 2
        }

        list.add(PracticeQuestion(i ,que, options, ans))
    }
    return list
}