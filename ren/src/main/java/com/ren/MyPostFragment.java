package com.ren;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 10/22/2016.
 */
public class MyPostFragment extends Fragment {
    private int ACTION_IMAGE_APP = 10;
    String timeStamp;
    String mImageFileLocation;
    TextView time1;
    String imageFileName;
    private String GALLERY_LOCATION = "imagegallery";
    private File mGalleryFolder;
    public Bitmap bitmap;
    ImageView iv1;
    ImageButton messageSend;
    public File storagePublicDirectory;
    String json_string = null;
    newImageAdapter imageAdapter = null;
    ListView listView;
    JSONObject jsonObject;
    JSONArray jsonArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        if(container == null)
        {
            return null;
        }
        View view = inflater.inflate(R.layout.activity_mypost, container, false);
        downloadImage();
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        ImageButton btnCamera = (ImageButton)view.findViewById(R.id.buttonCamera);

        listView = (ListView)view.findViewById(R.id.listView);
        messageSend = (ImageButton)view.findViewById(R.id.messenger_send_button);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent, ACTION_IMAGE_APP);
            }
        });


        messageSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage();

            }
        });

    }
    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent data){
        super.onActivityResult(RequestCode, ResultCode, data);
        if(RequestCode == ACTION_IMAGE_APP && ResultCode == RESULT_OK)
        {
            Intent temPic = new Intent(getActivity(), Preview.class);
            Bundle extras = new Bundle();
            extras.putString("imageFileName",imageFileName);
            extras.putString("imageLocation",mImageFileLocation);
            temPic.putExtras(extras);
            startActivity(temPic);
        }
    }


    File createImageFile() throws IOException {
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "Image_"+timeStamp;
        storagePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File Image = File.createTempFile(imageFileName, ".jpg", storagePublicDirectory);
        mImageFileLocation = Image.getAbsolutePath();

        return Image;
    }

    public void downloadImage(){
        class DownloadImage extends AsyncTask<Void, Void, String> {
            String json_url;
            String JSON_STRING;

            ProgressDialog refresh;
            @Override
            protected void onPreExecute() {
                imageAdapter = new newImageAdapter(getContext(), R.layout.records);
                json_url = "http://zhengzhizhou.x10host.com/getAllImage.php";
                refresh = ProgressDialog.show(getContext(), "Refreshing", null, true, true);
            }

            @Override
            protected void onPostExecute(String result) {
                json_string = result;
                listView.setAdapter(imageAdapter);
                try {
                    jsonObject = new JSONObject(json_string);

                    jsonArray =jsonObject.getJSONArray("result");
                    int count = 0;
                    while(count < 3){
                        JSONObject jo = jsonArray.getJSONObject(count);
                        new getImage().execute(jo);
                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refresh.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    URL url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while((JSON_STRING = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(JSON_STRING +"\n");
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return stringBuilder.toString().trim();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

            }
        }
        DownloadImage di = new DownloadImage();
        di.execute();
    }


    class getImage extends AsyncTask<JSONObject, Void, myImage>{

        @Override
        protected myImage doInBackground(JSONObject... params) {
            JSONObject jo = params[0];
            URL url = null;
            Bitmap bitmap = null;
            String name = null;
            try{
                url = new URL(jo.getString("url"));
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                name = jo.getString("name");
            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            myImage image = new myImage(bitmap, name);
            return image;
        }

        @Override
        protected void onPostExecute(myImage image) {
            super.onPostExecute(image);

            imageAdapter.add(image);

        }
    }

}
