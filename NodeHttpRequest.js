var http = require('http');

var qs = require('querystring');

var data = {
    a:123
};

var content = qs.stringify(data);

var options = {
    hostname: '127.0.0.1',
    port:8080,
    path: '/pay/pay_callback?'+content,
    method:'GET'
};

var req = http.request(options,function(res){
        res.setEncoding('utf8');
        res.on('data',function(chunk){
                console.log('Message: '+chunk);
            });
    });

req.on('error', function(e){
        console.log('problem with request:'+e.message);
    });

req.end();
