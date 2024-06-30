package com.perrankana.marketup

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform