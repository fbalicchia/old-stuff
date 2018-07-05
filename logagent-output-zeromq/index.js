'use strict'
var socket = require('zeromq')
var pattersMap = { 0: 'push', 1:'req', 2: 'pub'}


function logToErrorStream (message) {
  console.error(new Date().toISOString(), message)
}
var consoleLogger = {
  log: logToErrorStream,
  debug: logToErrorStream,
  error: logToErrorStream
}

/**
 * Constructor called by logagent
 * @config cli arguments and config.configFile entries
 * @eventEmitter logagent eventEmitter object
 */
function Outputzeromq (config, eventEmitter) {
  this.config = config
  this.eventEmitter = eventEmitter

}
/**
 * Plugin start function, called after constructor
 */

Outputzeromq.prototype.start = function () {
  var self = this
  if (!self.started) {
    let host = self.config.host || 'localhost'
    let port_zmq = self.config.port_zmq || '3000'
    let zeromqHost = 'tcp://'.concat(host).concat(':').concat(port_zmq)
    const laSock = socket.socket(pattersMap[self.config.pattern])
    // req/rep patter
    if(self.config.pattern === 1 ){
      laSock.connect(zeromqHost)
      var replyNbr = 0;
      laSock.on('message', function(msg) {
        consoleLogger.log('reply number ', replyNbr, msg.toString());
        replyNbr += 1;
      })
    }else{
      laSock.bindSync(zeromqHost)
    }
    this.client = laSock
    self.started = true
    this.eventEmitter.on('data.parsed', this.eventHandler.bind(this))
    consoleLogger.log('ZeroMQ output plugin started')
  }

}

Outputzeromq.prototype.eventHandler = function (data, context) {
  if(this.config.pattern === 2){
    this.client.send([this.config.topic,JSON.stringify(data)])
  }else{
    this.client.send(JSON.stringify(data))
  }
}


/**
 * Plugin stop function, called when logagent terminates
 * we close ZeroMq producer.
 */
Outputzeromq.prototype.stop = function (cb) {
  consoleLogger.log('stop ZeroMQ output plugin')
  this.start = false
  cb()
}
module.exports = Outputzeromq
