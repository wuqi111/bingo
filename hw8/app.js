//'use strict'

var express = require('express')
//var cors = require('cors')
var app = express()
var request = require('request')
//app.use(cors())
//app.use(express.static('public'));

app.all('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header("Access-Control-Allow-Methods","GET");
    res.header("X-Powered-By",' 3.2.1')
    res.header("Content-Type", "application/json;charset=utf-8");
    next();
});

app.get('/a', function(req, res){
    
      var response = {
       "keyword":req.query.keyword,
       "category":req.query.category,
           "distance":req.query.distance,
           "location1":req.query.location1,
             "location2":req.query.location2,
   };
    
     var urlone='https://maps.googleapis.com/maps/api/place/nearbysearch/json?location='+response.location1+','+response.location2+'&radius='+response.distance+'&type='+response.category+'&keyword='+response.keyword+'&key=AIzaSyCLMrfYVBaXfPIa2AK6jZ0xCQdyBe9PF7w';
    
     //   var stock = req.params.stock.toLowerCase()
       request(urlone, function(error, response, body){
        if(!error && response.statusCode == 200){
        var result = JSON.parse(body);
        res.send(result)
}
   // console.log(username);
   // console.log("/list_user GET 请求");  
})
})

app.get('/b', function(req, res){
    
      var response = {
       "next":req.query.nextpage,
   };
    console.log(response.next)
     var url='https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken='+response.next+'&key=AIzaSyCLMrfYVBaXfPIa2AK6jZ0xCQdyBe9PF7w';
    
     //   var stock = req.params.stock.toLowerCase()
       request(url, function(error, response, body){
        if(!error && response.statusCode == 200){
        var result = JSON.parse(body);
        res.send(result)
            
        
}
   // console.log(username);
   // console.log("/list_user GET 请求");  
})
})


var yelp = require('yelp-fusion');
var apiKey='K2mBXakgpsGZubHkGwEYNMKJWh5I1386wEI1v1v-PSaVtwP7XnKlJSXHz5wCnKZ9Tuc-13dBZfb3EZbC1_1P-I9Hc-2MjPp4ICKFqdaza0kl5cazT8CX-KlHyJK1WnYx';
var client = yelp.client(apiKey);
 
app.get('/gg', function(req, res){

// matchType can be 'lookup' or 'best'
client.businessMatch('best', {
  "name": req.query.name,
  "address1": req.query.address,
"city": req.query.city,
 "state": req.query.state,
  "country":req.query.country
}).then(response => {
    var result=response.jsonBody;
    res.send(result);
}).catch(e => {
  console.log(e);
});
    console.log(req.query.state);
    })



app.get('/kk', function(req, res){
  var reviewin=req.query.reivew;
    console.log(reviewin);
client.reviews(reviewin).then(response => {
    res.send(response.jsonBody);
  console.log(response.jsonBody);
}).catch(e => {
  console.log(e);
});
    })


//app.listen(8081, () => console.log('Server running on port 8081'))


//var running_port=process.env.PORT || 8081
//app.listen(running_port);
//console.log('server are running in port',running_port)

app.listen(8081);