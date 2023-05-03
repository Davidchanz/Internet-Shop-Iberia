$(document).ready(function() {
  $(".sorting-indicator span").click(function() {
    $(this).siblings().removeClass("active");
    $(this).addClass("active");
    sendSortingParams();
  });
});
function sendSortingParams(){
    var sort = document.getElementById("sortBox");
    var sortingParams = "&sortBy=";
    var sortName = sort.options[sort.selectedIndex].value;

    var sortBy = document.getElementById("sortBy");
    sortBy.value = sortName;
    sortBy.checked = true;
    var sortTo = document.getElementById("sortTo");

    sortingParams += sortName + "&sortTo=";
    var asc = document.getElementById("asc");
    var desc = document.getElementById("desc");
    if(asc.classList[1] == "active"){
        sortingParams += "asc";
        sortTo.value = "asc";
        sortTo.checked = true;
    }else{
        sortingParams += "desc";
        sortTo.value = "desc";
        sortTo.checked = true;
    }
    var params = window.location.search;
    if(params.includes("sortBy=")){
        params = params.substring(0, params.indexOf("&sortBy="));
    }
    window.location.replace(window.location.pathname + params + sortingParams);
}

var sort = document.getElementById("sortBox");
sort.onclick = function(){
    sendSortingParams();
}