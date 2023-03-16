var searchInputAdmin = document.getElementById("searchInputAdmin");


searchInputAdmin.addEventListener("input", function () {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var table = document.getElementById("content_tableProduct");
            table.innerHTML = xhr.responseText;
        }
    };
    xhr.open("GET", "/g3s/admin_product/searchajax.do?keyword=" + searchInputAdmin.value);
    xhr.send();
});

