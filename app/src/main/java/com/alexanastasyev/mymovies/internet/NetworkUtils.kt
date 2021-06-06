package com.alexanastasyev.mymovies.internet

object NetworkUtils {
    const val SERVICE_BASE_URL = "https://api.themoviedb.org/3/"

    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    const val IMAGE_LANDSCAPE_SIZE = "w1280"
    const val IMAGE_PORTRAIT_SIZE = "w1280"

    const val DEFAULT_LANGUAGE = "ru-RU"
    const val DEFAULT_REGION = "RU"

    const val API_KEY = "d0c314843449830d4310a88c038551d5"

    const val MIN_VOTES = 10

    const val SEARCH_DELAY_MILLISECONDS = 500L

    const val DATE_DELIMITER_SERVER = "-"
    const val DATE_DELIMITER = "."

    const val DATE_DAY_INDEX_SERVER = 2
    const val DATE_MONTH_INDEX_SERVER = 1
    const val DATE_YEAR_INDEX_SERVER = 0

    const val MOVIES_PER_PAGE = 20
}