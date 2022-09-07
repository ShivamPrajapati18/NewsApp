package com.example.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),NewsItemClicked {
    private lateinit var mAdapter:NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=51f04c5c1a2d40f18d15ce6dc2933600"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager=LinearLayoutManager(this)
        fetchdata(url)
        mAdapter=NewsAdapter(this)
        recyclerView.adapter=mAdapter
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.business ->fetchdata("https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=51f04c5c1a2d40f18d15ce6dc2933600")
                R.id.science ->fetchdata("https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=51f04c5c1a2d40f18d15ce6dc2933600")
                R.id.sports ->fetchdata("https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=51f04c5c1a2d40f18d15ce6dc2933600")
                R.id.technology->fetchdata("https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=51f04c5c1a2d40f18d15ce6dc2933600")
                R.id.health->fetchdata("https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=51f04c5c1a2d40f18d15ce6dc2933600")
            }
            true
        }
    }

    private fun fetchdata(url:String) {
        //val url="https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest= object:JsonObjectRequest(
            Request.Method.GET,url,null,
            {
                val newsJsonArray=it.getJSONArray("articles")
                val newsArray= ArrayList<newsData>()
                for (i in 0 until newsJsonArray.length())
                {
                    val newsArrayObject=newsJsonArray.getJSONObject(i)
                    val news= newsData(
                        newsArrayObject.getString("author"),
                        newsArrayObject.getString("title"),
                        newsArrayObject.getString("url"),
                        newsArrayObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateItem(newsArray)
            },
            {
                Log.d("api failed",it.toString())
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun itemclicked(items: newsData) {
       val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(items.url))
    }




/*
    fun business(item: MenuItem) {
        val url="https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=51f04c5c1a2d40f18d15ce6dc2933600"
        fetchdata(url)
    }

    fun science(item: MenuItem) {
        val url="https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=51f04c5c1a2d40f18d15ce6dc2933600"
        fetchdata(url)
    }
    fun sports(item: MenuItem) {
        val url="https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=51f04c5c1a2d40f18d15ce6dc2933600"
        fetchdata(url)
    }
    fun technology(item: MenuItem) {
        val url="https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=51f04c5c1a2d40f18d15ce6dc2933600"
        fetchdata(url)
    }
    fun health(item: MenuItem) {
        val url="https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=51f04c5c1a2d40f18d15ce6dc2933600"
        fetchdata(url)
    }
*/
}