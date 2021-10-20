package com.example.aibmoregetandpostrequestsbonus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
const val BASE_URL="https://dojo-recipes.herokuapp.com/"
class MainActivity : AppCompatActivity() {
    lateinit var EDT:EditText
    lateinit var BTNSHOW:Button
    lateinit var BTNADD:Button
    lateinit var RVmain:RecyclerView
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EDT=findViewById(R.id.edt)
        BTNADD=findViewById(R.id.btnadd)
        BTNSHOW=findViewById(R.id.btnshow)
        RVmain=findViewById(R.id.rv)

        RVmain.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        RVmain.layoutManager=linearLayoutManager

        BTNSHOW.setOnClickListener {
            getMyData()
        }

        BTNADD.setOnClickListener {
            var f = MyDataItem(EDT.text.toString())

            addSingleuser(f, onResult = {

                EDT.setText("")

                Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
            })
        }






    }

    private fun addSingleuser(f: MyDataItem, onResult: () -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)



        if (apiInterface != null) {
            apiInterface.addUser(f).enqueue(object : Callback<MyDataItem> {
                override fun onResponse(
                    call: Call<MyDataItem>,
                    response: Response<MyDataItem>
                ) {

                    onResult()

                }

                override fun onFailure(call: Call<MyDataItem>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();


                }
            })
        }

    }

    private fun getMyData() {
        val retorfitBuilder= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val retrofitData =retorfitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>,
            ) {
                val responseBody=response.body()!!

                recyclerViewAdapter = RecyclerViewAdapter(baseContext,responseBody)
                recyclerViewAdapter.notifyDataSetChanged()
                RVmain.adapter=recyclerViewAdapter



            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {


            }
        })
    }
}