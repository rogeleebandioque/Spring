$(document).ready(function() {


    $("#logout").click(function(){
        $("#logoutForm").submit();
    });

    $("#userform").submit(function(e) {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");

        var username = $("#username").val();
        var password = $("#password").val();
        var role = $("#role").val();
        
        var ajaxCall = $.ajax({
            url: "AddUser",
            type: "POST",
            dataType:"json",
            data: {
                    "username":username,
                    "password":password,
                    "role":role
                  },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        });

        ajaxCall.done(function(user) {
            alert("User Added!");
            $("#userTable").find("tr:gt(1)").remove();
            $.each(user, function(index, element) {
                $("#userTable").append("<tr>" + 
                    "<td>"+element.id+"</td>" +
                    "<td>"+element.username+"</td>" +
                    "<td>"+element.role+"</td>" +
                    "<td>"+
                    ((role == "ROLE_ADMIN") ? "<button class=\"update\" value= \""+element.id+"\">Update</button>"+
                    "<button class=\"deleteuser\" value =\""+element.id+"\">Delete</button>": "NONE")+
                                "</td></tr>");
            });
        });
        ajaxCall.fail(function() {
            alert("Unable to Add.");
        });
        e.preventDefault();
    });

    var deleteButton = function(x) {
        if (!confirm("Delete User?")) {
            return;
        }
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        
        var id = $(x).val();
        var ajaxCall = $.ajax({
            url: "removeuser/" + id,
            type: "DELETE",
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            }
        });
        ajaxCall.done(function(data) {
            if (data == true) {
                var tr = $(x).closest("tr");
                tr.remove();
            } else {
                alert("Unable to delete user.");
            }
        });
        ajaxCall.fail(function() {
            alert("Unable to delete.");
        });
    };
    $("#userTable").on("click",".deleteuser",function(event) {
        deleteButton($(this));
        event.preventDefault();
    });
});
