function searchByName(param) {
    var searchInput = param.value;
    $.ajax({
        url: "/g3s/admin_account/search.do",
        type: "get",
        data: {
            searchInput: searchInput
        },
        success: function (data) {
            var row = document.getElementById("content_tableAccount");
            row.innerHTML = data;
        }
    });
}

