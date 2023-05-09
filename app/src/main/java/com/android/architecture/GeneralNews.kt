package com.android.architecture

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.architecture.adapter.MainArticleAdapter
import com.android.architecture.models.Articles

class GeneralNews : AppCompatActivity() {
    private lateinit var progress3:ProgressBar
    private lateinit var recycler3:RecyclerView
    private var articleList:ArrayList<Articles> = ArrayList()
    var lm:RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var factory:MainActivityViewModelFactory
    private lateinit var model3 :AppViewModel
    private lateinit var articleAdapter:MainArticleAdapter
    private lateinit var searchView2:SearchView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_news)
        progress3 = findViewById(R.id.progress_3)
        recycler3  = findViewById(R.id.recycler_3)
        val country = intent.getStringExtra("country").toString()
        factory = MainActivityViewModelFactory("general",country)
        model3 = ViewModelProvider(this,factory).get(AppViewModel::class.java)
        actionBar?.setDisplayUseLogoEnabled(true)
        model3.getDashBoardLive().observe(this, Observer {
            if(it.status.equals("ok")){
                val lst = it.articles
                articleList.addAll(lst)
                articleAdapter = MainArticleAdapter(this,articleList)
                recycler3.layoutManager = lm
                recycler3.adapter = articleAdapter
                progress3.visibility = ProgressBar.GONE
            }
            else{
                Toast.makeText(this,it.status, Toast.LENGTH_LONG).show()
                progress3.visibility = ProgressBar.VISIBLE
            }
        })
    }
    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.refresh_menu,menu)
        var item = menu?.findItem(R.id.search)
        try {

        searchView2 = item?.actionView as SearchView
        searchView2.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    articleAdapter.filter.filter(query)
                    println(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    articleAdapter?.filter?.filter(newText)
                    println(newText)
                    return false
                }
            })
        }catch (e:Exception){}
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.refresh && progress3.visibility != ProgressBar.VISIBLE){
            fetchData()
            Toast.makeText(applicationContext,"Refreshed",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(applicationContext,"Something Went Wrong!", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun fetchData() {
        if(articleAdapter!=null){
            articleAdapter.clear()
            articleAdapter.notifyDataSetChanged()
        }
        model3.getDashBoardLive().observe(this, Observer {
            if(it.status.equals("ok")){
                var lst = it.articles
                articleList.addAll(lst)
                articleAdapter = MainArticleAdapter(this,articleList)
                recycler3.layoutManager = lm
                recycler3.adapter = articleAdapter
                progress3.visibility  = ProgressBar.VISIBLE
            }
            else{
                progress3.visibility = ProgressBar.GONE
            }
        })
    }
}