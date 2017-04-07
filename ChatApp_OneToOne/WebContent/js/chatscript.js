var username = null;
var websocket = null;
function connect(){
	username = document.getElementById("username").value;
	
	var host = "ws://localhost:8080/ChatApp_OneToOne/chat/"+username ; 
	var browserSupport = ("WebSocket" in window) ? true:false;
	if(browserSupport){
		websocket = new WebSocket(host);
		websocket.onopen = function() {
            display('Connected to server ... hello ' + username);
        };

        websocket.onclose = function() {
            display('Disconnected from server');
        };

        websocket.onmessage = function(event) {
        	var input = event.data;
        	var messageObj = JSON.parse(input);
        	if(messageObj.flage === "chat"){
        		display(messageObj.source+':\t'+ messageObj.message);
        	}
        	else if(messageObj.flage === "chat_counter"){
        		display_counter(messageObj.message);
        	}
        };

		
	}else{
		alert("Web Socket Not Supported in this Browser ");
		return ; 
	}

}

function send(){
	if(websocket != null){
		
		var to = document.getElementById("to").value;
		var msg = document.getElementById("msg").value;
		
		var messageObj = {flage:"chat", source:username, destination:to , message:msg};
        var json = JSON.stringify(messageObj);
        
        display(messageObj.source+':\t'+ messageObj.message);
        websocket.send(json);
	}
}
function display(text){
	var log = document.getElementById("log");
	log.value+=text+"\n";
}
function display_counter(text){
	var counter = document.getElementById("counter");
	counter.value = text;
}