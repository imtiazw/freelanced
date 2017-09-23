/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var cookie = $.cookie("userId");
var tableNumber = $.cookie("tableNumber");

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
    $.getJSON("GetPossibleMoves", {cookie: cookie, tableNumber: tableNumber, piece: 'ALL', team: 'pink'}, function(data){
        $.each(data,function(piece, positions){
            $.each(positions, function(key, position){
                saveNextPossiblePositions(piece,position);
            });
        });
    });
    $('.brown').attr('draggable', 'false');
    
});


function allowDrop(ev){
    ev.preventDefault();
}

function drag(ev){
    ev.dataTransfer.setData("Text", ev.target.id);
}

function drop(ev){
    var data = ev.dataTransfer.getData("Text");
    console.log(data);
    ev.target.appendChild(document.getElementById(data));
    ev.preventDefault();
}

