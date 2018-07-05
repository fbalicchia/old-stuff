'use strict'

var kafka = require('kafka-node')
var uuid = require('uuid')
var fs = require('fs')
var Producer = kafka.Producer
var Client = kafka.KafkaClient
var clientId = '`' + uuid.v4()
var client = new Client('localhost:9092', clientId, undefined, undefined, undefined)
var topic = 'test'




var fileStream = fs.createReadStream('order.xml', 'utf8')
var producer = new Producer(client, { requireAcks: 1 })


producer.on('ready', function () {
    var data = '';
    
    fileStream.on('data', function(chunk) {  
      data += chunk;
    }).on('end', function() {
        producer.send([
          {
            topic: topic,
            messages: data
          }
        ], function (err, result) {
          if (err) {
            console.log('error', err)
          }

          console.log(err || result)
          process.exit()
        })
    })
})


