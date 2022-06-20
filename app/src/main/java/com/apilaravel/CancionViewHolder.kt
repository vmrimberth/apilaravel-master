package com.apilaravel

import android.content.DialogInterface
import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.apilaravel.databinding.ItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class CancionViewHolder (val view: View): RecyclerView.ViewHolder(view){

    private val binding = ItemBinding.bind(view)

    var listCovers = listOf<Int>(
        R.drawable.c01,
        R.drawable.c02,
        R.drawable.c03,
        R.drawable.c04,
        R.drawable.c05,
        R.drawable.c06,
        R.drawable.c07,
        R.drawable.c08,
    )


    fun bind(cancion:CancionData){
        val cover = (0..7).random()
        binding.ivCover.setImageResource(listCovers[cover])
        binding.tvArtista.text = cancion.artista
        binding.tvTitle.text = cancion.titulo
        binding.mbMore.setOnClickListener { view ->
            showMoreActionsMenu(view, cancion)
        }
    }

    private fun showMoreActionsMenu(button: View, cancion:CancionData){
        val popupMenu = PopupMenu(this.view.context, button)
        popupMenu.menuInflater.inflate(R.menu.card_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                return when(item.itemId){
                    R.id.editar -> {
                        var intent = Intent(view.context, DetalleCancionActivity::class.java)
                        intent.putExtra("cancion", cancion)
                        view.context.startActivity(intent)
                        true
                    }
                    R.id.eliminar -> {
                        //showMessage(item.title)
                        var dialog = AlertDialog.Builder(view.context)
                        dialog.setTitle("CONFIRMAR")
                        dialog.setMessage("Eliminar cancion ${cancion.titulo} de ${cancion.artista}?")
                        dialog.setPositiveButton("Si", object : DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                if(cancion != null) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val response: Response<Void> = CancionObject.ApiAdapter().deleteCancion(cancion!!.id)
                                        runOnUiThread {
                                            if (response.isSuccessful) {
                                                //notifyDataSetChanged()
                                                p0!!.dismiss()
                                            }
                                        }
                                    }
                                }
                            }
                        })
                        dialog.setNegativeButton("Cancelar", null)
                        dialog.show()
                        true
                    }
                    else -> false
                }
            }
        })

        popupMenu.show()
    }

    private fun showMessage(title: CharSequence) {
        Toast.makeText(this.view.context, title, Toast.LENGTH_SHORT).show()
    }
}

private fun CoroutineScope.runOnUiThread(function: () -> Unit) {

}
