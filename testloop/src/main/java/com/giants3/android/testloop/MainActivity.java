package com.giants3.android.testloop;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    private String TAG="TEST";



    //异步
    Handler mHandler  ;


    TextView main_thread;
    TextView loop_thread;


    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        main_thread= (TextView) findViewById(R.id.main_thread);

        image= (ImageView) findViewById(R.id.imageView);
        loop_thread= (TextView) findViewById(R.id.loop_thread);


        main_thread.setText(String.valueOf(Thread.currentThread()));
        //创建子线程
        LooperThread looperThread=   new LooperThread()
                ;
        looperThread.start();
        loop_thread.setText(String.valueOf(looperThread));






        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, String.valueOf(Thread.currentThread()));
                    }
                });


            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                {
                    Message message = Message.obtain();
                    message.arg1 = 1;
                    message.obj = "向后台线程发送消息";
                    mHandler.sendMessage(message);

                }

//                {
//                    Message message = mHandler.obtainMessage();
//                    message.arg1 = 1;
//                    message.obj = "向后台线程发送消息";
//                    //message.setData();
//                    message.sendToTarget(); //或使用 handler.sendMessage(message);
//                }



            }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    new TestThread().start();


            }
        });
    }


    /**
     * 常驻线程
     */
     class LooperThread extends Thread
    {


        @Override
        public void run()
        {

            Looper.prepare();
            mHandler=new Handler() {


                @Override
                public void handleMessage(Message msg) {


                    //处理进来的message

                    if(msg.arg1==1)
                    {

                        Log.d(TAG, String.valueOf(Thread.currentThread()));

                        Log.d(TAG, String.valueOf(msg.obj ));
                    }

                    msg.recycle();


                }
            };
            Looper.loop();

        }

    }


    /**
     * 一次性线程
     */
    class TestThread extends Thread
    {


        @Override
        public void run()
        {



            //基于主线程消息队列的handler
            Handler    handler=new Handler(Looper.getMainLooper()) {


                @Override
                public void handleMessage(Message msg) {


                    //处理进来的message

                    if(msg.arg1==1)
                    {

                        Log.d(TAG, String.valueOf(Thread.currentThread()));



                        image.setImageBitmap((Bitmap) msg.obj);
                    }

                    msg.recycle();


                }
            };







                Message message = handler.obtainMessage();
                    message.arg1 = 1;
                    message.obj = loadBitmap();

                    message.sendToTarget(); //或使用 handler.sendMessage(message);

            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        image.setImageBitmap(loadBitmap());
                    }
                });
            }
        }

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
