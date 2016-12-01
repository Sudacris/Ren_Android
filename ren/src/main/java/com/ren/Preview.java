package com.ren;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by user on 11/5/2016.
 */

public class Preview extends AppCompatActivity {
    //preview
    private ImageView temPicView;
    public static final String UPLOAD_URL = "http://zhengzhizhou.x10host.com/savepicture.php";
    public static final String UPLOAD_KEY_IMAGE = "image";
    public static final String UPLOAD_KEY_NAME = "name";
    private Bitmap bitmapA;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        temPicView = (ImageView)findViewById(R.id.temPic);

        Bundle getExtras = getIntent().getExtras();
        String imageLocation = getExtras.getString("imageLocation");
        bitmapA = BitmapFactory.decodeFile(imageLocation);
        temPicView.setImageBitmap(bitmapA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_tempic, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.ok:
                uploadImage();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageByte = baos.toByteArray();
        String encodeImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return encodeImage;
    }
    private static class data{
        Bitmap bitmap;
        String name;
        data(Bitmap bitmap, String name){
            this.bitmap = bitmap;
            this.name = name;
        }
    }
    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap, Void, String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(Preview.this, "loading...", null,true,true);
            }
            @Override
            protected String doInBackground(Bitmap... params) {
                //String name = params[0].name;
                //Bitmap bitmap = params[0].bitmap;
                String uploadImage = getStringImage(params[0]);
                HashMap<String, String> hm = new HashMap<>();
                hm.put(UPLOAD_KEY_IMAGE, uploadImage);
                //hm.put(UPLOAD_KEY_NAME, name);
                String result = rh.sendPostRequest(UPLOAD_URL, hm);
                return result;
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }

        //data param = new data(bitmapA, name);
        UploadImage ui = new UploadImage();
        ui.execute(bitmapA);

    }


}
