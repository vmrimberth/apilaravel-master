package com.apilaravel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.apilaravel.databinding.ActivityDetalleCancionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DetalleCancionActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetalleCancionBinding

    private var cancion: CancionData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleCancionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras != null){
            cancion = intent.extras!!.getSerializable("cancion") as CancionData?
            binding.tituloEditText.setText(cancion?.titulo)
            binding.artistaEditText.setText(cancion?.artista)
            binding.albumEditText.setText(cancion?.album)
            binding.yearEditText.setText(cancion?.year)
            binding.generoEditText.setText(cancion?.genero)
            binding.formatoEditText.setText(cancion?.formato)
            binding.imagenEditText.setText(cancion?.image)
            binding.duracionEditText.setText(cancion?.duracion)
        }

        binding.saveCancionButton.setOnClickListener {
            onClickSaveCancion()
        }

        binding.volver.setOnClickListener {
            onBackPressed()
        }
//        var actionBar = supportActionBar
//        actionBar!!.title = "Gestion de canciones"
//        actionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClickSaveCancion() {
        if(cancion == null){
            cancion = CancionData(
                titulo = binding.tituloEditText.text.toString(),
                artista = binding.artistaEditText.text.toString(),
                album = binding.albumEditText.text.toString(),
                year = binding.yearEditText.text.toString(),
                genero = binding.generoEditText.text.toString(),
                formato = binding.formatoEditText.text.toString(),
                image = binding.imagenEditText.text.toString(),
                duracion = binding.duracionEditText.text.toString()
            )

            binding.progressbar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                val response: Response<Void> = CancionObject.ApiAdapter().saveCancion(cancion!!)

                // pasa el proceso al primer plano
                runOnUiThread {
                    binding.progressbar.visibility = View.GONE
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetalleCancionActivity,"Guardado correctamente",Toast.LENGTH_SHORT).show()
                        onBackPressed()
                    }
                }
            }
        }else{
            cancion!!.titulo = binding.tituloEditText.text.toString()
            cancion!!.artista = binding.artistaEditText.text.toString()
            cancion!!.album = binding.albumEditText.text.toString()
            cancion!!.year = binding.yearEditText.text.toString()
            cancion!!.genero = binding.generoEditText.text.toString()
            cancion!!.formato = binding.formatoEditText.text.toString()
            cancion!!.image = binding.imagenEditText.text.toString()
            cancion!!.duracion = binding.duracionEditText.text.toString()

            binding.progressbar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                val response: Response<Void> = CancionObject.ApiAdapter().updateCancion(cancion!!.id, cancion!!)

                // pasa el proceso al primer plano
                runOnUiThread {
                    binding.progressbar.visibility = View.GONE
                    if (response.isSuccessful) {
                        Toast.makeText(this@DetalleCancionActivity,"Guardado correctamente",Toast.LENGTH_SHORT).show()
                        onBackPressed()
                    }
                }
            }
        }

    }
}