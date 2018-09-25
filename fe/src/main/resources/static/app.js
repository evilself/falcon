var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#result").show();
    }
    else {
        $("#result").hide();
    }
    $("#scorers").html("");
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/scorers', function (scorer) {
            var scorer = JSON.parse(scorer.body)
            updateScorer(scorer.team, scorer.scorer, scorer.minute);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendScorer() {
    stompClient.send("/app/scorer", {}, JSON.stringify({'team': $("#team").val(), 'scorer':$("#scorer").val(), 'minute': $("#minute").val()}));
}

function sendRestScorer() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/scores",
        data: JSON.stringify({'team': $("#team").val(), 'scorer':$("#scorer").val(), 'minute': $("#minute").val()}),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
            xhr.setRequestHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE")
        },
        success: function( data ) {
            $("#dbEntries").append("<tr><td>" + data.minute + " - " + data.scorer + "</td></tr>");
        },
        contentType: "application/json",
        dataType: "json"
    });
}

function getRestScores() {
    $("#dbEntries").html("");
    $.get( "http://localhost:8081/scores", function( data ) {
        $.each(data, function(idx, score) {
            $("#dbEntries").append("<tr><td>" + score.minute + " - " + score.scorer + "</td></tr>");
        });
    });
}

function updateScorer(team, scorer, minute) {
    if(team && team == 'ARSENAL') {
        $("#scorers").append("<tr><td class='pull-right'>" + scorer + " - " + minute + "\'</td><td></td></tr>");
    } else {
        $("#scorers").append("<tr><td></td><td class='pull-left'>" + minute + "\' - " + scorer + "</td></tr>");
    }
}

$(function () {
    connect();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendScorer(); });
    $( "#sendRest" ).click(function() { sendRestScorer(); });
    $( "#getScores" ).click(function() { getRestScores(); });
});