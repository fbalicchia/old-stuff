'use strict'

var kafka = require('kafka-node')
var uuid = require('uuid')
var Producer = kafka.Producer
var Client = kafka.KafkaClient
var clientId = 'producer-example' + uuid.v4()
var client = new Client('localhost:9092', clientId, undefined, undefined, undefined)
var topic = 'test'
var producer = new Producer(client, { requireAcks: 1 })

producer.on('ready', function () {
  for (let i = 0; i < 10; i++) {
    var message = 'Message : ' + i
    console.log('send message ' + message)
    producer.send([
      {
        topic: topic,
        messages: message
      }
    ], function (err, result) {
      if (err) {
        console.log('error', err)
      }

      console.log(err || result)
      process.exit()
    })
  }
})

