$('#add-collection').click(function(){
    const popUp = document.getElementById('popUp');
    popUp.style.display = 'flex';
    const form = document.getElementById('new-collection-name-form');
    form.style.display = 'block';
    const form1 = document.getElementById('are-you-sure');
    form1.style.display = 'none';
    const form2 = document.getElementById('change-collection-name');
    form2.style.display = 'none';
});

var dellButtons = document.getElementsByClassName("dell-btn");
for(var i = 0; i < dellButtons.length; i++){
    dellButtons[i].onclick = function(){
        const popUp = document.getElementById('popUp');
        popUp.style.display = 'flex';
        const form = document.getElementById('are-you-sure');
        form.style.display = 'block';
        const form1 = document.getElementById('new-collection-name-form');
        form1.style.display = 'none';
        const form2 = document.getElementById('change-collection-name');
        form2.style.display = 'none';

        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open("GET", window.origin + "/userCollection/dell", false);
        xmlHttp.send(null);
        var answer = xmlHttp.responseText.split(',');

        var message = document.getElementById('are-you-sure-message');
        message.innerHTML = answer[0];
        var text = document.getElementById('are-you-sure-text');

        var name = this.parentElement.firstElementChild.text;
        
        text.innerHTML = answer[1] + name + "'?";

        var variable = document.getElementById('variable');
        variable.innerText = name;
        console.log(variable);
    }
}

var editButtons = document.getElementsByClassName("edit-btn");
for(var i = 0; i < editButtons.length; i++){
    editButtons[i].onclick = function(){
        const popUp = document.getElementById('popUp');
        popUp.style.display = 'flex';
        const form = document.getElementById('change-collection-name');
        form.style.display = 'block';
        const form1 = document.getElementById('new-collection-name-form');
        form1.style.display = 'none';
        const form2 = document.getElementById('are-you-sure');
        form2.style.display = 'none';

        var name = this.parentElement.firstElementChild.text;

        const oldCollection = document.getElementById('newCollectionName');
        oldCollection.value = name;

        const newCollection = document.getElementById('collectionName');
        newCollection.value = name;
    }
}