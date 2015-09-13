<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript" src="js/alpha.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<title>Submit Url to load</title>
</head>
<body>
	<p>
		URL:<br />
		<textarea rows="3" cols="90" name="url" id="text"
			onkeyup="onUrlChanged();"></textarea>
		<br /> User:<br /> <input type="text" id="user" />
	</p>

	<input type="submit" value="Submit" onclick="alpha();" />
	<br>
	<label id="result"></label>

</body>
</html>