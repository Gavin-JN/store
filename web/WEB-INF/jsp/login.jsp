<%--
  Created by IntelliJ IDEA.
  User: mao19
  Date: 2024/10/16
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
<%--    <link rel="stylesheet" type="text/css" href="">--%>
    <title>login</title>
</head>
<body>
<div  class="loginCss">
    <p class="pCss">Sign    In</p>
    <form action="/login" method="post" class="formCss" onsubmit="return errFunction();">
        <table>
            <tbody class="tableCss">
            <tr>
                <td>Username:</td>
                <td><input type="text" name="username" class="textCss" id="usernameId"></td>

<%--                <td> <p id="errOfusername" style="font-size: 12px;color: red;font-family: Arial">&nbsp;</p></td>--%>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password" class="textCss" id="passwordId"></td>
<%--                <td> <p id="errOfpassword" style="color: red ; font-size: 12px;font-family:Arial"> &nbsp;</p> </td>--%>
            </tr>
            <tr>
                <td></td>
                <td align="center"><input type="submit"value="Sign in" class="butCss" >&nbsp;</td>
            </tr>
            </tbody>
        </table>
    </form>
<%--    //显示用户信息的提示--%>
    <div class="errorOfUsername">
        <p id="errOfusername" style="font-size: 12px;color: red;font-family: Arial">&nbsp;</p>
    </div>
<%--    //显示用户密码的相关提示--%>
    <div class="errorOfPassword">
        <p id="errOfpassword" style="color: red ; font-size: 12px;font-family:Arial"> &nbsp;</p>
    </div>

    <div  class="upCss">
        <pre style="font-size: small">Not the user yet <a href="signUp.jsp">sign up</a> now</pre>
    </div>

</div>
<script>
    function errFunction()
    {
        var pass=document.getElementById("passwordId").value;
        var user=document.getElementById("usernameId").value;

        var x=document.getElementById("errOfusername");
        var y=document.getElementById("errOfpassword");
        var ifRun=true;
        if (user === "") {
            x.innerText = "Please input username!";
            ifRun=false;
        }
        if (pass === "") {
            y.innerText = "Please input password!";
            ifRun=false;
        }
        return ifRun;
    }

</script>
<style>
.pCss{
padding-top: 40px;
text-align: center;
font-size: xxx-large;
font-family: "Bradley Hand ITC";
}

.loginCss{
box-shadow:0 0 10px #000;
margin-top: 12%;
margin-left: 62%;
width: 500px;
height: 520px;
border-radius: 16px;
align-items: center;
font-size: large;
font-family: "Bradley Hand ITC";

}

.textCss{
margin-top: 0;
align-content: center;
border-radius: 8px;
width: 230px;
height: 36px;
background-color: #cfd9df;
border: 1px ;
}

.butCss{
width: 200px;
height: 36px;
border-radius: 6px;
background-color: #008CBA;
border: none;
font-size: larger;
font-family: "Bradley Hand ITC";

}
.butCss:hover {
box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
}

.formCss{
    margin-top: 50px;
    margin-right: 10px;
    line-height: 80px;
    height: 60px;
    margin-left: 18%;
}
.tableCss{
    align-content: center;
    margin-top: 50px;
    margin-right: 10px;
    line-height: 80px;
    height: 60px;
}
.upCss{
margin-top: 20%;
margin-left: 38%;
}

.errorOfUsername{
   margin-top: -2%;
margin-left: 37%;
}

.errorOfPassword{
    margin-top: 15%;
    margin-left: 37%;
}
</style>
</body>
</html>
