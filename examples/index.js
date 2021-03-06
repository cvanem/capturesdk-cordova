/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var inAppBrowserRef;
var currentCommandId;
var environment = 'Production';
//var developmentUrl = "https://10.0.2.2:5001";
var developmentUrl = "https://192.168.1.46:5001";
var productionUrl = "https://greenlink.app";

function callbackSuccess(result) {
    console.log('success callback: ' + JSON.stringify(result));
    //convert the ArrayBuffer to a base64 encoded string so we can send it to the browser
    let base64String = btoa(String.fromCharCode(...new Uint8Array(result)));
    console.log('success callback base64: ' + base64String);
    onData(base64String);
}
function callbackError(error) {
    console.log('error callback: ' + JSON.stringify(error));    
}

function onData(data) {
    //alert('onData executing: ' + data);
    inAppBrowserRef && inAppBrowserRef.executeScript({ code: "onData(\""+data+"\")" }); // Clear out the command in localStorage for subsequent opens.
}

var app = {
    // Application Constructor
    initialize: function() {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {        
        this.receivedEvent('deviceready');
        console.log('device ready');
        alert('device ready');
        
        
        var url = environment == 'Development' ? developmentUrl : productionUrl;        
        console.log('opening ' + url);
        inAppBrowserRef = cordova.InAppBrowser.open(url, '_blank', 'location=no,zoom=no,toolbar=no,hidenavigationbuttons=yes'); //open the in app browser with no location bar
        console.log('done opening, adding loadstop event listner');
        inAppBrowserRef.addEventListener( "loadstop", function() { //Fired when browser is finished loading
            console.log('loadstop fired');
            //alert('inappbrowser loaded');
            //alert('device: ' + device.cordova);
            
            console.log('clearing native command');
            inAppBrowserRef.executeScript({ code: "localStorage.setItem( 'nativeCommand', '' );" }); // Clear out the command in localStorage for subsequent opens.
            console.log('cleared native command');
            console.log('defining resetCommand');
            var resetCommand = function() {
                inAppBrowserRef.executeScript({
                    code: "localStorage.setItem( 'nativeCommand', '' )"
                });
            }            
            console.log('defining setStatus');

            var setStatus = function(status) {
                inAppBrowserRef.executeScript({
                    code: "localStorage.setItem( 'nativeCommandStatus', " + status + " )"
                });
            }
            console.log('done defining, registering callback');
            //alert('registering callback');
            capturesdk.registerCallback("All",callbackSuccess,callbackError);
            //alert('testing callback');            
            console.log('testing callback');
            capturesdk.testCallback("All",function(result){
                console.log('successfully tested callback: ' + JSON.stringify(result));
               },
               function(error) {
                   console.log('error testing callback: ' + JSON.stringify(error));
                }
            );
            

          /*  
            // Start an interval
            var loop = setInterval(function() {                
                inAppBrowserRef.executeScript(
                    {
                        code: "localStorage.getItem( 'nativeCommand' )"
                    },
                    function( values ) {
                        var cmd = JSON.parse(values[0]);
                        const {id, command, props} = cmd;
                        
                        if(id && id != '' && id != currentCommandId) {                            
                            currentCommandId = id;
                            if(command == 'PrintReceipt') {
                                setStatus("Printing");                                
                                printObj = {                                    
                                    cutReceipt: true,
                                    openCashDrawer: true,
                                    base64Image: props.base64Image,
                                    width: props.width,
                                }
                                starprnt.printBase64Image(props.printeraddress,props.printertype, printObj, function(result){
                                    setStatus("Success");
                                    },
                                    function(error) {
                                    setStatus("Error Printing: " + error);
                                });
                            }
                        }
                        resetCommand(); //We are done processing, so reset commandId
                    }
                );
            }, 2000 );
*/
        });
        
    },
    
    // Once the InAppBrowser finishes loading
    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }
};

app.initialize();
