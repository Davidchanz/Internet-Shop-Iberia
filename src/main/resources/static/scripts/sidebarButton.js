/* JavaScript code for the slide side bar */
      var sidebar = document.getElementById("sidebar");
      var slideButton = document.getElementById("slide-button");
      sidebar.style.left = "-250px"
      slideButton.onclick = function() {
        var shadow = document.getElementById("shadow");
        shadow.style.opacity = 1;
        shadow.style.pointerEvents = "all";
        sidebar.style.left = "0";
        shadow.onclick = function(){
          shadow.style.opacity = 0;
          shadow.style.pointerEvents = "none";
          sidebar.style.left = "-250px";
        }
      }