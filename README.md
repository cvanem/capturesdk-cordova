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


Insert your APP_KEY and DEVELOPER_ID into the plugin.xml file (See *Note below prior to updating this).  Here is the section in the plugin.xml file you need to update:

    <platform name="android">
        <config-file parent="./application" target="AndroidManifest.xml">
            <meta-data android:name="com.socketmobile.capture.APP_KEY" android:value="..."/>
            <meta-data android:name="com.socketmobile.capture.DEVELOPER_ID" android:value="..."/>
        </config-file>
    </platform>

*Note: If example builds from the public repository so if you need to change the above, you will need to change it in the public repository.  Alternatively you can do the following instead:
In the example directory run:
    
    yarn clean
    yarn clonerepo (this clones the public repository to /examples/capturesdk-cordova
    Open the /examples/capturesdk-cordova/plugin.xml file and make the necessary changes
    yarn buildfromclone (builds the cordova example using the cloned repo instead of the public repo)
    yarn android
    
