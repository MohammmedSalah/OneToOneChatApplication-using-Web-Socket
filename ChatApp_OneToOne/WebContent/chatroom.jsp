<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Chat Room</title>
<script type="text/javascript" src="js/chatscript.js"></script>
</head>
<body>

	<table>
		<tr>
			<td colspan="2"><input type="text" id="username"
				placeholder="Username" />
				<button type="button" onclick="connect();">Connect</button></td>
				Online Users Now:<textarea readonly="true" rows="1" cols="3" id="counter">0</textarea>
		</tr>
		<tr>
			<td><textarea readonly="true" rows="10" cols="80" id="log"></textarea>
			</td>
		</tr>
		<tr>
			<td><input type="text" size="15" id="to" placeholder="To" /> 
			<input type="text" size="51" id="msg" placeholder="Message" />
			<button type="button" onclick="send();">Send</button>
		</tr>
	</table>
</body>
</body>
</html>