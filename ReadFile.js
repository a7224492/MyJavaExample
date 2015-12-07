var fs = require('fs');

function sleep(milliSeconds){
    var startTime = new Date().getTime();
    while (new Date().getTime() < startTime+milliSeconds);
}

fs.readFile('1.json', function(err,read){
    sleep(2000);
    console.log('1.json');
    sleep(2000);
    });
