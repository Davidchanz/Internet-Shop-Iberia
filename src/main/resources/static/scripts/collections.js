function manageCollection(value) {
    $("#add-to-list-select").load("coll?collectionId="+value+"&"+window.location.search.substring(1));
}

$('#add-to-list').change(function(){
    var value = $(this).val();
    manageCollection(value);
});