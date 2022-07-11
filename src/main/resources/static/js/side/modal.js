
function go_calendar(){
    location.href = "/user/infoCalender";
}
function go_record(){
    location.href = "/user/goRecord";
}

function goJoin(){
    location.href = "/admin/Join";
}
function goVideo(){
    location.href = "/admin/video";
}
function goMember(){
    location.href = "/admin/Member";
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