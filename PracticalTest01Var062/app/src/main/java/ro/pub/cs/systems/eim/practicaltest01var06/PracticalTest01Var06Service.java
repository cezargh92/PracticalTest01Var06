package ro.pub.cs.systems.eim.practicaltest01var06;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PracticalTest01Var06Service extends Service {

    String link;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(Constants.TAG, "onCreate() method was invoked");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Constants.TAG, "onStartCommand() method was invoked");

        link = intent.getStringExtra("link");

        ProcessingThread processingThread = new ProcessingThread(this, link);
        processingThread.start();


        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Constants.TAG, "onBind() method was invoked");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(Constants.TAG, "onUnbind() method was invoked");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(Constants.TAG, "onRebind() method was invoked");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "onDestroy() method was invoked");
    }


    private class ProcessingThread extends Thread {

        private Context context;
        String link;

        public ProcessingThread(Context context, String l) {
            this.context = context;
            link = l;
        }

        @Override
        public void run() {
            while(true) {
                sendMessage();
                sendMessage();
                sleep();
            }
        }

        private void sleep() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        private void sendMessage() {
            Intent intent = new Intent();
            intent.putExtra("link_s", link);

            Random rand = new Random();
            int messageType = rand.nextInt() % 3;

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            intent.putExtra("time_date", dateFormat.format(date));


            switch(messageType) {
                case Constants.MESSAGE_1:
                    intent.setAction(Constants.ACTION_1);
                    break;
                case Constants.MESSAGE_2:
                    intent.setAction(Constants.ACTION_2);
                    break;
            }

            context.sendBroadcast(intent);
        }

    }
}