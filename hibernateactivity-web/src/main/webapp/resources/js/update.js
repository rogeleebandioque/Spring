function updatePerson(person_id){
    $.ajax({
        url: "update/"+person_id,
        type: "GET",
        });
}
