# capturesdk-cordova
Cordova plugin for Socket Mobile Capture SDK

***Currently under development ****

git clone https://cvanem/capturesdk-cordova


Example using cordova-plugin-inappbrowser:

Prerequisites:
    
    yarn
    node
    npm
    cordova (npm install -g cordova)
    Android tools: Android Studio/android sdk/jdk

Open command prompt and navigate to the /examples directory and run the commands:

    yarn install
    yarn clean (Only needed if the examples/inappbrowser directory exists and you want to run yarn build)
    yarn build (creates the cordova project and configures all plugins and the android platform)
    yarn android (compiles and runs the android app on a usb connected device or open emulator)


Insert your APP_KEY and DEVELOPER_ID into the plugin.xml file.  Here is the section in the plugin.xml file you need to update:

    <platform name="android">
        <config-file parent="./application" target="AndroidManifest.xml">
            <meta-data android:name="com.socketmobile.capture.APP_KEY" android:value="..."/>
            <meta-data android:name="com.socketmobile.capture.DEVELOPER_ID" android:value="..."/>
        </config-file>
    </platform>

This currently needs to be done in the public repo before running the yarn build example.  If you need to change this you will need to either change the https://github.com/cvanem/capturesdk-cordova repository before running yarn build or you can update the androidmanifest.xml manually after running yarn build but before running yarn android.
