/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var cookie = $.cookie("userId");
var tableNumber = $.cookie("tableNumber");
var person = getPerson();
var team = getTeam(person);
//start of data will used during game play
var Pieces = {
    pink : [
        {
            name : "PP1",
            nextPossiblePositions : []
        },
        {    
            name : "PP2",
            nextPossiblePositions : []
        },
        {
            name : "PP3",
            nextPossiblePositions : []
        },
        {
            name : "PP4",
            nextPossiblePositions : []
        },
        {
            name : "PP5",
            nextPossiblePositions : []
        },
        {
            name : "PP6",
            nextPossiblePositions : []
        },
        {
            name : "PP7",
            nextPossiblePositions : []
        },
        {
            name : "PP8",
            nextPossiblePositions : []
        },
        {
            name : "PRR",
            nextPossiblePositions : []
        },
        {
            name : "PLR",
            nextPossiblePositions : []
        },
        {
            name : "PRN",
            nextPossiblePositions : []
        },
        {
            name : "PLN",
            nextPossiblePositions : []
        },
        {
            name : "PRB",
            nextPossiblePositions : []
        },
        {
            name : "PLB",
            nextPossiblePositions : []
        },
        {
            name : "PQ",
            nextPossiblePositions : []
        },
        {
            name : "PK",
            nextPossiblePositions : []
        }
    ],
    brown : [
        {
            name : "BP1",
            nextPossiblePositions : []
        },
        {    
            name : "BP2",
            nextPossiblePositions : []
        },
        {
            name : "BP3",
            nextPossiblePositions : []
        },
        {
            name : "BP4",
            nextPossiblePositions : []
        },
        {
            name : "BP5",
            nextPossiblePositions : []
        },
        {
            name : "BP6",
            nextPossiblePositions : []
        },
        {
            name : "BP7",
            nextPossiblePositions : []
        },
        {
            name : "BP8",
            nextPossiblePositions : []
        },
        {
            name : "BRR",
            nextPossiblePositions : []
        },
        {
            name : "BLR",
            nextPossiblePositions : []
        },
        {
            name : "BRN",
            nextPossiblePositions : []
        },
        {
            name : "BLN",
            nextPossiblePositions : []
        },
        {
            name : "BRB",
            nextPossiblePositions : []
        },
        {
            name : "BLB",
            nextPossiblePositions : []
        },
        {
            name : "BQ",
            nextPossiblePositions : []
        },
        {
            name : "BK",
            nextPossiblePositions : []
        }
    ]
};

//end of data will be used during game play

$(function(){
    console.log('working');
    $.getJSON("GetPossibleMoves", {cookie: cookie, tableNumber: tableNumber, piece: 'ALL', team: 'brown'}, function(data){
        $.each(data,function(piece, positions){
            $.each(positions, function(key, position){
                saveNextPossiblePositions(piece,position);
            });
        });
    });
    $('.pink').attr('draggable', 'false');
    
});


function allowDrop(ev){
    ev.preventDefault();
}

function drag(ev){
    ev.dataTransfer.setData("Text", ev.target.id);
    previousPosition = ev.target.parentNode.id;
}

function drop(ev){
    var data = ev.dataTransfer.getData("Text");
    console.log(data);
    ev.target.appendChild(document.getElementById(data));
    ev.preventDefault();
}

function getPerson(){
    var person = null;
    var temp = function () {
                var a = window.location.href,
                    b = a.lastIndexOf("/");
                return a.substr(b + 1);
            }();
            
    if(temp === 'chess_board_host.html'){
        person = 'host';
    }else if(temp === 'chess_board_player.html'){
        person = 'player';
    }
    
    return person;
}

function getTeam(person){
    var team = null;
    if(person === 'host'){
        team = 'brown';
    }else if(person === 'player'){
        team = 'pink';
    }
    
    return team;
}