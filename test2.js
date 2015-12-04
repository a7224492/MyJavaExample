function employee(name,job,born){
    this.name = name;
    this.job = job;
    this.born = born;
}

var bill = new employee('jiangzhen','engineer', 'guizhou');

bill.salary = 2000;

console.log(bill.salary);
