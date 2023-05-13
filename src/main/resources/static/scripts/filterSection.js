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
  var number = page[0].textContent;
  document.getElementById("page").value = number;
  document.getElementById("page").checked = true;

  var sectionHeaders = document.getElementsByClassName("section-header");
  for(var k = 0; k < sectionHeaders.length; k++){
      sectionHeaders[k].onclick = function(){
          console.log(this);
          var section = this.parentElement;
          console.log(section);
          section.classList.toggle("minimized");
          var sectionContent = this.nextElementSibling;
          console.log(sectionContent);
          if (section.classList.contains("minimized")) {
              sectionContent.style.maxHeight = "0";
          } else {
              sectionContent.style.maxHeight = sectionContent.scrollHeight + "px";
          }
      }
  }