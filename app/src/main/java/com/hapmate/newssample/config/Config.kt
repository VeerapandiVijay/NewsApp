package com.hapmate.newssample.config

import java.text.SimpleDateFormat

object Config {

    const val BASE_URL = "https://newsapi.org/v2/"

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    val displayDateFormat = SimpleDateFormat("YYYY-MM-dd HH:MM")
}