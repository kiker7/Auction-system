$(function () {
    getNotifications();
    setInterval(getNotifications, 10000);

    function getNotifications() {
        $.get("/home/user-notifications", function (data) {
            $("#notifications-indicator").text(data);
        });
    }
});

