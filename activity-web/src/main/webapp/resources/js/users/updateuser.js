$(document).ready(function () {
    var update = function (e) {
        var id = e;
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        var ajaxCall = $.ajax({
            url: "/updateuser/" + id,
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        });
        ajaxCall.done(function (data) {
            $("#container").append("<div id=\"change\"> <br/><br/>"+
                    "<form id=\"editform\" modelAttribute=\"userform\">" +
                    "<table align=\"center\">"+
                    "<input type=\"hidden\" id=\"editid\"value=\"" + data.id + "\"/>" +
                    "<tr><th colspan=\"2\">Edit User</th></tr>"+
                    "<tr><td>Username: </td>"+
                    "<td><input type=\"text\" id=\"editusername\" value=\"" + 
                    data.username + "\"/></td></tr>" +
                    "<tr><td>New Password: </td>"+
                    "<td><input type=\"password\" id=\"editpassword\"/></td></tr>" +
                    "<tr><td>Role</td><td>" +
                    "<select id=\"editrole\">"+
                    "<option value=\"ROLE_ADMIN\">Admin</option>"+
                    "<option value=\"ROLE_USER\">User</option>"+
                    "</select></td></tr>" +
                    "<tr><td colspan=\"2\"><center>"+
                    "<input class=\"btn btn-success\"type=\"submit\"value=\"Submit\"/> " +
                    "<input class=\"btn btn-warning\"type=\"button\" id=\"cancel\" value=\"Cancel\"/>"+
                    "</center></td></tr></table></form>");
        });
    };
    $("#container").on("submit", "#editform", function (e) {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        var id = $("#editid").val();
        var username = $("#editusername").val();
        var password = $("#editpassword").val();
        var role = $("#editrole").val();

        var ajaxCall = $.ajax({
            url: "UpdateUser",
            type: "POST",
            dataType: "json",
            data: {
                "username": username,
                "password": password,
                "role": role,
                "id": id
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        });

        ajaxCall.done(function (user) {
            alert("User Updated!");
            $("#userTable").find("tr:gt(1)").remove();
            $.each(user, function (index, element) {
                $("#userTable").append("<tr>" +
                        "<td>" + element.id + "</td>" +
                        "<td>" + element.username + "</td>" +
                        "<td>" + element.role + "</td>" +
                        "<td><sec:authorize access=\"hasRole(\'ROLE_ADMIN\')\">" +
                        ((role === "ROLE_ADMIN") ? "<button class=\"btn btn-primary update\" value= \"" + 
                        element.id + "\">Update</button> " +
                        "<button value =\"" + 
                        element.id + "\" class=\"btn btn-danger deleteuser\">Delete</button>" : "NONE") +
                        "</td></tr>");
            });
            $("#change").remove();
        });
        ajaxCall.fail(function () {
            alert("Unable to edit.");
        });
        e.preventDefault();
    });
    $("#container").on("click", "#cancel", function (e) {
        $("#change").remove();
        e.preventDefault();
    });
    $("#userTable").on("click", ".update", function (e) {
        update($(this).val());
        e.preventDefault();
    });
});
