package com.omens.weather

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.gson.Gson
import com.omens.weather.databinding.ActivityMainBinding
import com.omens.weather.db.DataInterface
import com.omens.weather.db.DataSetterAndGetter
import com.omens.weather.db.DatabaseManager
import com.omens.weather.model.ListWeatherCityResponse
import com.omens.weather.model.WeatherCityResponse
import com.omens.weather.utils.NavigationEventHandler
import java.math.BigDecimal
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), NavigationEventHandler, DataInterface.View {
    private var actionListener: DataInterface.UserActionsListener? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    lateinit var weatherCity : WeatherCityResponse
    private var listWeatherCityResponse: ListWeatherCityResponse? = null

    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbMgr = DatabaseManager.sharedInstance
        dbMgr!!.initCouchbaseLite(applicationContext)
        dbMgr.openOrCreateDatabase(applicationContext, "nameOfDocument")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController!!.graph)
        actionListener = DataSetterAndGetter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun showHideProgressbar(visibility: Boolean) {
        if(visibility)
            binding.mainContent.loadingBarMain.visibility = View.VISIBLE
        else
            binding.mainContent.loadingBarMain.visibility = View.GONE
    }

    override fun quitApp() {
        AlertDialog.Builder(this).setMessage(resources.getString(R.string.dialog_closing_app))
            .setCancelable(false)
            .setPositiveButton(resources.getString(R.string.dialog_yes)) { _, _ ->
                this@MainActivity.finish()
                exitProcess(0)
            }
            .setNegativeButton(resources.getString(R.string.dialog_no), null)
            .show()
    }

    override fun saveToDB(list: ListWeatherCityResponse) {
        val data: MutableMap<String, Any> = HashMap()
        DatabaseManager.sharedInstance?.currentDoc?.let { data.put("document", it) }

        val gson = Gson()
        val jsonString = gson.toJson(list)

        data["weatherList"] = jsonString
        actionListener?.saveDataToDb(data)

        actionListener!!.fetchDataFormDB()
    }

    override fun loadFromDB() {
        actionListener!!.fetchDataFormDB()
    }

    override fun getObject(): WeatherCityResponse {
        return weatherCity
    }

    override fun setObject(response: WeatherCityResponse) {
        weatherCity = response
    }

    override fun setData(outputList: ListWeatherCityResponse?) {
        listWeatherCityResponse = outputList!!
    }

    override fun getData(): List<WeatherCityResponse>? {
        return if(listWeatherCityResponse != null)
            listWeatherCityResponse!!.list
        else
            null
    }

    override fun onDestroy() {
        super.onDestroy()
        DatabaseManager.sharedInstance!!.closeDatabase()
    }

    override fun kelvinToCelsius(tempInK: BigDecimal): BigDecimal {
        return tempInK-BigDecimal.valueOf(273.15)
    }

    override fun navigateToFragment(id: Int) {
        navController!!.navigate(id)
    }


}