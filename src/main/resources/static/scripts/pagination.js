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


        var search = "";
        var params = window.location.search;
        if(params.includes("&page=")){
            params.split("&").forEach(element => {
                if(!element.includes("page=")){
                    search = search + element + "&";
                }
            });;
            params = search.substring(0, search.length-1);
            window.location = window.location.pathname + params + "&page=" + number;
        }else{
            window.location = window.location + "&page=" + number;
        }
   }
}