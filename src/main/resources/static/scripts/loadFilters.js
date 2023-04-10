function updateFilters(params) {
    $(document).ready(function(){
      $.ajaxSetup({
        headers: {
          'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
      });
    });
    $("#filter-category").load("f", params);
}

var iDs = document.getElementsByClassName("product-id");
let params = "products=";
for(var i = 0; i < iDs.length; i++){
    params += iDs[i].innerText + ",";
}

updateFilters(params);

console.log("sdfg");