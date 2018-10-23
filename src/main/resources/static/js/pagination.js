$(document).ready(function () {
    changePageAndSize();
    changeLibraryPageSize();
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function (e) {
        window.location.replace("/home?pageSize=" + this.value + "&page=1");
    });
}

function changeLibraryPageSize() {
    $('#libraryPageSizeSelect').change(function (e) {
        window.location.replace("/library?pageSize=" + this.value + "&page=1");
    })
}