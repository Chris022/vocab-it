package com.chris022.vocabit

import android.util.Log
import javax.inject.Inject

class ConsoleLogger @Inject constructor() : ILogger {
    override fun warn(msg: String) {
        Log.println(Log.WARN,"app",msg)
    }

    override fun info(msg: String) {
        Log.println(Log.INFO,"app",msg)
    }

    override fun error(msg: String) {
        Log.println(Log.ERROR,"app",msg)
    }

    override fun debug(msg: String) {
        Log.println(Log.DEBUG,"app",msg)
    }
}