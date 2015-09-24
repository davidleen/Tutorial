package com.giants3.android.testasynctask;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Observable;

public class MainActivity extends AppCompatActivity {



    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= (TextView) findViewById(R.id.textView);
        imageView= (ImageView) findViewById(R.id.imageView);





        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               new AsyncTask<Void, Void, Bitmap>() {
                   @Override
                   protected Bitmap doInBackground(Void... params) {





                       return loadBitmap();

                   }

                   @Override
                   protected void onPostExecute(Bitmap bitmap) {


                       imageView.setImageBitmap(bitmap);



                   }
               }.execute();
            }
        });


        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {










        }});





    }



    private Bitmap loadBitmap()
    {


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ((BitmapDrawable)(getResources().getDrawable(R.mipmap.ic_launcher))).getBitmap();


    }


}
