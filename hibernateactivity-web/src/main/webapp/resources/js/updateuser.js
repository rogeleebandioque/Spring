$(document).ready(function() {
    $(".update").click(function(e){
        alert("j");
        var id = $(this).val();
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
         var ajaxCall = $.ajax({
            url: "/updateuser/"+id,
            type: "GET",
            dataType: "json",
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }        
        });
        ajaxCall.done(function(data) {
            $("body").append("<div id=\"change\"> <br/><br/><br/>" + 
                "<form name=\"userform\" modelAttribute=\"userform\">"+
                "<table border=\"1\" align=\"center\"> <input type=\"hidden\" name=\"id\"value=\""+ data.id +"\"/>"+
                "<tr><td>Username: </td><td><input type=\"text\" name=\"username\" value=\""+data.username+"\"/></td></tr>" +
                "<tr><td>Password: </td><td><input type=\"password\" name=\"password\"value=\""+data.password+"\"/></td></tr>" +
                "<tr><td>Role</td><td>"+
                "<select name=\"role\"><option value=\"ROLE_ADMIN\">Admin</option><option value=\"ROLE_USER\">User</option></select>"+
                "</td></tr>" +
                "<tr><td colspan=\"2\"><center><input type=\"submit\"value=\"Submit\">" +
                "<button id=\"cancel\">Cancel</button></center></td></tr></table></form>");
        });
    });
    $("body").on("submit","#userform",function(e){
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        var id=$("id").val();
        var username = $("#username").val();
        var password = $("#password").val();
        var role = $("#role").val();
        
        var ajaxCall = $.ajax({
            url: "UpdateUser",
            type: "POST",
            dataType:"json",
            data: {
                    "username":username,
                    "password":password,
                    "role":role,
                    "id":id
                  },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        });

        ajaxCall.done(function(user) {
            alert("User Updated!");
            $("#userform").remove();
            $("#userTable").find("tr:gt(1)").remove();
            $.each(user, function(index, element) {
                $("#userTable").append("<tr>" + 
                    "<td>"+element.id+"</td>" +
                    "<td>"+element.username+"</td>" +
                    "<td>"+element.role+"</td>" +
                    "<td><button id=\""+element.id+"\"onclick=\"location.href=\'upuser/"+element.id+"\'\">Update</button>" +
                    "<button class=\"deleteuser\" value =\""+element.id+"\">Delete</button></td></tr>");
            });
        });
        ajaxCall.fail(function() {
            alert("Unable to edit.");
        });
        e.preventDefault();
    });
});
