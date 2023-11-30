package com.example.smartfood

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfood.Adapter.SupplierAdapter
import com.example.smartfood.ModelResponse.SupplierResponse
import com.example.smartfood.Request.SupplierRequest
import com.example.smartfood.Service.APIServiceSupplier
import com.example.smartfood.databinding.FragmentSupplierBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SupplierFragment : Fragment() {
    private lateinit var binding: FragmentSupplierBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SupplierAdapter
    private val supplierList = mutableListOf<SupplierResponse>()

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentSupplierBinding.inflate(inflater)


       // Configura el RecyclerView y el adaptador
       recyclerView = binding.rcyViewSupplier
       recyclerView.layoutManager = LinearLayoutManager(requireContext())

       adapter = SupplierAdapter(supplierList)
       recyclerView.adapter = adapter

       searchAllSupplier()

       //FloatingButton
       binding.floatingButton.setOnClickListener{nextFragment()}
       return binding.root
   }

    private fun nextFragment() {
        // Infla el layout del Custom Dialog
        val dialogView: View = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout_supplier, null)

        // Crea un AlertDialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        // Crea el AlertDialog
        val alertDialog = builder.create()

        dialogView.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val nombre = dialogView.findViewById<EditText>(R.id.editTextNombre).text.toString()
            val ruc = dialogView.findViewById<EditText>(R.id.editTextRUC).text.toString()
            val direccion = dialogView.findViewById<EditText>(R.id.editTextDireccion).text.toString()
            val supplierRequest = SupplierRequest(nombre, ruc, direccion)
            addSupplier(supplierRequest)
            alertDialog.dismiss() // Cerrar el diálogo después de agregar el proveedor.
        }

        dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            alertDialog.dismiss()
        }

        // Muestra el AlertDialog
        alertDialog.show()
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://smartfood1-4d588e0d10e0.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchAllSupplier(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIServiceSupplier::class.java).getAllSuplier("supplier/all")
            val sup = call.body()
            //Variable donde esta la respuesta
            withContext(Dispatchers.Main){
                if(call.isSuccessful){
                    val supliers = sup?: emptyList()
                    supplierList.clear()
                    supplierList.addAll(supliers)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }

    private fun addSupplier(supplierRequest: SupplierRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIServiceSupplier::class.java).addSupplier(supplierRequest)
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    searchAllSupplier()
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
    }
}