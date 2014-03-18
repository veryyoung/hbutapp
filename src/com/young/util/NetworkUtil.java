package com.young.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by VERYYOUNG on 14-3-11.
 */
public class NetworkUtil {
    public static boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) AppUtil.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }
}
