<%-- 
    Document   : newpassword
    Created on : May 4, 2014, 4:58:07 PM
    Author     : ALI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Password</title>
        <script>
            function validate(){
                var result = true;
                if(document.getElementById("newPassword").value !== document.getElementById("retypeNewPassword").value){
                    alert('Both passwords should be same');
                    document.getElementById("retypeNewPassword").focus();
                    result = false;
                }
                if(document.getElementById("newPassword").value === "" && document.getElementById("retypeNewPassword").value === ""){
                    alert("Provide some password");
                    document.getElementById("newPassword").focus();
                    result = false;
                }
                return result;
            };
        </script>
    </head>
    <body>
        <center>
            <form name="passwordForm" onsubmit="return validate();" action="SetNewPassword">
                <h3>Please enter a new Password</h3>
                <br>
                <input type="password" id="newPassword" name="newPassword" placeholder="New Password"/>
                <br>
                <input type="password" id="retypeNewPassword" name="retypeNewPassword" placeholder="Retype Password"/>
                <br>
                <input type="hidden" name="userId" value="<%= request.getAttribute("userId")%>"/>
                <button name="savePassword">Save Password</button>
            </form>
        </center>
    </body>
</html>
