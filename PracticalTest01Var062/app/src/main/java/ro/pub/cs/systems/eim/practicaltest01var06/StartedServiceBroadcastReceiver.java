package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class StartedServiceBroadcastReceiver extends BroadcastReceiver {

    public StartedServiceBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String link = intent.getStringExtra("link_s");

        Log.i(Constants.TAG, "RECEIVER");

        if (Constants.ACTION_1.equals(action)) {
            Log.i(Constants.TAG, "ACTION_1 " + intent.getStringExtra("link_s"));
        }
    }

}
