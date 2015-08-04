$(document).ready(function() {

    $("#person").click(function(){ 
            $("#displist").empty();
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
            $("#displist").append('<form action="SearchRole">' +
                                    '<input type="radio" name="listBy" value="1"> Police '+
                                    '<input type="radio" name="listBy" value="2"> Politician '+
                                    '<input type="radio" name="listBy" value="3"> Soldier '+
                                    '<input type="radio" name="listBy" value="4"> Celebrity '+
                                    '<input type="radio" name="listBy" value="5"> Worker <br/>'+
                                    '<input type="submit" value="Search">'+
                                    '</form>');

    });

    var searchperson = function(){
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

    var deleteButton = function(e) {
        if (!confirm("Are you sure you want to delete the employee?")) {
        return;
        }
        var id = $(this).val();
        var ajaxCall = $.ajax({
            url: "remove/" + id,
            type: "DELETE",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            }
        });
        ajaxCall.done(function(data) {
            if (data == true) {
                var tr = $(e.target).closest("tr");
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

    $("#persons").on("click",".delete",function(e) {
        deleteButton();
        e.preventDefault();
    });

});
