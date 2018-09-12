package capturesdk.cordova;

//import android.app.Activity;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.IccOpenLogicalChannelResponse;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.Log;

import com.socketmobile.capture.android.Capture;

import com.socketmobile.capture.client.DeviceClient;
import com.socketmobile.capture.client.DataEvent;
import com.socketmobile.capture.client.DeviceStateEvent;
import com.socketmobile.capture.client.DeviceState;
import com.socketmobile.capture.client.ConnectionState;
import com.socketmobile.capture.client.ConnectionCallback;
import com.socketmobile.capture.android.events.ConnectionStateEvent;
import com.socketmobile.capture.client.CaptureClient;
//import com.socketmobile.capture.client.CaptureClient$Listener; //doesn't work
//import com.socketmobile.capture.client.CaptureClient.Listener; //doesn't work
import com.socketmobile.capture.client.CaptureClient.Listener; //doesn't work
//import com.socketmobile.capture.client.CaptureClient.CaptureClient.Listener; //doesn't work

import com.socketmobile.capture.CaptureError;
import com.socketmobile.capture.AppKey;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
//import com.capturesdk.cordova.BuildConfig;
import app.greenlink.myapplication.BuildConfig;

/**
 * This class echoes a string called from JavaScript.
 */

public class NewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("-------------------------------onCreate");        
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onData(DataEvent event) {
        System.out.println("-------------------------------onData");
    }
}
        