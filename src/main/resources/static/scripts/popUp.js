const cancel = document.querySelector('#cancelBtn');
cancel.addEventListener('click', function() {
      const popUp = document.getElementById('popUp');
      popUp.style.display = 'none';
});

const yes = document.querySelector('#yesButton');
yes.addEventListener('click', function() {
      const popUp = document.getElementById('popUp');
      popUp.style.display = 'none';
      var variable = document.getElementById('variable');
      window.location = '/coll/d?name=' + variable.innerText;
});