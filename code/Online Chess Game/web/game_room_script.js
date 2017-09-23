/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var cookie = $.cookie("userId");

$(function(){
    $('#timeSetting').hide();
    $('#newChessBoardError').hide();
    $('#joinBoardError').hide();
    $.getJSON("GameRoomServlet", {cookie: cookie}, function(data){
        var playerTableData = "<tr id='tableHeader'><th>Player ID</th><th>Rating</th></tr>";
        $.each(data.player, function(key, value){
                 console.log(value);
                 playerTableData = playerTableData + "<tr class='tableList'><td>"+value.userId+"</td><td>"+value.rating+"</td></tr>";
        });
        $("#playerData").html(playerTableData);

        var chatWindowData = "<span class='signInMessage'> Welcome to Online Chess Game!</span><br>";
        $("#textArea").html(chatWindowData);

        var list = $("#roomList table").html();
        $.each(data.chessBoard, function(key, value){
            list = list + "<tr class='tableList'><td>" + value.tableNumber + "</td><td>"+ value.boardHost +"</td><td>" + value.playerId + "</td><td>" + value.maximumGameTime + " min, " + value.timePerMove + " min/move</td></tr>";
        });
    });
    
    var chatWindowData;
    var inputTextData;
    var sendMessage;
    $("#send").click(function(){
        chatWindowData = $("#textArea").html();
        inputTextData = $("#input").val();
        if(inputTextData !== ''){
            chatWindowData = chatWindowData + "<br>" + "<span class='sender'>" + cookie + ": </span>" + inputTextData;
            $("#textArea").html(chatWindowData);

            $("#textArea").scrollTop($("#textArea").prop("scrollHeight"));
            sendMessage = "<span class='receiver'>" + cookie + ": </span>" + inputTextData;
            $("#input").val("");

            $.post("SendMessage", {message : sendMessage, userId : cookie});
        }
    });
    
    $('#input').keypress(function(event){
        if (event.keyCode === 10 || event.keyCode === 13){
            $('#send').click();
        } 
    });
    
    $('#setTime').click(function(){
        $.getJSON("RequestCreateBoard", {cookie: cookie, maximumGameTime : validGameTime($('#maximumGameTime').val()), timePerMove : validGameTime($('#timePerMove').val())}, function(data){
            if(data.mayHost === true){
                $.cookie("tableNumber", data.tableNumber);
                $.cookie("playerId", data.playerId);
                $('#timeSetting').hide();
                window.open("chess_board_host.html","_blank"); 
            }else{
                $('#timeSetting').hide();
                $('#newChessBoardError').show();
            }
        });
    });
    
    $('#roomList').on("click", "button", function(){
        console.log("joinGame");
        var tableNumber = this.id;
        
        $.getJSON("JoinBoard",{userId : cookie, tableNumber : tableNumber}, function(data){
            if(data.playerAdded === true){
                window.open("chess_board_player.html","_blank"); 
            }else{
                $('#joinBoardError').show();
            }
        });
    });
    
    $('#cancelSetTime').click(function(){
        $('#timeSetting').hide();
    });
    
    $('#errorOk').click(function(){
        $('#newChessBoardError').hide();
    });
    
    $('#createBoard').click(function(){
        $('#timeSetting').show();
    });
    
    $('#joinBoardError button').click(function(){
        $('#joinBoardError').hide();
    });
    
    initializeButtons();
});   

(function(){
    poll = function(){
        console.log('polling');
        
        $.getJSON("GameRoomServlet", {cookie: cookie}, function(data){
            var playerTableData = "<tr id='tableHeader'><th>Player ID</th><th>Rating</th></tr>";
            $.each(data.player, function(key, value){
                     console.log(value);
                     playerTableData = playerTableData + "<tr class='tableList'><td>"+value.userId+"</td><td>"+value.rating+"</td></tr>";
            });
            $("#playerData").html(playerTableData);
            $("#playerList").scrollTop($("#playerList").prop("scrollHeight"));
            
            $.each(data.messages, function(id, value){
                console.log(value);
                if (value !== "") {
                    if(value.indexOf("uniquestring123abc") !== -1){
                        $('#joinBoardRequest').html('');
                        $('#joinBoardRequest').html(value);
                        if($('#joinBoardRequest').html() !== ''){
                            showRequestTab();
                        }
                    }else{
                        var chatWindowData = $("#textArea").html();
                        chatWindowData = chatWindowData + "<br>" + value;
                        $("#textArea").html(chatWindowData);
                        $("#textArea").scrollTop($("#textArea").prop("scrollHeight"));
                    }
                }
            });
            
            
            var list = "<tr id='tableHeader'><th>Table</th><th>White</th><th>Black</th><th>Game Setting</th></tr>";
            $.each(data.chessBoard, function(key, value){
                console.log(value);
                list = list + "<tr class='tableList'><td>" + value.tableNumber + "</td><td>"+ value.boardHost +"</td><td>" + chessBoardListPlayerId(value.playerId, value.tableNumber) + "</td><td>" + value.maximumGameTime + " min, " + value.timePerMove + " min/move</td></tr>";
            });
            $("#roomList table").html(list);
            $("#roomList").scrollTop($("#roomList").prop("scrollHeight"));
        });
    },
    pollInterval = setInterval(function(){
        poll();
    }, 3000);
})();

function chessBoardListPlayerId(playerId, tableNumber){
    var result= playerId;
    if(playerId === ""){
        result = "<button class='joinGame' id=" + tableNumber + ">Join</button>";
    }
    
    return result;
}

function validGameTime(time){
    if(time > 1440 || time < 1){
        time = 1440;     //validate time
    }
    return time;
}

function initializeButtons(){

    joinGameButton();
    joinBoardErrorButton();
    denyButton();
    joinBoardResponseOkButton();
    signOutButton();
}

function showRequestTab(){
    $('#requestTableTimePerMove').html('');
    $('#requestTableTimePerMove').html($('.invitation').attr('data-timePerMove'));
    $('#requestTableMaximumGameTime').html('');
    $('#requestTableMaximumGameTime').html($('.invitation').attr('data-maximumGameTime'));
    $('#requestTab').show();
}

function joinGameButton(){
    
    $('#acceptRequest').click(function(){
        var tableNumber = $('.invitation').attr('data-tableNumber');
        $.getJSON("JoinBoard",{userId : cookie, tableNumber : tableNumber}, function(data){
            if(data.playerAdded === true){
                window.open("chess_board_player.html","_blank"); 
                $('#requestTab').hide();
            }else{
                $('#joinBoardError').show();
            }
        });
    });
}

function joinBoardErrorButton(){
    $('#joinBoardError > button').click(function(){
        $('#joinBoardError').hide();
    });
}

function denyButton(){
    
    $('#denyRequest').click(function(){
        var requestResponse = "<span class='invitationResponse' data-uniqueString='uniquestringabc123' data-text='" + $('#denyReason').val() + "'></span>";
        $('#requestTab').hide();
        $.post('RequestResponse', {userId: cookie, tableNumber: $('.invitation').attr('data-tableNumber'), requestDenial: requestResponse, inviterId: $('.invitation').attr('data-inviterId')});
        $('#joinBoardRequest').html('');
    });
}

function joinBoardResponseOkButton(){
    $('#showText > button').click(function(){
       $('#showText').hide();
    });
}

function signOutButton(){
    $('#signOut').click(function(){
        $.post('SignOut', {userId: cookie}, function(data){
            if(data.signedOutSuccessful === true){
                window.location.href = "index.html";
            }
        });
    });
}