var params = window.location.search;
  if(params.includes("categoryId=")){
    var categoryId = params.split("&")[0].substring(params.indexOf("=")+1);
    document.getElementById("categoryId").value = categoryId;
    document.getElementById("categoryId").checked = true;
  }else if(params.includes("searchRequest=")){
    var searchRequest = params.split("&")[0].substring(params.indexOf("=")+1);
    document.getElementById("searchRequest").value = searchRequest;
    document.getElementById("searchRequest").checked = true;
  } else{
    var collectionId = params.split("&")[0].substring(params.indexOf("=")+1);
    document.getElementById("collectionId").value = collectionId;
    document.getElementById("collectionId").checked = true;
  }
  var sort = document.getElementById("sortBox");
  var sortName = sort.options[sort.selectedIndex].value;
  document.getElementById("sortBy").value = sortName;
  document.getElementById("sortBy").checked = true;

  var sortToValue = "";
  if(asc.classList[1] == "active"){
      sortToValue = "asc";
  }else{
      sortToValue = "desc";
  }
  document.getElementById("sortTo").value = sortToValue;
  document.getElementById("sortTo").checked = true;



  var page = document.getElementsByClassName("page active");
  console.log(page);
  console.log(page.length);
  console.log(page[1]);
  var number = page[0].textContent;
  document.getElementById("page").value = number;
  document.getElementById("page").checked = true;