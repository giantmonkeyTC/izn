package com.weno.izn.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.weno.izn.R
import com.weno.izn.structures.Client
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.util.*

class CameraActivity : AppCompatActivity() {
    // 申请权限的集合，同时要在AndroidManifest.xml中申请，Android 6以上需要动态申请权限
    var permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_WIFI_STATE
    )

    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    var mPermissionList: MutableList<String> = ArrayList()

    //图片路径
    private var path: String? = null

    //文件
    var f: File? = null
    var cameraRequestCode = 1
    var storeRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        //修复7.0后相机闪退
        // initPhotoError()
        //6.0获取多个权限
        //6.0获取多个权限
        mPermissionList.clear()
        //
        //
        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(
                    this@CameraActivity,
                    permissions[i]
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                mPermissionList.add(permissions[i])
            }
        }

        //未授予的权限为空，表示都授予了

        //未授予的权限为空，表示都授予了
        if (mPermissionList.isEmpty()) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(
                MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(File(Environment.getExternalStorageDirectory().path + "/" + "table.jpg"))
            )
            startActivityForResult(cameraIntent, cameraRequestCode)

            //读取根目录下的一张图片
            /*     path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1.png";*/

            /* readImg(showImg);*/
        } else { //请求权限方法
            val permissions = mPermissionList.toTypedArray()
            ActivityCompat.requestPermissions(this@CameraActivity, permissions, 1)
        }
    }

    //点击事件回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequestCode && resultCode == RESULT_OK) {
            path = Environment.getExternalStorageDirectory().path + "/" + "table.jpg"
            val fileExist = fileIsExists(path)
            if (fileExist) {
                Toast.makeText(this@CameraActivity, "开始上传" + f!!.path, Toast.LENGTH_LONG).show()
                try {
                    //上传图片
                    Client.api.identityService.uploadPhoto().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe({
                            // Client.eventBus.postevent(IdentityResultEvent)
                            it["result"]
                        }, {

                        })

//                    ImageUpload.run(f, this@CameraActivity)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
        readImg(camera_view)
    }

    //界面显示图片
    fun readImg(view: View?) {
        val bitmap = BitmapFactory.decodeFile(path)
        camera_view.setImageBitmap(bitmap)
    }

    //判断文件是否存在
    fun fileIsExists(strFile: String?): Boolean {
        try {
            f = File(strFile)
            if (!f!!.exists()) {
                return false
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    val showRequestPermission: Boolean =
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this@CameraActivity,
                            permissions[i]!!
                        )
                    if (showRequestPermission) {
                        Toast.makeText(this@CameraActivity, "权限未申请", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun initPhotoError() {
        // android 7.0系统解决拍照的问题
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
    }

}