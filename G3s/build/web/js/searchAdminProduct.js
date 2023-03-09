function searchByName(param) {
    var searchInput = param.value;
    $.ajax({
        url: "/g3s/admin_product/search.do",
        type: "get",
        data: {
            searchInput: searchInput
        },
        success: function (data) {
            var row = document.getElementById("content_tableProduct");
            row.innerHTML = data;
        }
    });
}
