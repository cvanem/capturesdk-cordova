# capturesdk-cordova
Cordova plugin for Socket Mobile Capture SDK

***Currently under development ****

git clone https://cvanem/capturesdk-cordova


Example using cordova-plugin-inappbrowser:

Prerequisites:   
    
    node/npm
    yarn (npm install -g yarn)
    cordova (npm install -g cordova)
    Android tools: Android Studio/android sdk/jdk
    cocoapods (For Mac OS: sudo gem install cocoapods)

Open command prompt and navigate to the /examples directory and run the commands:

    yarn install
    yarn clean (Only needed if the examples/inappbrowser directory exists and you want to run yarn build)
    yarn build (creates the cordova project and configures all plugins and the android platform)
    yarn android (compiles and runs the android app on a usb connected device or open emulator)

*Note: The above example builds from the public repository so if you need to change the app key or developer id, you will need to clone the repo first and update the values before building.  Here are instructions for doing this:
In the example directory run:
    
    1. yarn install
    2. yarn clean
    3. yarn clonerepo (this clones the public repository to /examples/capturesdk-cordova)
    4. Open the /examples/capturesdk-cordova/plugin.xml file and make the necessary changes (see below for instructions)
    5. yarn buildfromclone (builds the cordova example using the cloned repo instead of the public repo)
    6. yarn android
    
Instructions for changing the app key or developer id:
To change the app key or developer id, you can insert your APP_KEY and DEVELOPER_ID into the plugin.xml file.  You must do this after cloning the repo prior to building (see step 4 above).   Here is the section in the plugin.xml file you need to update:

    <platform name="android">
        <config-file parent="./application" target="AndroidManifest.xml">
            <meta-data android:name="com.socketmobile.capture.APP_KEY" android:value="..."/>
            <meta-data android:name="com.socketmobile.capture.DEVELOPER_ID" android:value="..."/>
        </config-file>
    </platform>
