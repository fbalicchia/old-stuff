'use strict'
var socket = require('zeromq')
var pattersMap = { 0: 'pull', 1:'rep', 2: 'sub'}


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
function Inputzeromq (config, eventEmitter) {
  this.config = config
  this.eventEmitter = eventEmitter

}
/**
 * Plugin start function, called after constructor
 */

Inputzeromq.prototype.start = function () {
  var self = this
  if (!self.started) {
    let host = self.config.host || 'localhost'
    let port_zmq = self.config.port_zmq || '3000'
    let zeromqHost = 'tcp://'.concat(host).concat(':').concat(port_zmq)
    // fire and forget
    if(self.config.pattern === 0 ){
      const laSock = socket.socket(pattersMap[self.config.pattern])
      //when network connection is closed any data wait 0.5 sec before is discared
      laSock.setsockopt(socket.options['linger'], 500)
      laSock.setsockopt(socket.options['connect_timeout'], 2500)
      laSock.setsockopt(socket.options['connect_timeout'], 2500)
      laSock.connect(zeromqHost)
      laSock.on('message', function (msg) {
        self.eventEmitter.emit('data.raw',`${msg}` , {sourceName: 'ZeroMQ ' + host, patter: 'fireAndForget'})      
      }) //request/response
    }else if (self.config.pattern === 1){
      const laSock = socket.socket(pattersMap[self.config.pattern])
      laSock.bindSync(zeromqHost)
      laSock.on('message', function (msg) {
        self.eventEmitter.emit('data.raw',`${msg}` , {sourceName: 'ZeroMQ ' + host})
        //Add address where is Logagent is binding
        laSock.send(`Received '${msg} from Logagent`)
      })
    }else if (self.config.pattern === 2 && self.config.topic){
      const laSock = socket.socket(pattersMap[self.config.pattern])
      laSock.connect(zeromqHost)
      laSock.subscribe(self.config.topic)  
      laSock.on('message', function (msg,topic) {
        self.eventEmitter.emit('data.raw',`${msg}` , {sourceName: 'ZeroMQ ' + host, topic: self.config.topic})      
      })
    }else {
      consoleLogger.error('ZeroMQ pattern is not supported ')
      return
    }
    self.started = true
    consoleLogger.log('ZeroMQ input plugin started')
  }

}
/**
 * Plugin stop function, called when logagent terminates
 * we close rabbbitmq consumer.
 */
Inputzeromq.prototype.stop = function (cb) {
  consoleLogger.log('stop ZeroMQ input plugin')
  this.start = false
  cb()
}
module.exports = Inputzeromq