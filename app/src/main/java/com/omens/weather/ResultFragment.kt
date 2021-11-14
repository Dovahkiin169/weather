package com.omens.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.omens.weather.databinding.FragmentSecondBinding
import com.omens.weather.model.Main
import com.omens.weather.model.WeatherCityResponse
import com.omens.weather.utils.NavigationEventHandler
import com.squareup.picasso.Picasso

class ResultFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentSecondBinding? = null
    private var listener: NavigationEventHandler? = null

    lateinit var weatherResponse: WeatherCityResponse
    lateinit var context: AppCompatActivity

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        weatherResponse = listener?.getObject()!!
        for(data in weatherResponse.weather!!.indices){
            val tmpData = weatherResponse.weather!![data]
            val iconUrl = "http://openweathermap.org/img/w/" + tmpData!!.icon + ".png"
            Picasso.get().load(iconUrl).resize(150, 150 ).centerCrop().into(binding.weatherImageView)
        }
        val mainWeatherInfo = weatherResponse.main as Main
        binding.textViewTemp.text = resources.getString(R.string.temp,listener!!.kelvinToCelsius(mainWeatherInfo.temp))
        binding.textViewFeelsLike.text = resources.getString(R.string.feels_like,listener!!.kelvinToCelsius(mainWeatherInfo.feels_like))
        binding.textViewTempMin.text = resources.getString(R.string.temp_min,listener!!.kelvinToCelsius(mainWeatherInfo.temp_min))
        binding.textViewTempMax.text = resources.getString(R.string.temp_max,listener!!.kelvinToCelsius(mainWeatherInfo.temp_max))
        binding.textViewPressure.text = resources.getString(R.string.pressure,mainWeatherInfo.pressure)
        binding.textViewHumidity.text = resources.getString(R.string.humidity,mainWeatherInfo.humidity)

        val fragment = childFragmentManager.findFragmentById(R.id.my_map) as SupportMapFragment?
        fragment!!.getMapAsync(this)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as AppCompatActivity
        if (context is NavigationEventHandler) {
            listener = context
        } else {
            throw RuntimeException("$context must implement NavigationEventHandler")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(weatherResponse.coord!!.lat.toDouble(), weatherResponse.coord!!.lon.toDouble()))
                .title("Marker")
        )

        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(weatherResponse.coord!!.lat.toDouble(), weatherResponse.coord!!.lon.toDouble())) // Center Set
            .zoom(6.0f)
            .build()

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}