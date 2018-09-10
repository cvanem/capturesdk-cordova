cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
  {
    "id": "capturesdk-cordova.CaputureSDK",
    "file": "plugins/capturesdk-cordova/www/CaptureSDK.js",
    "pluginId": "capturesdk-cordova",
    "clobbers": [
      "capturesdk"
    ]
  },
  {
    "id": "cordova-plugin-inappbrowser.inappbrowser",
    "file": "plugins/cordova-plugin-inappbrowser/www/inappbrowser.js",
    "pluginId": "cordova-plugin-inappbrowser",
    "clobbers": [
      "cordova.InAppBrowser.open",
      "window.open"
    ]
  }
];
module.exports.metadata = 
// TOP OF METADATA
{
  "cordova-plugin-whitelist": "1.3.3",
  "capturesdk-cordova": "1.0.0",
  "cordova-plugin-inappbrowser": "3.0.0"
};
// BOTTOM OF METADATA
});