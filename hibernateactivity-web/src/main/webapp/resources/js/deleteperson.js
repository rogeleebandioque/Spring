$(document).ready(function() {
    var deleteLink = $("a:contains('Delete')");
    $(deleteLink).click(function(event) {
        console.log($(event.target).attr("href"));
        $.ajax({
        url: $(event.target).attr("href"),
        type: "GET",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
            },
        success: function() {
            console.log("delete")
            var respContent = "Deleted";
            var rowToDelete = $(event.target).closest("tr");
            rowToDelete.remove();
            $("#usermessage").html(respContent);
            }
        });
    event.preventDefault();
    });
}); 

