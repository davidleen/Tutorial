package com.giants3.android.testrxandroid;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

                Observable.just("one", "two", "three", "four", "five")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {

                            StringBuilder stringBuilder=new StringBuilder();
                            @Override
                            public void onCompleted() {

                                textView.setText(stringBuilder.toString());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {
                                stringBuilder.append(s);

                            }
                        });
            }
        });


        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


         Observable<BitmapDrawable > bitmapDrawableObservable=       Observable.create(new Observable.OnSubscribe<BitmapDrawable>() {
                    @Override
                    public void call(Subscriber<? super BitmapDrawable> subscriber) {
                        subscriber.onNext(loadDrawable());
                        subscriber.onCompleted();
                    }
                });


                   Observable.create(new Observable.OnSubscribe<BitmapDrawable>() {
                 @Override
                 public void call(Subscriber<? super BitmapDrawable> subscriber) {
                     subscriber.onNext(loadDrawable());
                     subscriber.onCompleted();
                 }
             })


                           .map(new Func1<BitmapDrawable, Bitmap>() {
                       @Override
                       public Bitmap call(BitmapDrawable bitmapDrawable) {
                           return bitmapDrawable.getBitmap();
                       }
                   })
                     .subscribeOn(Schedulers.newThread())
                     .observeOn(AndroidSchedulers.mainThread())

                     .subscribe(new Action1<Bitmap>() {
                         @Override
                         public void call(Bitmap bitmap) {

                             imageView.setImageBitmap(bitmap);
                         }
                     });
            }
        });









    }




    private  BitmapDrawable loadDrawable()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       return ((BitmapDrawable)(getResources().getDrawable(R.mipmap.ic_launcher)))  ;

    }



}
