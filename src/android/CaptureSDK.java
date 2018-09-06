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
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    //@Override
    protected void onCreate() {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Capture.builder(this.cordova.getActivity())
                //.enableLogging(BuildConfig.DEBUG)
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onData(DataEvent event) {
        DeviceClient device = event.getDevice();
        String data = event.getData().getString();
        // Do something
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCaptureDeviceStateChange(DeviceStateEvent event) {
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

    private CallbackContext _callbackContext = null;
    String strInterface;

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


        if (action.equals("registerCallback")) {            
            this._callbackContext = callbackContext;
            return true;
        }
        else if (action.equals("testCallback")) {
            JSONObject result = new JSONObject();
            result.put("testCallback","Success!");
            this._callbackContext.success(result);            
            return true;
        }
        else if (action.equals("checkStatus")) {
            String portName = args.getString(0);
            String portSettings = args.getString(1);
            this.checkStatus(portName, portSettings, callbackContext);
            return true;
        }
        return false;
    }

    public void checkStatus(String portName, String portSettings, CallbackContext callbackContext) {

        final Context context = this.cordova.getActivity();
        final CallbackContext _callbackContext = callbackContext;

        final String _portName = portName;
        final String _portSettings = portSettings;

        cordova.getThreadPool()
                .execute(new Runnable() {
                    public void run() {

                        /*StarIOPort port = null;
                        try {

                            port = StarIOPort.getPort(_portName, _portSettings, 10000, context);

                            // A sleep is used to get time for the socket to completely open
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                            }

                            StarPrinterStatus status;
                            Map<String, String> firmwareInformationMap = port.getFirmwareInformation();
                            status = port.retreiveStatus();


                            JSONObject json = new JSONObject();
                            try {
                                json.put("offline", status.offline);
                                json.put("coverOpen", status.coverOpen);
                                json.put("cutterError", status.cutterError);
                                json.put("receiptPaperEmpty", status.receiptPaperEmpty);
                                json.put("ModelName", firmwareInformationMap.get("ModelName"));
                                json.put("FirmwareVersion", firmwareInformationMap.get("FirmwareVersion"));
                            } catch (JSONException ex) {

                            } finally {
                                _callbackContext.success(json);
                            }


                        } catch (StarIOPortException e) {
                            _callbackContext.error("Failed to connect to printer :" + e.getMessage());
                        } finally {

                            if (port != null) {
                                try {

                                    StarIOPort.releasePort(port);
                                } catch (StarIOPortException e) {
                                    _callbackContext.error("Failed to connect to printer" + e.getMessage());
                                }
                            }

                        }
                        */

                    }
                });
    }
}