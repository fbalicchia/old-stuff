var zmq = require('zeromq')
var sock = zmq.socket('push')

sock.bindSync('tcp://127.0.0.1:3000')
console.log('Producer bound to port 3000')

setInterval(function(){
  console.log('send data to logagent')
  sock.send('Good message for logagent')
}, 500)