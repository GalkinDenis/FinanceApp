package ru.denis.financeApp.interfaces

import java.io.IOException

interface TrendInterface {
    @Throws(IOException::class)
    fun find(v: String?)
    fun setAdapter(data: Array<Array<String>>)
    fun setProgressBar(i: Int)
}