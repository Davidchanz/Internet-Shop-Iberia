function updateSearchResults(value) {
	$.get("s?searchInput="+value).done(function(fragment) { // get from controller
		$("#dropdown").replaceWith(fragment); // update snippet of page
	});
}
// Show/hide dropdown on input focus/blur
var searchInput = document.querySelector('.search-bar input[type="text"]');
var dropdown = document.querySelector('.search-bar .dropdown');
searchInput.addEventListener('focus', function() {
	updateSearchResults(searchInput.value);
});
searchInput.addEventListener('blur', function() {
	setTimeout(function() {
		dropdown.style.display = 'none';
	}, 200);
});
searchInput.addEventListener("input", function(e) {
	updateSearchResults(e.target.value);
});
// Select dropdown item on click
var dropdownLinks = document.querySelectorAll('.search-bar .dropdown a');
for (var i = 0; i < dropdownLinks.length; i++) {
	dropdownLinks[i].addEventListener('click', function(e) {
		e.preventDefault();
		searchInput.value = this.textContent;
		dropdown.style.display = 'none';
		updateSearchResults(this.textContent);
	});
}