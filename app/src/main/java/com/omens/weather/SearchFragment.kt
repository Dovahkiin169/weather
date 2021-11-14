package com.omens.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.omens.weather.databinding.FragmentFirstBinding
import com.omens.weather.model.WeatherCityResponse
import com.omens.weather.utils.NavigationEventHandler
import com.omens.weather.utils.getListLikeString

class SearchFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var listener: NavigationEventHandler? = null
    private lateinit var adapter: CustomAdapter
    lateinit var context: AppCompatActivity

    private val binding get() = _binding!!

    private lateinit var cityPlusCountryCode: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        cityPlusCountryCode = binding.editTextCityPlusCountryCode

        val recyclerview = binding.recyclerView
        recyclerview.layoutManager = LinearLayoutManager(context)
        val list : List<WeatherCityResponse> = listOf()
        adapter = CustomAdapter(list,listener!!)
        recyclerview.adapter = adapter
        recyclerview.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                listener?.quitApp()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButton.setOnClickListener {
            if (cityPlusCountryCode.text.toString().isNotEmpty()) {
                getListLikeString(cityPlusCountryCode.text.toString(), requireContext(), listener!!, adapter) { data ->
                    if (data != null)
                        listener?.saveToDB(data)
                }
            }
            else{
                Toast.makeText(context, "Field can't be empty", Toast.LENGTH_SHORT).show()
            }
        }
        if(listener!!.getData() != null){
            adapter.reloadList(listener!!.getData()!!)
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
}