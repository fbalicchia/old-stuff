var zmq = require('zeromq')
var sock = zmq.socket('req')

sock.connect('tcp://127.0.0.1:3000')          

var counter = 0                             

sock.on(`message`, function (msg) {           
	console.log(`Response received from logagent: "${msg}"`)
	setTimeout(sendMessage, 2000)               
})

sendMessage();

function sendMessage () {
	const msg = `MSG #${counter++}`
	console.log(`Sending ${msg}`)
	sock.send(msg)                       
}