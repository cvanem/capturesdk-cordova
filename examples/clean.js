const fs = require('fs-extra')

fs.remove('./inappbrowser', err => {
    if (err) return console.error(err)
  
    console.log('Remove ./inappbrowser success!') // I just deleted my entire HOME directory.
})

fs.remove('./capturesdk-cordova', err => {
    if (err) return console.error(err)
  
    console.log('Remove ./capturesdk-cordova success!') // I just deleted my entire HOME directory.
})