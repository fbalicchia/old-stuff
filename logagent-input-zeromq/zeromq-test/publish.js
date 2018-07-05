var zmq = require('zeromq')
var sock = zmq.socket('pub')

sock.setsockopt(zmq.options['backlog'], 10)

Object.keys(zmq.options).forEach(function(key){
	console.log('key ' + key )
	console.log('value ' + zmq.options[key])
})

sock.bindSync('tcp://127.0.0.1:3000')
console.log('Publisher bound to port 3000')

setInterval(function(){
  console.log('send data to logagent')
  sock.send(['topic-example', 'value messege'])
}, 500)
