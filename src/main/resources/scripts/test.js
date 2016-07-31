
function test() {
    return "Hello, from script file!";
}

var called = 0;

function hello(){
    called++;
    return "I have been called "+called+" times!";
}

function respond(message){
    called++;
    return "You said: "+message.getArgument(2);
}