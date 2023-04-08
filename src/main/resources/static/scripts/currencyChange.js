$(document).ready(function() {
              $("#curr").change(function () {
                  var selectedOption = $('#curr').val();
                  if (selectedOption != ''){
                     if(window.location.search != ""){
                        if(window.location.search.includes("lang=")){
                          if(window.location.search.includes("-")){
                            var params = window.location.search.substring(0, window.location.search.indexOf("-"));
                            window.location.replace(params + '-' + selectedOption);
                          }else
                            window.location.replace(window.location.search + '-' + selectedOption);
                        }else{
                          var userLang = navigator.language || navigator.userLanguage;
                          userLang = userLang.substring(0, 2);
                          window.location.replace(window.location.pathname + window.location.search + '&lang=' + userLang + '-' + selectedOption);
                        }
                    }
                    else{
                      var userLocal = navigator.language || navigator.userLanguage;
                      var userLang = userLocal.substring(0, 2);
                      window.location.replace(window.location.pathname + '?lang=' + userLang + '-' + selectedOption);
                    }
                  }
              });
          });