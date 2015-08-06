$(document).ready(function() {

    $("#logout").click(function(){
        $("#logoutForm").submit();
    });

    $("#person").click(function(){ 
            $("#displist").empty();
            $("#disp").empty();
            $("#displist").append('<input type="text" name="searchBy"id="searchBy" placeHolder="Search.."/><br/>'+
                                    '<select name="listBy" id=listBy>'+
                                    '<option value="last_name"> Last Name </option>'+
                                    '<option value="date_hired"> Date Hired </option>'+
                                    '<option id="listBy" value="grade"> Grade </option> </select> '+
                                    '<select name="order" id="order"><option value="asc"> Ascending </option> '+
                                    '<option value="desc"> Descending </option></select> <br/>'+
                                    '<input type="submit" value="Search">');
    });

    $("#prole").click(function(){ 
            $("#displist").empty();
            $("#disp").empty();
            $("#disp").append('<select name="listBy">' + 
                                    '<option value="1"> Police </option> '+
                                    '<option value="2"> Politician </option>'+
                                    '<option value="3"> Soldier </option>'+
                                    '<option value="4"> Celebrity </option>'+
                                    '<option value="5"> Worker <br/>'+
                                    '<input type="submit" value="Search">'+
                                    '</select>');

    });

    var searchperson = function(){
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        
        var searchBy = $('#searchBy').val();
        var list = $('#listBy').val();
        var order = $('#order').val();
        console.log(searchBy + " " + list + " "+order);
        var aCall = $.ajax({    
            url: "Persons",
            type: "POST",
            dataType: "json",
            data: {
                    "search":searchBy,
                    "listBy":list,
                    "order":order
                    },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }            
        });
        aCall.done(function(person) {
            $("#persons").find("tr:gt(1)").remove();
            $.each(person, function(index, element) { 
                var contact = "";
                $.each(element.contact, function(i,e){
                    contact = contact + e.type + ":" + e.contact + "<br/>";
                });
                $("#persons").append("<tr>" + 
                "<td>"+element.id+"</td>" +
                "<td>"+element.names.first_name+" "+ element.names.last_name+"</td>" +
                "<td>"+new Date(element.date_hired).toISOString().slice(0, 10)+"</td>" +
                "<td>"+element.grade+"</td>" +
                "<td>"+ contact +"</td>" +
                "<td><button id=\""+element.id+"\"onclick=\"location.href=\'update/"+element.id+"\'\">Update</button>" +
                "<button class=\"delete\" value =\""+element.id+"\">Delete</button></td></tr>");
            }); 
        });
        aCall.fail(function() {
            alert("Sorry.");
        });
    };

    var searchrole = function(){
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        
        var list = $('#listBy').val();
        var aCall = $.ajax({    
            url: "SearchRole",
            type: "POST",
            dataType: "json",
            data: {
                    "listBy":list,
                    },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        });
        aCall.done(function(person) {
            $("#persons").find("tr:gt(1)").remove();
            $.each(person, function(index, element) { 
                var contact = "";
                $.each(element.contact, function(i,e){
                    contact = contact + e.type + ":" + e.contact + "<br/>";
                });
                $("#persons").append("<tr>" + 
                "<td>"+element.id+"</td>" +
                "<td>"+element.names.first_name+" "+ element.names.last_name+"</td>" +
                "<td>"+new Date(element.date_hired).toISOString().slice(0, 10)+"</td>" +
                "<td>"+element.grade+"</td>" +
                "<td>"+ contact +"</td>" +
                "<td><button id=\""+element.id+"\"onclick=\"location.href=\'update/"+element.id+"\'\">Update</button>" +
                "<button class=\"delete\" value =\""+element.id+"\">Delete</button></td></tr>");
            }); 
        });
        aCall.fail(function() {
            alert("Sorry.");
        });
    };

    var deleteButton = function(x) {
        if (!confirm("Delete Employee?")) {
            return;
        }
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        
        var id = $(x).val();
        var ajaxCall = $.ajax({
            url: "remove/" + id,
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
                alert("Unable to delete employee.");
            }
        });
        ajaxCall.fail(function() {
            alert("Unable to delete.");
        });
    };

    $("#s").on("submit","#displist",function(event){
        searchperson();
        event.preventDefault();
    });    
    $("#s").on("submit","#disp",function(event){
        searchrole();
        event.preventDefault();
    });

    $("#persons").on("click",".delete",function(event) {
        deleteButton($(this));
        event.preventDefault();
    });

});
