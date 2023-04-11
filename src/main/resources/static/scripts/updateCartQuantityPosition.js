var cartQuantity = document.querySelector('#cart-quantity');
var quantity = parseInt(cartQuantity.textContent, 10);
if(quantity >= 10){
    cartQuantity.style.transform = "translate(-36.5px, -6px)";
}else{
    cartQuantity.style.transform = "translate(-28.5px, -6px)";
}