package capturesdk.cordova;

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
//import com.socketmobile.capture.client.CaptureClient.CaptureClient.Listener; //doesn't work

import com.socketmobile.capture.CaptureError;
import com.socketmobile.capture.AppKey;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import com.capturesdk.cordova.BuildConfig;

/**
 * This class echoes a string called from JavaScript.
 */

public class CaptureSDK extends CordovaPlugin {
    public CallbackContext onDataContext=null;
    public AppKey appkey = null;
    public CaptureClient client = null;    
    //public static CaptureClient.Listner listner = null;
    public ConnectionCallback connectionCallback = null;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onData(DataEvent event) {
        System.out.println("-------------------------------onData");
        DeviceClient device = event.getDevice();
        String data = event.getData().getString();
        // Do something

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {                
                // Then you're allowed to execute more than twice a callback.
                PluginResult resultA = new PluginResult(PluginResult.Status.OK, "myfirstJSONResponse");
                resultA.setKeepCallback(true);
                onDataContext.sendPluginResult(resultA);
    
                // Some more code
    
                Boolean something = true;
    
                // bla bla bla code
    
    
                PluginResult resultB = new PluginResult(PluginResult.Status.OK, "secondJSONResponse");
                resultB.setKeepCallback(true);
                onDataContext.sendPluginResult(resultB);       
            }
        });
        PluginResult pluginResult = new  PluginResult(PluginResult.Status.NO_RESULT); 
        pluginResult.setKeepCallback(true); // Keep callback
    }

    
    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false otherwise.
     */

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        // Execute an asynchronous task
        
        System.out.println("-------------------------------Executing");

        //This registers the javascript callback.  The callback is currently stored in onDataContext        
        if(action.equals("registerCallback")) {
            onDataContext = callbackContext; //register the callback            
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    //Set the app key and initialize capture client
                    System.out.println("-------------------------------Capture builder started");
                    //Capture.builder(cordova.getActivity().getApplicationContext())
                    //.enableLogging(BuildConfig.DEBUG)
                    //.build();            
                    //System.out.println("-------------------------------Capture builder done");                    
                    //System.out.println("-------------------------------setting appkey and client");
                    //appkey = new AppKey("MC0CFHc4jhssCXc8FljtHDgOeiV3YZJjAhUAgu+FTZgrAjpFyEOcBnVfWzrs1LA=","android:com.capturesdk.cordova","43d33419-e8e6-4ec6-a1f2-c8f9e6b960c8"); 
                    //client = new CaptureClient(appkey);
                    //Can't figure out out to import CaptureClient.Listner as it alows throws a compiler error when imported
                    //client.setListener(new com.socketmobile.capture.client.CaptureClient.Listner() {
                    //});                    
                    //client.connect(connectionCallback); //set the connection callback

                    System.out.println("-------------------------------Registering callback");
                    // Then you're allowed to execute more than twice a callback.
                    PluginResult resultA = new PluginResult(PluginResult.Status.OK, "registeredCallback successful");
                    resultA.setKeepCallback(true);
                    onDataContext.sendPluginResult(resultA);                    
                }
            });
            PluginResult pluginResult = new  PluginResult(PluginResult.Status.NO_RESULT); 
            pluginResult.setKeepCallback(true); // Keep callback            
        } else if(action.equals("testCallback")) {          
            //Tests the onDataContext callback to ensure it has been set and functions correctly  
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    System.out.println("-------------------------------Testing callback");
                    // Then you're allowed to execute more than twice a callback.
                    PluginResult resultA = new PluginResult(PluginResult.Status.OK, "callbackTest was successful");
                    resultA.setKeepCallback(true);
                    onDataContext.sendPluginResult(resultA);
        
                    // Some more code
                    Boolean something = true;
        
                    // bla bla bla code
                    PluginResult resultB = new PluginResult(PluginResult.Status.OK, "second callbackTest was successful");
                    resultB.setKeepCallback(true);
                    onDataContext.sendPluginResult(resultB);       
                }
            });
            PluginResult pluginResult = new  PluginResult(PluginResult.Status.NO_RESULT); 
            pluginResult.setKeepCallback(true); // Keep callback            
        }
        return true;
    }
}