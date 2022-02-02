package com.wizeline.heroes

const val PRIVATE_KEY = "053d25888ac40843b19531dc3375edaed41e0157"
const val API_KEY = "a872fb0f7ece7efe4e8f309240db4720"
val TS: String get() = System.currentTimeMillis().toString()
val HASH: String get() = "$TS$PRIVATE_KEY$API_KEY".toMD5()