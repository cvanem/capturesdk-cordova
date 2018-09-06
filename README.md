# capturesdk-cordova
Cordova plugin for Socket Mobile Capture SDK

***Currently under development ****

Example using cordova-plugin-inappbrowser:

In the example directory run:

yarn build

yarn android

Insert your APP_KEY and DEVELOPER_ID into the plugin.xml file.  Here is the section in the plugin.xml file you need to update:
    <platform name="android">
    ...
        <config-file parent="./application" target="AndroidManifest.xml">
            <meta-data android:name="com.socketmobile.capture.APP_KEY" android:value="..."/>
            <meta-data android:name="com.socketmobile.capture.DEVELOPER_ID" android:value="..."/>
        </config-file>
    ...
    </platform>
		
This needs to be done before adding the plugin
