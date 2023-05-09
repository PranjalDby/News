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

class Science : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var model: AppViewModel
    private lateinit var progressBar: ProgressBar
    private var articleList:ArrayList<Articles> = ArrayList()
    var lm: RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var factory:MainActivityViewModelFactory
    private lateinit var articleAdapter: MainArticleAdapter
    private lateinit var searchView3:SearchView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_science)
        var cnt = intent.getStringExtra("country").toString();
        factory = MainActivityViewModelFactory("science",cnt)
        model = ViewModelProvider(this,factory).get(AppViewModel::class.java)
        recyclerView = findViewById(R.id.recycler_2)
        progressBar = findViewById(R.id.progress_2)
        actionBar?.setDisplayUseLogoEnabled(true)
        model.getDashBoardLive().observe(this, Observer {
            if(it.status.equals("ok")){
                val lst = it.articles
                articleList.addAll(lst)
                articleAdapter = MainArticleAdapter(this,articleList)
                recyclerView.layoutManager = lm
                recyclerView.adapter = articleAdapter!!
                progressBar.visibility = ProgressBar.GONE
            }
            else{
                Toast.makeText(this,it.status, Toast.LENGTH_LONG).show()
                progressBar.visibility = ProgressBar.VISIBLE
            }
        })
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.refresh_menu,menu)
        var item = menu?.findItem(R.id.search)
        try {
            searchView3 = item?.actionView as SearchView
            searchView3.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
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
        }catch (e:java.lang.Exception){}
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.refresh && progressBar.visibility != ProgressBar.VISIBLE){
            fetchData()
            Toast.makeText(applicationContext,"Refreshed", Toast.LENGTH_LONG).show()
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
        model.getDashBoardLive().observe(this, Observer {
            if(it.status.equals("ok")){
                var lst = it.articles
                articleList.addAll(lst)
                articleAdapter = MainArticleAdapter(this,articleList)
                recyclerView.layoutManager = lm
                recyclerView.adapter = articleAdapter
                progressBar.visibility  = ProgressBar.VISIBLE
            }
            else{
                progressBar.visibility = ProgressBar.GONE
            }
        })
    }
}