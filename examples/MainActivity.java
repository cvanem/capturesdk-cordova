/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package app.greenlink.myapplication;

import android.os.Bundle;
import org.apache.cordova.*;

import com.socketmobile.capture.android.Capture;

import com.socketmobile.capture.client.DeviceClient;
import com.socketmobile.capture.client.DataEvent;
import com.socketmobile.capture.client.DeviceStateEvent;
import com.socketmobile.capture.client.DeviceState;
import com.socketmobile.capture.client.ConnectionState;
import com.socketmobile.capture.client.ConnectionCallback;
import com.socketmobile.capture.android.events.ConnectionStateEvent;
import com.socketmobile.capture.client.CaptureClient;
import com.socketmobile.capture.client.CaptureClient.Listener; //doesn't work

import com.socketmobile.capture.CaptureError;
import com.socketmobile.capture.AppKey;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
//import app.greenlink.myapplication.BuildConfig;

public class MainActivity extends CordovaActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);

        Capture.builder(getApplicationContext())
        //.enableLogging(BuildConfig.DEBUG)
        .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onData(DataEvent event) {
        System.out.println("-------------------------------onData from MainActivity");
        DeviceClient device = event.getDevice();
        String data = event.getData().getString();           
        loadUrl("javascript:window.onData(\""+data+"\")");
    }
}
