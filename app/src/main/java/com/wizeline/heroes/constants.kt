package com.wizeline.heroes

import com.wizeline.heroes.BuildConfig.API_KEY

const val PRIVATE_KEY = "053d25888ac40843b19531dc3375edaed41e0157"
val TS: String get() = System.currentTimeMillis().toString()
val HASH: String get() = "$TS$PRIVATE_KEY$API_KEY".toMD5()