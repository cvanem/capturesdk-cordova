const fs = require('fs-extra')

// Async with promises:
fs.copy('./index.js', './inappbrowser/www/js/index.js')
  .then(() => console.log('copy index.js success!'))
  .catch(err => console.error(err))

fs.copy('./MainActivity.java', './inappbrowser/platforms/android/src/app/greenlink/myapplication/MainActivity.java')
  .then(() => console.log('copy MainActivity.java success!'))
  .catch(err => console.error(err))


