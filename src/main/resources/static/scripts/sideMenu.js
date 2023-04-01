function d(nested){
  if(nested == null)
    return;
  else{
    let children = nested.children;
    var j;
    for (j = 0; j < children.length; j++){
      d(children[j]);
      if(children[j].getAttribute("class") === "active"){
        children[j].setAttribute("open", "false");
      }
    }
    if(nested.getAttribute("class") === "nested"){
      nested.style.display = "none";
    }
  }
}

/* JavaScript code for the nested tree structure */
var toggler = document.getElementsByClassName("active");
var i;
for (i = 0; i < toggler.length; i++) {
  toggler[i].setAttribute("open", "false");
  toggler[i].addEventListener("click", function(){
    var nested = this.nextElementSibling;
    if(this.getAttribute("open") === "false"){
      this.setAttribute("open", "true");
      nested.style.display = "block";
    }else{
      this.setAttribute("open", "false");
      d(nested);
    }
  });
}