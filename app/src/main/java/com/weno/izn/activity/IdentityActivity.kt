package com.weno.izn.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.weno.izn.R
import com.weno.izn.structures.IdentViewModel
import kotlinx.android.synthetic.main.activity_identity.*

class IdentityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identity)
        val identViewModel:IdentViewModel by viewModels()

        button_camera.setOnClickListener {
            val intent = Intent(this,CameraActivity2::class.java)
            startActivity(intent)
        }
        button_photo_album.setOnClickListener {
            val intent = Intent(this,PhotoAlbumActivity::class.java)
            startActivity(intent)
        }
    }

}