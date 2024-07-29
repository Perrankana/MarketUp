package com.perrankana.marketup.stock.repositories

interface FormatRepository {
    fun getFormats(): List<String>
    fun saveFormat(format: String) : List<String>
}

object FormatRepositoryImpl: FormatRepository{
    private val formats = mutableListOf("A4", "A5", "A6")

    override fun getFormats(): List<String> = formats

    override fun saveFormat(format: String): List<String> {
        formats.add(format)
        return formats
    }
}