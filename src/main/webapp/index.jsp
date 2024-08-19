<html>
<head>
    <link rel = "stylesheet" type = "text/css" href = "styles.css">
    <script>
        function sendInputChange() {
            var inputValue = document.getElementById("inputField").value;

            var req = new XMLHttpRequest();
            req.open("GET", "autocomplete?keyword=" + inputValue , true);
            req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            req.send();
            req.onreadystatechange = function() {
                if (req.readyState === 4 && req.status === 200) {
                    document.getElementById("result").innerText = req.responseText;
                }
            };
        }
    </script>
</head>
<body>
<form action = "Search">
    <input type = "text" name = "keyword" id="inputField" name="inputField" onkeydown="sendInputChange()">
    <button type = "submit">Search</button>
</form>
<p>Autocomplate Advice: <span id="result"></span></p>
</body>

</html>