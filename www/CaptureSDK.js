var exec = require('cordova/exec');

module.exports = {
    //Android and iOS functions
    registerCallback: function(type, success, error) {
        exec(success, error, "CaptureSDK", "registerCallback", [type]);
    },

    testCallback: function(type, success, error) {
        exec(success, error, "CaptureSDK", "testCallback", [type]);
    },
    
    //iOS only functions
    registerErrorCallback: function(type, success, error) {
        exec(success, error, "CaptureSDK", "registerErrorCallback", [type]);
    },
    registerRemovalCallback: function(type, success, error) {
        exec(success, error, "CaptureSDK", "registerRemovalCallback", [type]);
    },
    registerArrivalCallback: function(type, success, error) {
        exec(success, error, "CaptureSDK", "registerArrivalCallback", [type]);
    },

};
