package com.dicoding.ecobin.ui.helper
import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ReverseGeocodingTask(private val apiKey: String, private val listener: OnAddressFetchedListener) :
    AsyncTask<Double, Void, String>() {

    interface OnAddressFetchedListener {
        fun onAddressFetched(address: String)
    }

    override fun doInBackground(vararg params: Double?): String {
        val latitude = params[0]
        val longitude = params[1]

        val apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=$latitude,$longitude&key=$apiKey"

        val response = StringBuilder()
        var connection: HttpURLConnection? = null
        try {
            val url = URL(apiUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
        } catch (e: Exception) {
            Log.e("ReverseGeocodingTask", "Error retrieving geocoding data: ${e.message}")
        } finally {
            connection?.disconnect()
        }

        return response.toString()
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        parseGeocodingResponse(result)
    }

    private fun parseGeocodingResponse(response: String) {
        try {
            val jsonObj = JSONObject(response)
            if (jsonObj.getString("status") == "OK") {
                val results = jsonObj.getJSONArray("results")
                if (results.length() > 0) {
                    val formattedAddress = results.getJSONObject(0).getString("formatted_address")
                    listener.onAddressFetched(formattedAddress)
                }
            } else {
                Log.e("ReverseGeocodingTask", "Geocoding API response status not OK")
            }
        } catch (e: Exception) {
            Log.e("ReverseGeocodingTask", "Error parsing geocoding response: ${e.message}")
        }
    }
}
