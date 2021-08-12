package com.weno.izn.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageUpload extends AppCompatActivity {


    private static final String IMGUR_CLIENT_ID = "123";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private static final OkHttpClient client = new OkHttpClient();


    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static void run(File f, final Context context) throws Exception {
        final File file=f;
        new Thread() {
            @Override
            public void run() {
                //子线程需要做的工作
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", "Square Logo")
                        .addFormDataPart("file", UUID.randomUUID().toString()+".jpg",
                                RequestBody.create(MEDIA_TYPE_PNG, file))
                        .build();
                //设置为自己的ip地址jpg
                Request request = new Request.Builder()
                        .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                        .url("http://192.168.43.189:5000/upload")
                        .post(requestBody)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                   // Log.d("ImageUpload",response.body().string());
                    String res = response.body().string();
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    //Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("识别结果");
                    dialog.setMessage(res);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                    Looper.loop();
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
