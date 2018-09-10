const fs = require('fs-extra')

// Async with promises:
fs.copy('./index.js', './inappbrowser/www/js/index.js')
  .then(() => console.log('copy index.js success!'))
  .catch(err => console.error(err))

  fs.copy('./Podfile', './inappbrowser/platforms/ios/Podfile')
  .then(() => console.log('copy Podfile success!'))
  .catch(err => console.error(err))