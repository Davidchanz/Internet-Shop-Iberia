$(document).ready(function() {
  $("#locales").change(function () {
      var selectedOption = $('#locales').val();
      if (selectedOption != ''){
         if(window.location.search != ""){
            if(window.location.search.includes("lang=")){
              var params = window.location.search.substring(0, window.location.search.indexOf("lang")+5);
              var curr = window.location.search.substring(window.location.search.indexOf("lang")+7);
              window.location.replace(params + selectedOption + curr);
            }else{
              var userLocal = navigator.language || navigator.userLanguage;
              var userCurr = userLocal.substring(3);
              window.location.replace(window.location.pathname + window.location.search + '&lang=' + selectedOption + '-' + userCurr);
            }
        }
        else{
          var userLocal = navigator.language || navigator.userLanguage;
          var userCurr = userLocal.substring(3);
          window.location.replace(window.location.pathname + '?lang=' + selectedOption + '-' + userCurr);
        }
      }
  });
});