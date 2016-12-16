var selectedMode = 0;



$(window).load( function() {

    $(".ninja-btn, .panel-overlay").click( function() {
        $(".ninja-btn, .panel-overlay, .panel").toggleClass("active");
        /* Check panel overlay */
        if ($(".panel-overlay").hasClass("active")) {
            $(".panel-overlay").fadeIn();
        } else {
            $(".panel-overlay").fadeOut();
            var urlContent = document.getElementById("serverUrl").value;
            document.getElementById("serverUrl").setAttribute("value", urlContent);
            var portContent = document.getElementById("serverPort").value;
            document.getElementById("serverPort").setAttribute("value", portContent);
        }
    });

    $("#modeManual").click( function() {
        $("#modeManual").addClass("active");
        $("#modeConversation").removeClass("active");
        $("#modeRepeater").removeClass("active");
    });
    $("#modeConversation").click( function() {
        $("#modeManual").removeClass("active");
        $("#modeConversation").addClass("active");
        $("#modeRepeater").removeClass("active");
    });
    $("#modeRepeater").click( function() {
        $("#modeManual").removeClass("active");
        $("#modeConversation").removeClass("active");
        $("#modeRepeater").addClass("active");
    });
});


$(window).on("load resize", function() {
    var menuHeightOffset = $(".panel").find("ul").height() /2;

    $(".panel").find("ul").css({
        "margin-top": -menuHeightOffset
    });
});


function toggleStartButton(value) {
    if(value) {
        $("#startButton").addClass("green").removeClass("red").text('START');
    }
    else {
        $("#startButton").addClass("red").removeClass("green").text('STOP');
    }
}