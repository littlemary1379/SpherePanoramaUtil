package com.mary.cardboardtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ImageView;

import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity extends AppCompatActivity {

    private VrPanoramaView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);


        findView();
        what();

    }

    private void findView(){
        view = findViewById(R.id.vrPanoramaView);
    }

    private void what() {
        ImageLoaderTask imageLoaderTask = new ImageLoaderTask();
        imageLoaderTask.execute(Pair.create(getIntent().getData(), new VrPanoramaView.Options()));
    }

    class ImageLoaderTask extends AsyncTask<Pair<Uri, VrPanoramaView.Options>,Void, Boolean> {

        @Override
        protected Boolean doInBackground(Pair<Uri, VrPanoramaView.Options>... pairs) {

            InputStream is = null;

            AssetManager assetManager = getAssets();
            try {
                is = assetManager.open("PanoramaRenderTest4.jpg");

                VrPanoramaView.Options options = new VrPanoramaView.Options();
                options.inputType = VrPanoramaView.Options.TYPE_MONO;
                view.loadImageFromBitmap(BitmapFactory.decodeStream(is), options);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }
    }

}