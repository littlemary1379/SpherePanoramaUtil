package com.mary.cardboardtest;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private VrPanoramaView view;
    private static Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        findView();
        what();

    }

    private void findView() {
        view = findViewById(R.id.vrPanoramaView);
    }

    private void what() {
        showSpherePanorama(Pair.create(getIntent().getData(), new VrPanoramaView.Options()));
    }

    private void showSpherePanorama(Pair<Uri, VrPanoramaView.Options> pair) {
        handler.postDelayed(() -> {
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
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, 0);
    }

}