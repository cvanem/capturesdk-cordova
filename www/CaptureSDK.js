var exec = require('cordova/exec');

module.exports = {

    //Android functions

    registerOnDataCallback: function(type, success, error) {
        exec(success, error, "CaptureSDK", "registerCallback", [type]);
    },
    testOnDataCallback: function(type, success, error) {
        exec(success, error, "CaptureSDK", "testCallback", [type]);
    }
};

