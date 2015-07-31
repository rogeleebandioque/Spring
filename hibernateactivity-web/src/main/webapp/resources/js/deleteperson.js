function deletePerson(id){
    $.ajax({
        url: "delete/"+id,
        type: "DELETE",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
            }
        })
    .done(function() {
        console.log("delete")
        var respContent = "Deleted";
        var rowToDelete = $("#"+id).closest("tr");
        rowToDelete.remove();
        $("#usermessage").html(respContent);
    });
}

