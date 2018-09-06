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
import com.socketmobile.capture.android.events.ConnectionStateEvent;
import com.socketmobile.capture.client.CaptureClient;
import com.socketmobile.capture.CaptureError;
import com.socketmobile.capture.AppKey;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.capturesdk.cordova.BuildConfig;
//import de.greenrobot.event.Subscribe;

/*
AppKey appkey = new AppKey("<my appkey>", "<my app id>", "<my developer id>");
CaptureClient client = new CaptureClient(appkey);
client.setListener(mListener);
client.connect(connectionCallback);

ConnectionCallback connectionCallback = new ConnectionCallback() {
    @override public void onConnectionStateChanged(ConnectionState state) {
        switch(state.get()) {
            case ConnectionState.CONNECTING:
                // do something or nothing
                break;
            case ConnectionState.CONNECTED:
                // client is now usable
                break;
            case ConnectionState.DISCONNECTING:
                // only called when shutting down gracefully
                break;
            case ConnectionState.DISCONNECTED:
                if(state.disconnectCausedByError()) {
                    // Handle error
                } else {
                    // Shut down normally
                }
            default:
                // Unreachable
                break;
        }
    }
}

@Override
public void onDeviceStateEvent(DeviceStateEvent event) {
    mDevice = event.getDevice();
    DeviceState state = event.getState()
    switch (state.intValue()) {
        case DeviceState.GONE:
            // Scanner is gone
            break;
        case DeviceState.AVAILABLE:
            // Scanner is connected to the service. You can choose to open the device or not.
            break;
        case DeviceState.OPEN:
            // Scanner is open, but you do not have control of it. It may be in the process of
            // opening or another application may have opened the scanner.
            break;
        case DeviceState.READY:
            // Scanner is ready. Configure scanner
            break;
    }
}

public void onData(DataEvent event) {
    DeviceClient device = event.getDevice();
    String data = event.getData().getString();
    // do something
}
*/

/**
 * This class echoes a string called from JavaScript.
 */



public class CaptureSDK extends CordovaPlugin {

    public static CallbackContext _callbackContext = null;
    public static CallbackContext onDataContext=null;
    public static AppKey appkey = null;
    public static CaptureClient client = null;
    String strInterface;
    //@Override
    protected void onCreate() {
        /*
        System.out.println("-------------------------------onCreate");
        appkey = new AppKey("MC0CFHc4jhssCXc8FljtHDgOeiV3YZJjAhUAgu+FTZgrAjpFyEOcBnVfWzrs1LA=","android:com.capturesdk.cordova","43d33419-e8e6-4ec6-a1f2-c8f9e6b960c8"); 
        client = new CaptureClient(appkey);
        System.out.println("-------------------------------set appkey and client");
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        Capture.builder(this.cordova.getActivity().getApplicationContext())
             .enableLogging(BuildConfig.DEBUG)
            .build();
            */
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCaptureDeviceStateChange(DeviceStateEvent event) {
        System.out.println("-------------------------------onCaptureDeviceStateChange");
        DeviceClient device = event.getDevice();
        DeviceState state = event.getState();

        switch(state.intValue()) {
            case DeviceState.READY:
                // Ready to use
                break;
            default:
                // Device not ready for use
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCaptureServiceConnectionStateChange(ConnectionStateEvent event) {
        System.out.println("-------------------------------onCaptureServiceConnectionStateChange");
        ConnectionState state = event.getState();
        CaptureClient client = event.getClient();

        if (state.hasError()) {
            CaptureError error = state.getError();
            switch(error.getCode()) {
                case CaptureError.COMPANION_NOT_INSTALLED:
                /*
                alert("Socket Mobile Companion must be installed to use your scanner") {
                    positiveButton("Install") { // onClick
                        Capture.installCompanion(this.cordova.getActivity())
                    }
                }
                */
                    break;
                case CaptureError.SERVICE_NOT_RUNNING:
                    if (state.isDisconnected()) {
                        if (Capture.notRestartedRecently()) {
                            //Capture.restart(this);
                        } else {
                            // Service keeps crashing - Reboot the host device and check for updates to Companion
                        }
                    }
                    break;
                case CaptureError.BLUETOOTH_NOT_ENABLED:
                /*alert("Bluetooth must be enabled to use your scanner") {
                    positiveButton("Enable") { // onClick
                        // BLUETOOTH permission must be enabled in your AndroidManifest
                        startActivity(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
                    }
                }
                */
                    break;
            }
        }

        switch(state.intValue()) {
            case ConnectionState.CONNECTING:
                // ...
                break;
            case ConnectionState.CONNECTED:
                // ...
                break;
            case ConnectionState.READY:
                // ...
                break;
            case ConnectionState.DISCONNECTING:
                // ...
                break;
            case ConnectionState.DISCONNECTED:
                // ...
                break;
        }
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

        if(action.equals("registerCallback")) {
            onDataContext = callbackContext; //register the callback
            
            
            System.out.println("-------------------------------Capture builder done");
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {

                    System.out.println("-------------------------------setting appkey and client");
                    appkey = new AppKey("MC0CFHc4jhssCXc8FljtHDgOeiV3YZJjAhUAgu+FTZgrAjpFyEOcBnVfWzrs1LA=","android:com.capturesdk.cordova","43d33419-e8e6-4ec6-a1f2-c8f9e6b960c8"); 
                    client = new CaptureClient(appkey);
                    System.out.println("-------------------------------set appkey and client");
                    //super.onCreate(savedInstanceState);
                    //setContentView(R.layout.activity_main);
                    System.out.println("-------------------------------Capture builder");            
                    Capture.builder(cordova.getActivity().getApplicationContext())
                    .enableLogging(BuildConfig.DEBUG)
                    .build();


                    System.out.println("-------------------------------Registering callback");

                    // Then you're allowed to execute more than twice a callback.
                    PluginResult resultA = new PluginResult(PluginResult.Status.OK, "registeredCallback successful");
                    resultA.setKeepCallback(true);
                    onDataContext.sendPluginResult(resultA);
        
                    // Some more code
        
                    Boolean something = true;
        
                    // bla bla bla code
        
                    /*
                    PluginResult resultB = new PluginResult(PluginResult.Status.OK, "secondJSONResponse");
                    resultB.setKeepCallback(true);
                    onDataContext.sendPluginResult(resultB);       
                    */
                }
            });
            PluginResult pluginResult = new  PluginResult(PluginResult.Status.NO_RESULT); 
            pluginResult.setKeepCallback(true); // Keep callback            
        } else if(action.equals("testCallback")) {            
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