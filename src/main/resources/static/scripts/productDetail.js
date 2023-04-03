function updateMainImage(value) {
	$.get("update/image?image="+value).done(function(fragment) { // get from controller
		$("#mainImage").replaceWith(fragment); // update snippet of page
	});
}

var smallImages = document.getElementsByClassName("small-image");
for(var i = 0; i < smallImages.length; i++){
    smallImages[i].onclick = function(){
        updateMainImage(this.src);
    }
}