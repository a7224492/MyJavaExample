var fs = require('fs');

function sleep(milliSeconds){
    var startTime = new Date().getTime();
    while (new Date().getTime() < startTime+milliSeconds);
}

var stu = fs.readFile('1.json', function(err,res){
    sleep(2000);
    console.log('1json');
    fs.readFile('2.json',function(err,res){
        sleep(2000);
        console.log('2json');
        fs.readFile('3.json', function(err,read){
            sleep(2000);
            console.log('3json');
            });
        });
    });
