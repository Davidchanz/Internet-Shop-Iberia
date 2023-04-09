// Handle errors
function handleError() {
  const errorSection = document.getElementById('error');
  errorSection.style.display = 'flex';
}
handleError();

// Handle button click
const btn = document.querySelector('#errorBtn');
btn.addEventListener('click', function() {
  window.location.href = '/';
});
