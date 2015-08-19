$(document).ready(function () {
    var addperson = function (e) {
        var name = $(e).html();
        $("#chosen").append('<option class=\"remove\" value=\"' + $(e).val() + 
                '\">' + name + '</option>');
        $(e).remove();
    };
    var rmperson = function (t) {
        var name = $(t).html();
        $("#team").append('<option class=\"choice\" value=\"' + $(t).val() + 
                '\">' + name + '</option>');
        $(t).remove();
    };
    $("#chosen").on("dblclick", ".remove", function (e) {
        rmperson($(this));
        e.preventDefault();
    });
    $("#team").on("dblclick", ".choice", function (e) {
        addperson($(this));
        e.preventDefault();
    });
    var select = function() {
        $('#chosen option').prop("selected", "selected");
    }
    
    $("#container").on("submit","#projectForm", function(e) {
        select();
    });

});
