'use strict'

var grok = require('node-grok')
var grokDefaultpatterns = grok.loadDefaultSync()

/**
  * sourceName - origin of the log, e.g. file name
  * config - properties from the config section for this plugin
  * data - the log message as string
  * callback - callback function (err, data).
  */
module.exports = function (sourceName, config, data, callback) {
  try {
    var drop = false
    /*
    if (config.matchSource) {
      if (!config.matchSource.test(sourceName)) {
        // pass data for unmatched source names
        return callback(null, data)
      }
    }
      */
      
    if(typeof config.patterns !== 'undefined' && config.patterns.length > 0){
      config.patterns.forEach(function(pattern){
        drop = checkData(pattern,data)
        if(! drop) return 
      })
    }

    if(config.patternsFile && config.patternId && drop){
      var coll = new grok.GrokCollection()
      coll.loadSync(config.patternsFile) 
      var patternFile =  coll.getPattern(config.patternId)
      if(patternFile){
        drop = checkData(patternFile,data)      
      }
    }
    return drop ? callback(null,null) : callback(null,data)
    
  }catch(err){
    return callback(null, data)
  }

  function checkData(pattern,data){
    var patternIstance = grokDefaultpatterns.createPattern(pattern)
    var result = patternIstance.parseSync(data)
    return result === undefined || result === null 
  }

}