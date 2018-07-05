var zmq = require('zeromq');
var sock = zmq.socket('sub');

sock.connect('tcp://localhost:3000');
sock.subscribe('topic-example');
console.log('Subscriber connected to port 3000');

sock.on('message', function(topic, message) {
  console.log('received a message related to:', topic.toString(), 'containing message:', message.toString());
});