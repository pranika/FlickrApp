package flickr.yahoo.com.flickr.views;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static int TYPE_CONNECTED = 3;
    public static final int NETWORK_STATUS_NOT_CONNECTED=0,NETWORK_STAUS_WIFI=1,NETWORK_STATUS_MOBILE=2,NETWORK_STATUS_CONNECTED=3;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
            else if(activeNetwork == null && !activeNetwork.isConnected())
                return TYPE_NOT_CONNECTED;
            else
                return TYPE_CONNECTED;
        }
        return TYPE_CONNECTED;
    }

    public static int getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        int status = 0;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = NETWORK_STAUS_WIFI;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status =NETWORK_STATUS_MOBILE;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED;
        }
        else if (conn == NetworkUtil.TYPE_CONNECTED) {
            status = NETWORK_STATUS_CONNECTED;
        }

        return status;
    }
}
