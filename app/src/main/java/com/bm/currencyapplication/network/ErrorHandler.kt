package com.bm.currencyapplication.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLPeerUnverifiedException

object ErrorHandler {

    private var message = ""
    private var jsonObject: JSONObject? = null

    fun handleFailureRequest(exception: Exception): String {
        when (exception) {
            is HttpException -> {

                try {
                    val response = exception.response()!!.errorBody()!!.string()
                    jsonObject = JSONObject(response)
                    message = try {
                        jsonObject!!.getString("message")
                    } catch (jsonException: JSONException) {
                        try {
                            jsonObject!!.getString("detail")
                        } catch (jsonException2: JSONException) {
                            "Server responded with an error,Please contact server admin"
                        }
                    }
                    try {
                        jsonObject!!.getInt("error_code")
                    } catch (jsonException: JSONException) {
                        if (exception.response()!!.code() == 400) {
                            message = "Server responded with an error,Please contact server admin"
                        } else if (exception.response()!!.code() == 401) {
                            message = "Invalid credentials"

                        } else if (exception.response()!!.code() == 403) {
                            message = "Invalid access token"

                        } else if (exception.response()!!.code() == 404) {
                            message = "Server not Found"
                        } else if (exception.response()!!.code() == 415) {
                            message = "Server responded with an error,Please contact server admin"
                        } else if (exception.response()!!.code() == 500) {
                            message = "Server responded with an error,Please contact server admin"
                        } else {
                            message = "Server responded with an error,Please contact server admin"

                        }
                    }
                } catch (jsonException: JSONException) {
                    message = if (exception.response()!!.code() == 500) {
                        "Server responded with an error,Please contact server admin"
                    } else {
                        "Server responded with an error,Please contact server admin"
                    }
                } catch (jsonException: IOException) {
                    message = if (exception.response()!!.code() == 500) {
                        "Server responded with an error,Please contact server admin"
                    } else {
                        "Server responded with an error,Please contact server admin"
                    }
                }
            }

            is UnknownHostException -> {

                message = "No internet connection, please check your connection and try again"
            }

            is SSLPeerUnverifiedException -> {
                message = exception.message.toString()

            }

            is ConnectException -> {
                message = "No internet connection, please check your connection and try again"


            }

            is SSLException -> {
                message = "No internet connection, please check your connection and try again"

            }


            else -> {
                message = exception.message.toString()

            }
        }
        return message

    }
}