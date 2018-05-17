// Broadcast Reciever to track network changes
package flickr.yahoo.com.flickr.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import flickr.yahoo.com.flickr.views.NetworkUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        NetworkMessageEvent networkMessageEvent = new NetworkMessageEvent();
        int status = NetworkUtil.getConnectivityStatusString(context);
        if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if(status==NetworkUtil.NETWORK_STATUS_NOT_CONNECTED){


                networkMessageEvent.setCustomMessage("Turn on Internet");
                EventBus.getDefault().post(networkMessageEvent);
                //Toast.makeText(context,"Turn on Internet",Toast.LENGTH_LONG).show();
            }
            else if(status==NetworkUtil.NETWORK_STAUS_WIFI || status==NetworkUtil.NETWORK_STATUS_MOBILE) {

                networkMessageEvent.setCustomMessage("Internet is turned on");
                EventBus.getDefault().post(networkMessageEvent);
            }
        }
    }
}