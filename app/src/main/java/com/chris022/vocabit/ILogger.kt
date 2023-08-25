package com.chris022.vocabit

interface ILogger {
    fun warn(msg: String)
    fun info(msg: String)
    fun error(msg:String)
    fun debug(msg:String)
}