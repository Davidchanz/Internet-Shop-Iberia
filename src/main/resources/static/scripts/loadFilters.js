function updateFilters(params) {
    $(document).ready(function(){
      $.ajaxSetup({
        headers: {
          'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        }
      });
    });
    $("#filter-category").load("f", categoryId);
}

var params = window.location.search;
var categoryId = "categoryId=" + params.substring(params.indexOf("=")+1);

updateFilters(categoryId);