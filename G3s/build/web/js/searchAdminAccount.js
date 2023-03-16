var searchInputAccount = document.getElementById("searchInputAccount");


searchInputAccount.addEventListener("input", function () {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var table = document.getElementById("content_tableAccount");
            table.innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/g3s/admin_account/searchajax.do?keyword=" + searchInputAccount.value);
    xhr.send();
});