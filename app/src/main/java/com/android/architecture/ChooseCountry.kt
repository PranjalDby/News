package com.android.architecture;

import android.os.Bundle
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity

class ChooseCountry: AppCompatActivity() {
    private lateinit var checkbox:CheckBox;
    private lateinit var model: AppViewModel;
    private var country = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_country)
        checkbox = findViewById(R.id.canada);
        checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                country = "ca"
                intent.putExtra("country",country)
                setResult(RESULT_OK,intent)
                finish()
            }
            else{
                finish()
            }
        })
        findViewById<CheckBox>(R.id.us).setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(country!=""){
                    intent.removeExtra(country)
                    country = ""
                    country = "us"
                    intent.putExtra("country",country)
                    setResult(RESULT_OK,intent)
                    finish()
                }
                else{
                    country = "us"
                    intent.putExtra("country",country)
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }
            else{
                finish()
            }
        }
        findViewById<CheckBox>(R.id.ind).setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(country!=""){
                    intent.removeExtra(country)
                    country = ""
                    country = "in"
                    intent.putExtra("country",country)
                    setResult(RESULT_OK,intent)
                    finish()
                }
                else{
                    country = "in"
                    intent.putExtra("country",country)
                    setResult(RESULT_OK,intent)
                    finish()
                }
            }
            else{
                finish()
            }
        }
    }
    override fun onStop() {
        super.onStop()
        finish()
    }
}