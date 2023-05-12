var pages = document.getElementsByClassName("page");
console.log(pages[0].textContent);

for(var i = 0; i < pages.length; i++){
    pages[i].onclick = function(){
        var active = document.getElementsByClassName("page active");
        active[0].classList.remove('active');
        this.classList.add('active');
        var number = this.textContent;
        document.getElementById("page").value = number;
        document.getElementById("page").checked = true;
        var params = window.location.toString();
        if(params.indexOf("&page=") != -1){
            var page = params.indexOf("&page=");
            window.location = window.location.toString().substring(0, page) + "&page=" + number;
        }else{
            window.location = window.location + "&page=" + number;
        }
   }
}