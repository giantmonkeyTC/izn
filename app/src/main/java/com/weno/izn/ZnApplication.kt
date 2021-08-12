package com.weno.izn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.weno.izn.activity.IdentityActivity
import kotlinx.android.synthetic.main.activity_main.*

class ZnApplication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Ident_main.setOnClickListener {
            val intent = Intent(this,IdentityActivity::class.java)
            startActivity(intent)
        }
    }
}