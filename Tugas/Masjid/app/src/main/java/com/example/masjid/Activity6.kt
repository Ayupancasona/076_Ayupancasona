package com.example.masjid

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_6.*
import org.json.JSONArray
import org.json.JSONObject

class Activity6 : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_6)
        getdariserver()

        btnSimpan6.setOnClickListener() {
            val data_judul: String = judul_slideshow.text.toString()
            val data_url: String = url_slide.text.toString()
            postkeserve(data_judul, data_url)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
        @SuppressLint("WrongConstant")
        fun getdariserver() {
            val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

            val users = ArrayList<User>()


            AndroidNetworking.get("https://projectmenumasjid.000webhostapp.com/masjid/slideshow_json.php")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.e("_kotlinResponse", response.toString())

                        val jsonArray = response.getJSONArray("result")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            Log.e("_kotlinTitle", jsonObject.optString("judul_slideshow"))

                            // txt1.setText(jsonObject.optString("shubuh"))
                            var isi1 = jsonObject.optString("judul_slideshow").toString()
                            var isi2 = jsonObject.optString("url_slideshow").toString()

                            users.add(User("$isi1", "$isi2"))


                        }

                        val adapter = CustomAdapter(users)
                        recyclerView.adapter = adapter

                    }

                    override fun onError(anError: ANError) {
                        Log.i("_err", anError.toString())
                    }
                })

        }

        fun postkeserve(data1: String, data2: String) {
            AndroidNetworking.post("https://projectmenumasjid.000webhostapp.com/proses-slideshow.php")
                .addBodyParameter("judul_slideshow", data1)
                .addBodyParameter("url_slideshow", data2)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray?) {

                    }

                    override fun onError(anError: ANError?) {

                    }
                })
        }
    }