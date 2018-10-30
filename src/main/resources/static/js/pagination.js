$(document).ready(function () {
    changePageAndSize();
    changeLibraryPageSize();
    changeAllGamesPageSize();
    changleAuctionsPageSize();
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

function changeAllGamesPageSize() {
    $('#gamesPageSizeSelect').change(function (e) {
        window.location.replace("/game/all?pageSize=" + this.value + "&page=1");
    })
}

function changleAuctionsPageSize() {
    $('#auctionPageSizeSelect').change(function (e) {
        window.location.replace("/auctions?pageSize=" + this.value + "&page=1");
    });
}