<%--
  Created by IntelliJ IDEA.
  User: undefined
  Date: 2020/11/21
  Time: 23:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body id="win">
<div id="ha" style="position: relative">
    <p>This is ha</p>
    <button onclick="zd()">动一下</button>
</div>

<div id="fa">
    <p>This is fa</p>
</div>

<form name="myTest" method="post" enctype="multipart/form-data" style="position: relative" id="wi">
    <input name="fileA" type="file">
    <input name="fileB" type="file">
    <input type="submit">
</form>

<script src="./root/assets/js/pinecone.js"></script>
<script>
    var pageData = ${szPageData};

    $_PR({
        "ha": function () {
            trace("Jesus!Guess what fuck now. ha");
            $_PINE( "#ha p" ).myText( pageData["debug"] );
        },
        "fa": function () {
            trace("Jesus!Guess what fuck now. fa");
            $_PINE( "#fa p" ).myText( pageData["debug"] );
        }
    }).summon($_GET["action"]);

</script>
<script >

    function zd(u){
        var a=['top','left'],b=0;
        u=setInterval(function(){
            document.getElementById('ha').style[a[b%2]]=(b++)%4<2?0:4;
            if(b>15){clearInterval(u);b=0}
        },32)
    }
</script>
</body>
</html>
