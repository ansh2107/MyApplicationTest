package com.example.ansh.myapplicationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.cloudinary.Transformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.ansh.myapplicationtest.R.id.fab;

public class MainActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE_REQUEST_CODE = 1000;
    FloatingActionButton fab;
    ArrayList<String> urls ;
    GridView gridview ;
    private String reso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        urls = new ArrayList<String>();

         gridview = (GridView) findViewById(R.id.gridview);

        Map config = new HashMap();
        config.put("cloud_name", "dkcicznay");

        MediaManager.init(this, config);

         fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMediaChooser();
            }
        });
    }

    private void openMediaChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_IMAGE_REQUEST_CODE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings1) {
            reso = "60x40";
            gridview.setAdapter(new ImageAdapter(MainActivity.this,urls,reso));
            gridview.getAdapter().notify();

            return true;
        }
        if (id == R.id.action_settings2) {
            reso = "300x250";
            gridview.setAdapter(new ImageAdapter(MainActivity.this,urls,reso));
            gridview.getAdapter().notify();

            return true;
        }
        if (id == R.id.action_settings3) {
            reso = "1200x1000";
            gridview.setAdapter(new ImageAdapter(MainActivity.this,urls,reso));
            gridview.getAdapter().notify();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSnackBar(String message) {
        Snackbar.make(fab, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CHOOSE_IMAGE_REQUEST_CODE && data != null && data.getData() != null) {
            MediaManager.get().upload(data.getData()).callback(new UploadCallback() {

                @Override
                public void onStart(String requestId) {
                    showSnackBar("Upload started...");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    urls.add(resultData.get("public_id").toString());
                    reso = "max";
                    gridview.setAdapter(new ImageAdapter(MainActivity.this,urls,reso));
                    showSnackBar("Upload complete!");
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    showSnackBar("Upload error: " + error.getDescription());
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    showSnackBar("Upload rescheduled.");
                }
            }).dispatch();
        }


    }


}
