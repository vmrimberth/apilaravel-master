package com.apilaravel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.apilaravel.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CancionAdapter
    private val cancionesList = mutableListOf<CancionData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        canciones()
        binding.listMusicButton.setOnClickListener {
            canciones()
        }

        binding.newCancionFAB.setOnClickListener {
            startActivity(Intent(this@MainActivity, DetalleCancionActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        canciones()
    }

    private fun initRecyclerView() {
        //Log.d("TEST","SUFFLE: "+ Arrays.toString(cancionesList.toTypedArray()))
        adapter = CancionAdapter(cancionesList)
        binding.rvCanciones.layoutManager = LinearLayoutManager(this)
        binding.rvCanciones.adapter = adapter
    }

    fun canciones() {
        CoroutineScope(Dispatchers.IO).launch {
            val can: Response<List<CancionData>> =
                CancionObject.ApiAdapter().obtenerCanciones()
            val objcancion = can.body()!!
            runOnUiThread {
                if (can.isSuccessful) {
                    val clist = fillList(objcancion)
                    cancionesList.clear()
                    cancionesList.addAll(clist)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun fillList(list: List<CancionData>): MutableList<CancionData> {
        val _res = mutableListOf<CancionData>()
        for (item: CancionData in list) {
            if (!item.artista.isNullOrEmpty()) {
                _res.add(item)
            }
        }
        return _res
    }
}