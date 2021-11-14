package com.omens.weather.db

import com.couchbase.lite.CouchbaseLiteException
import com.couchbase.lite.MutableDocument
import com.google.gson.Gson
import com.omens.weather.db.DataInterface.UserActionsListener
import com.omens.weather.model.ListWeatherCityResponse
import java.util.HashMap

class DataSetterAndGetter(private val dataFromDB: DataInterface.View) :
    UserActionsListener {
    val gson = Gson()
    var outputList : ListWeatherCityResponse? = null
    override fun fetchDataFormDB() {
        val database = DatabaseManager.database
        val docId = DatabaseManager.sharedInstance!!.currentDocId
        if (database != null) {
            val data: MutableMap<String, Any?> = HashMap()
            data["document"] = DatabaseManager.sharedInstance!!.currentDoc
            val document = database.getDocument(docId)
            if (document != null)
                outputList = gson.fromJson(document.getString("weatherList"), ListWeatherCityResponse::class.java)
            dataFromDB.setData(outputList)
        }
    }



    override fun saveDataToDb(data: MutableMap<String, Any>) {
        val database = DatabaseManager.database
        val docId = DatabaseManager.sharedInstance!!.currentDocId
        val mutableDocument = MutableDocument(docId, data)
        try {
            database!!.save(mutableDocument)
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }
}