
function go_calendar(){
    location.href = "/user/calendar";
}
function go_record(){
    location.href = "/user/exinfo";
}

function goJoin(){
    location.href = "/admin/register";
}
function goVideo(){
    location.href = "/admin/video";
}
function goMember(){
    location.href = "/admin/member";
}
window.onload = function() {
    function show1 () {
        document.querySelector(".black_bg").className = "black_bg show1";
    }
    function close1 () {
        document.querySelector(".black_bg").className = "black_bg";
    }

    document.querySelector("#modal_btn").addEventListener("click", show1);
    document.querySelector(".black_bg").addEventListener("click", close1);
}