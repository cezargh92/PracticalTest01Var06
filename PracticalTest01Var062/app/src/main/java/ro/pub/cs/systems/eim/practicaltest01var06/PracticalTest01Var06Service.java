package ro.pub.cs.systems.eim.practicaltest01var06;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
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

        ProcessingThread processingThread = new ProcessingThread(this);
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

        public ProcessingThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            sendMessage();
            sleep();
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

            //Toast.makeText(getApplicationContext(), "TEST", Toast.LENGTH_LONG).show();

            Random rand = new Random();
            int messageType = rand.nextInt() % 2;

            Calendar c = Calendar.getInstance();
            int seconds = c.get(Calendar.SECOND);
            int min = c.get(Calendar.MINUTE);
            int h = c.get(Calendar.HOUR);
            int d = c.get(Calendar.DATE);

            String mess = String.valueOf(h) +  " " + String.valueOf(min) + " " + String.valueOf(h);
            intent.putExtra("time_date", mess);


            switch(messageType) {
                case Constants.MESSAGE_1:
                    Log.i(Constants.TAG, "MeSSAGE 1");
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