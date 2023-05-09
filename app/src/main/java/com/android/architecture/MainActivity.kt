package com.android.architecture

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.architecture.adapter.MainArticleAdapter
import com.android.architecture.models.Articles
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var model: AppViewModel
    private lateinit var factory: MainActivityViewModelFactory
    private lateinit var articleAdapter: MainArticleAdapter
    private var articleList:ArrayList<Articles> = ArrayList()
    private var articleList_saved:Bundle? = null
    var lm:RecyclerView.LayoutManager = LinearLayoutManager(this)
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var reciever:BroadcastReceiver;
    private lateinit var searchView: SearchView
    private var country:String = ""
    private var flag = 0;
    @SuppressLint("MissingInflatedId", "AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pager_adapter)
        factory = MainActivityViewModelFactory("technology","in")
        model = ViewModelProvider(this, factory)[AppViewModel::class.java]
        recyclerView = findViewById(R.id.recycler)
        progressBar = findViewById(R.id.progress)
        navigationView = findViewById(R.id.navigation_view)
        drawerLayout = findViewById(R.id.drawer_layout)
        actionBar?.setDisplayUseLogoEnabled(true)
        //main Logic
        var colorDrawable: ColorDrawable = ColorDrawable(Color.parseColor("#0F9D58"))
        actionBar?.setBackgroundDrawable(colorDrawable)
        actionBar?.setDisplayShowHomeEnabled(true)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.DrawerOpen, R.string.DrawerClose)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.bussiness && country!="") {
                val ii = Intent(this, Bussiness::class.java)
                ii.putExtra("country",country);
                startActivity(ii)
            }
            else if(it.itemId == R.id.science && country!=""){
                val i2 = Intent(this, Science::class.java)
                i2.putExtra("country",country);
                startActivity(i2)
            }
            else if(it.itemId == R.id.general && country!=""){
                val i3 = Intent(this, GeneralNews::class.java)
                i3.putExtra("country",country);
                startActivity(i3)
            }
            else if (it.itemId == R.id.country){
                val getcountry =  Intent(this,ChooseCountry::class.java);
                startActivityForResult(getcountry,100)
            }
            false
        }
        //loading data
        loadData(flag)
//        search view instance saved
        if(savedInstanceState!=null){
            searchQuery = savedInstanceState.getString("search")
            country = savedInstanceState.getString("cntry").toString()
            flag = savedInstanceState.getInt("flag")
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        country = savedInstanceState.getString("cntry","in")
        flag = savedInstanceState.getInt("flag",0);
    }
    private fun loadData(flg:Int) {
        if (checkConnection() && flg == 0) {
            model.getDashBoardLive().observe(this, Observer {
                if (it.status.equals("ok")) {
                    val lst = it.articles
                    articleList.addAll(lst)
                    articleAdapter = MainArticleAdapter(this, articleList)
                    recyclerView.layoutManager = lm
                    recyclerView.adapter = articleAdapter
                    progressBar.visibility = ProgressBar.GONE
                } else {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
            })
            flag = 1;
        }
        else if(checkConnection() && flg == 1){
            factory.country = country
            model.country = country;
            loadData(0)
            flag = 0
        } else {
            Toast.makeText(this, "Internet is off", Toast.LENGTH_LONG).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100){
            country = data?.getStringExtra("country").toString();
            Toast.makeText(this,"Choosed Country is "+ country ,Toast.LENGTH_LONG).show()
            flag = 1
            fetchData()
        }
        else{
            //
        }
        println(country)
    }
    fun checkConnection():Boolean{
        reciever = ConnectionManager()
        registerReceiver(reciever,IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        if(NetworkUtil().getConnectivityStatusString(applicationContext) == true){
            return true
        }
        return false
    }
//    getting result
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("search",searchQuery)
        super.onSaveInstanceState(outState)
    }
    //displaying our refresh menu on action bar
    private var searchQuery:String? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.refresh_menu,menu)
        var item = menu?.findItem(R.id.search)
        try{
            if(progressBar.visibility == ProgressBar.VISIBLE) {
                searchView = item?.actionView as SearchView
                if(searchQuery?.isNotEmpty() == true){
                    searchView.isIconified = true
                    searchView.onActionViewExpanded()
                    searchView.setQuery(searchQuery,false)

                }
                searchView.setOnQueryTextListener(
                    object : OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            articleAdapter.filter.filter(query)
                            return false
                        }
                        override fun onQueryTextChange(newText: String?): Boolean {
                            articleAdapter.filter.filter(newText)
                            searchQuery = newText
                            return false
                        }
                    })
            }
            else{
                searchView.visibility = SearchView.GONE
            }
        }catch (e:Exception){
            Toast.makeText(applicationContext,"Can't use Temporarily",Toast.LENGTH_SHORT).show()
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        if(item.itemId == R.id.refresh && checkConnection() && progressBar.visibility != ProgressBar.VISIBLE){
            fetchData()
            Toast.makeText(applicationContext,"Refreshed",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRestart() {
        checkConnection()
        super.onRestart()
    }
    private fun fetchData() {
        articleAdapter.clear()
        articleAdapter.notifyDataSetChanged()
        loadData(flag)
    }

    override fun onStop() {
        super.onStop()
    }
}