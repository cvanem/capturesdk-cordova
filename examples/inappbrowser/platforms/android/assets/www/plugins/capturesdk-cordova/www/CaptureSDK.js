cordova.define("capturesdk-cordova.CaputureSDK", function(require, exports, module) {
var exec = require('cordova/exec');

module.exports = {

    //Android and iOS functions

    registerCallback: function(type, success, error) {
        exec(success, error, "CaptureSDK", "registerCallback", [type]);
    },
    testCallback: function(type, success, error) {
        exec(success, error, "CaptureSDK", "testCallback", [type]);
    },
};

});
