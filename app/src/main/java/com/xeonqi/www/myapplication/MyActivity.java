package com.xeonqi.www.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MyActivity extends ActionBarActivity {
    public static final int REQUEST_IMAGE_SELECTOR = 0; //requestCode 0
    public static final String PREFS_NAME = "privatePrefsFile";
    private static String imgFilename = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        imgFilename = settings.getString("imageFilename", null);

        Button changePic = (Button) findViewById(R.id.button);
        final ImageView showPic = (ImageView) findViewById(R.id.imageView);
        final Button resetButton = (Button) findViewById(R.id.resetButton);
        if (imgFilename != null) {
            showPic.setImageURI(Uri.parse(imgFilename));
        }

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent galleryPic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryPic, REQUEST_IMAGE_SELECTOR);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                showPic.setImageDrawable(getResources().getDrawable(R.drawable.picture_holder));
                imgFilename = null;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView showPic = (ImageView) findViewById(R.id.imageView);
        switch (requestCode) {
            case REQUEST_IMAGE_SELECTOR :
                if (null != data && resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
/*
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap photo = BitmapFactory.decodeStream(inputStream);
                        showPic.setImageBitmap(photo);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                    }
*/
                    showPic.setImageURI(null);
                    showPic.setImageURI(selectedImage);

                    imgFilename = selectedImage.toString();
                    Context context = getApplicationContext();
                    CharSequence text = "Loading Image: success." + selectedImage.toString();
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                break;
            default:
                break;
        }

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        LinearLayout layout0 = (LinearLayout)findViewById(R.id.linearLayout);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layout0.setOrientation(LinearLayout.HORIZONTAL);
            Toast.makeText(this, "landscape", Toast.LENGTH_LONG).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            layout0.setOrientation(LinearLayout.VERTICAL);
            Toast.makeText(this, "portrait", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        storeImageResult();
    }

    @Override
    protected void onPause(){
        super.onPause();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        storeImageResult();
    }

    @Override
    protected void onStop(){
        super.onStop();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        storeImageResult();
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Error", "Error getting bitmap", e);
        }
        return bm;
    }

    private void storeImageResult(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("imageFilename", imgFilename);
        // Commit the edits!
        editor.commit();
    }
}