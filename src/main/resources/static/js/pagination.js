$(document).ready(function () {
    changePageAndSize();
});

function changePageAndSize() {
    $('#pageSizeSelect').change(function (e) {
        window.location.replace("/home?pageSize=" + this.value + "&page=1");
    });
}