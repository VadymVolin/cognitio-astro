package com.cognitio.astro.util

object UrlConstants {
    const val MOCK_URL = "https://temp.com/"

    /**
     * NASA GOV API url
     */
    const val BASE_NASA_GOV_URL = "api.nasa.gov"

    const val HTTPS_PROTOCOL = "https"
    const val HTTP_PROTOCOL = "http"

    const val API_KEY_PARAM = "api_key"         // string	    DEMO_KEY	api.nasa.gov key for expanded usage

    /**
     * APOD request
     */
    const val APOD_PATH = "planetary/apod"
    /**
     * APOD query params
     */
    const val DATE_PARAM = "date"               // YYYY-MM-DD	today	The date of the APOD image to retrieve
    const val START_DATE_PARAM = "start_date"   // YYYY-MM-DD	none	The start of a date range, when requesting date for a range of dates. Cannot be used with date.
    const val END_DATE_PARAM = "end_date"       // YYYY-MM-DD	today	The end of the date range, when used with start_date.
    const val COUNT_PARAM = "count"             // int	        none	If this is specified then count randomly chosen images will be returned. Cannot be used with date or start_date and end_date.
    const val THUMBS_PARAM = "thumbs"           // bool	        False	Return the URL of video thumbnail. If an APOD is not a video, this parameter is ignored.

    /**
     * FIREBASE related constants
     */
    const val ASTRO_PHOTO_STORAGE_REF = "astro_photo"
    const val MOON_STORAGE_REF = "moon"

}