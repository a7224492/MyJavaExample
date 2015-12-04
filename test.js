function People(name){
    this.name = name; 
    this.introduce = function(){
        console.log('i am '+this.name);
    }
}

People.Run = function(){
    console.log('I can run');
}

People.prototype.introduceChinese = function(){
    console.log('prototype');
}

var people = new People('jiangzhen');
people.hello = function(){
    console.log(this.name+'hello');
};
people.introduce();
People.Run();
people.introduceChinese();
people.hello();
