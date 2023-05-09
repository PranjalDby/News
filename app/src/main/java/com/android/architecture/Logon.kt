package com.android.architecture

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.ProgressBar

class Logon : AppCompatActivity() {
    private lateinit var img:ImageView
    private lateinit var progress:ProgressBar
    @SuppressLint("Deprecated")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loadin_page)
        img = findViewById(R.id.news_image)
        img.setImageResource(R.drawable.news)
        progress = findViewById(R.id.start_progress)
        Handler().postDelayed({
            var i: Intent = Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        },1000)
    }

    override fun onRestart() {
        super.onRestart()
        var i: Intent = Intent(this,MainActivity::class.java)
        startActivity(i)
        super.onRestart()
    }
}