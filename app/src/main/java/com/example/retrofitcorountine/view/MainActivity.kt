package com.example.retrofitcorountine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitcorountine.adapter.RecyclerAdapter
import com.example.retrofitcorountine.databinding.ActivityMainBinding
import com.example.retrofitcorountine.model.cryptoModel
import com.example.retrofitcorountine.service.cryptoAPI

import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private lateinit var cryprtoMOdels : ArrayList<cryptoModel>
    private lateinit var recyclerAdapter : RecyclerAdapter
    private  var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error : ${throwable.localizedMessage.toString() }")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        loadData()
    }


    private fun loadData() {
        val retfofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(cryptoAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retfofit.getData()

            withContext(Dispatchers.Main + exceptionHandler){
                if (response.isSuccessful){
                    response.body().let {it
                        cryprtoMOdels = ArrayList(it)
                        cryprtoMOdels.let { list ->
                            recyclerAdapter = RecyclerAdapter(list,this@MainActivity)
                            binding.recyclerview.adapter = recyclerAdapter
                        }
                    }
                }
            }
        }

    }



    override fun onItemClick(cryptoModel: cryptoModel) {
        Toast.makeText(this,"clicked: ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

}