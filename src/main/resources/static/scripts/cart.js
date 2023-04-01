function updateProductQuantity(productId, quantity) {
    $.get("q?productId="+productId+"&quantity="+quantity).done(function(fragment) { // get from controller
        $("#products-list").replaceWith(fragment); // update snippet of page
    });
}

var quantityButtons = document.getElementsByClassName("custom-quantity-button");
for(var i = 0; i < quantityButtons.length; i++){
  quantityButtons[i].onclick = function(){
    var quantity = this.previousElementSibling.value;
    var productId = this.previousElementSibling.previousElementSibling.children[0].children[1].textContent;
    if(quantity < 10){
      if(quantity == ""){
        quantity = 0;
      }
      var comb = this.previousElementSibling.previousElementSibling.children[0];
      comb.style.display = "block";
      this.style.display = "none";
      this.previousElementSibling.style.display = "none";
    }
    updateProductQuantity(productId, quantity);
  }
}

var combobox = document.getElementsByClassName("combo-box");
for(var i = 0; i < combobox.length; i++){
  var select = combobox[i].nextElementSibling;
  combobox[i].onclick = function(){
    var another_select = document.getElementsByClassName("select");
    for(var j = 0; j < another_select.length; j++){
      another_select[j].style.display = "none";
    }
    var select = this.nextElementSibling;
    select.style.top = this.offsetTop+"px";
    select.style.display = "block";
  }

  var lis = select.getElementsByTagName("li");
  for(var k = 0; k < lis.length; k++){
    lis[k].onclick = function(){
      if(this.value == "10"){
        var select = this.parentNode;
        select.style.display = "none";
        var comb = select.previousSibling.previousSibling;
        comb.style.display = "none";
        var input = select.parentNode.nextElementSibling;
        var button = input.nextElementSibling;
        input.style.display = "block";
        button.style.display = "block";
      }else{
        var select = this.parentNode;
        select.style.display = "none";
        updateProductQuantity(select.previousElementSibling.children[1].textContent, this.value);
      }
    }
  }
}