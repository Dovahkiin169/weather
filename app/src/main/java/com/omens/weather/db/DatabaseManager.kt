package com.omens.weather.db

import android.content.Context
import android.util.Log
import com.couchbase.lite.*

open class DatabaseManager protected constructor() {
    private var listenerToken: ListenerToken? = null
    var currentDoc: String? = null
    fun initCouchbaseLite(context: Context?) {
        CouchbaseLite.init(context!!)
    }

    val currentDocId: String
        get() = "document::$currentDoc"

    fun openOrCreateDatabase(context: Context, doc: String?) {
        val config = DatabaseConfiguration()
        config.directory = String.format("%s/%s", context.filesDir, doc)
        currentDoc = doc
        try {
            val dbName = "data"
            database = Database(dbName, config)
            registerForDatabaseChanges()
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }

    private fun registerForDatabaseChanges() {
        listenerToken = database!!.addChangeListener { change: DatabaseChange ->
            for (docId in change.documentIDs) {
                val doc =
                    database!!.getDocument(
                        docId!!
                    )
                if (doc != null) Log.i(
                    "DatabaseChangeEvent",
                    "Document was added/updated"
                ) else Log.i("DatabaseChangeEvent", "Document was deleted")
            }
        }
    }

    fun closeDatabase() {
        try {
            if (database != null) {
                deregisterForDatabaseChanges()
                database!!.close()
                database = null
            }
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
    }

    private fun deregisterForDatabaseChanges() {
        if (listenerToken != null) database!!.removeChangeListener(
            listenerToken!!
        )
    }

    companion object {
        var database: Database? = null
            private set
        private var instance: DatabaseManager? = null
        val sharedInstance: DatabaseManager?
            get() {
                if (instance == null) instance = DatabaseManager()
                return instance
            }
    }
}