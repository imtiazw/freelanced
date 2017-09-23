/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var cookie = $.cookie("userId");
var tableNumber = $.cookie("tableNumber");
var nativePerson = getPerson();
var team = getTeam(nativePerson);
var currentPlayerControl = '';
var gameStarted = false;
var maximumGameTime = 0;
var maximumTimePerMove = 0;
var processingJoinBoardRequest = false;

//start of data will used during game play
var Pieces = {
    pink: [
        {
            name: "PP1",
            nextPossiblePositions: []
        },
        {
            name: "PP2",
            nextPossiblePositions: []
        },
        {
            name: "PP3",
            nextPossiblePositions: []
        },
        {
            name: "PP4",
            nextPossiblePositions: []
        },
        {
            name: "PP5",
            nextPossiblePositions: []
        },
        {
            name: "PP6",
            nextPossiblePositions: []
        },
        {
            name: "PP7",
            nextPossiblePositions: []
        },
        {
            name: "PP8",
            nextPossiblePositions: []
        },
        {
            name: "PRR",
            nextPossiblePositions: []
        },
        {
            name: "PLR",
            nextPossiblePositions: []
        },
        {
            name: "PRN",
            nextPossiblePositions: []
        },
        {
            name: "PLN",
            nextPossiblePositions: []
        },
        {
            name: "PRB",
            nextPossiblePositions: []
        },
        {
            name: "PLB",
            nextPossiblePositions: []
        },
        {
            name: "PQ",
            nextPossiblePositions: []
        },
        {
            name: "PK",
            nextPossiblePositions: []
        }
    ],
    brown: [
        {
            name: "BP1",
            nextPossiblePositions: []
        },
        {
            name: "BP2",
            nextPossiblePositions: []
        },
        {
            name: "BP3",
            nextPossiblePositions: []
        },
        {
            name: "BP4",
            nextPossiblePositions: []
        },
        {
            name: "BP5",
            nextPossiblePositions: []
        },
        {
            name: "BP6",
            nextPossiblePositions: []
        },
        {
            name: "BP7",
            nextPossiblePositions: []
        },
        {
            name: "BP8",
            nextPossiblePositions: []
        },
        {
            name: "BRR",
            nextPossiblePositions: []
        },
        {
            name: "BLR",
            nextPossiblePositions: []
        },
        {
            name: "BRN",
            nextPossiblePositions: []
        },
        {
            name: "BLN",
            nextPossiblePositions: []
        },
        {
            name: "BRB",
            nextPossiblePositions: []
        },
        {
            name: "BLB",
            nextPossiblePositions: []
        },
        {
            name: "BQ",
            nextPossiblePositions: []
        },
        {
            name: "BK",
            nextPossiblePositions: []
        }
    ]
};

var toKingLocations = [
    /*object would be like the following
     {
     name = "";
     locations = []
     }
     */
    {
        name: 'increasingColumn',
        locations: []
    },
    {
        name: 'increasingRow',
        locations: []
    },
    {
        name: 'decreasingColumn',
        locations: []
    },
    {
        name: 'decreasingRow',
        locations: []
    },
    {
        name: 'increasingRowIncreasingColumn',
        locations: []
    },
    {
        name: 'increasingRowDecreasingColumn',
        locations: []
    },
    {
        name: 'decreasingRowDecreasingColumn',
        locations: []
    },
    {
        name: 'decreasingRowIncreasingColumn',
        locations: []
    }
];

var restrictedPiece = [
    /*object type would be like to following
     {
     restrictedPiece: "",
     restrictedBecauseOf: "",
     restrictedFor: "",
     allowedPositions: []
     }
     */
];

var lastMove = null;
//end of data will be used during game play
$(function() {
    $('#startGame').click(function() {
        $('#checkMate').hide();
        initializeClientSideChessPlayBoard();
        initializePieces();
        resetToKingLocations();
        initializeRestrictedPiece();
        initializeLastMove();
        initializeChessPlayBoard();
        displayVaribles();
        disablePieces();
        getAndSetGameStarted();
        $.getJSON('StartGame', {cookie: cookie, tableNumber: tableNumber}, function(data) {
            if (data.gameStarted === true) {
                //startTimer();
                setCurrentPlayerControl(data.currentPlayerControl);
                if (isMyControl() === true) {
                    enableNativeControl();
                }
            } else {
                $('#playBoardMessage').html("Waiting for other Players to Start the Game");
                $('#playBoardMessage').show();
            }
            $('#startGame').attr('disabled', true);
        });
        
    });
    initializeChessPlayBoard();
    //displayVaribles();
    initializeCompetitorList();
    initializeTime();
    initializePlayerListAndChatWindow();
    sendMessage();
    disablePieces();
    initializeButtons();
    if(isHost() === true){
        loadPlayersInInvitationList(cookie);
    }
    //enableNativeControl();
    //enableOpponentControl();    //this is a testing function and should be deleted after testing
});

//this function is for testing purpose
function testingFunction() {
    
    var str = '{"simpleMove":{"pieceMoved":{"name":""},"previousPosition":{"row":"","column":""},"currentPosition":{"row":"","column":""},"nativeNextPossiblePositions":{},"opponentNextPossiblePositions":{},"nativeReturnSet":false,"opponentReturnSet":false},"pieceBeaten":{"pieceBeaten":{"name":""},"beatenBy":{"name":""},"beatingPiecePreviousPosition":{"row":"","column":""},"beatingPieceCurrentPosition":{"row":"","column":""},"beatenPiecePosition":{"row":"","column":""},"nativeNextPossiblePositions":{},"opponentNextPossiblePositions":{},"nativeReturnSet":false,"opponentReturnSet":false},"specialMove":{"eNPassantPlayed":false,"cAstle":false,"pAwnUpgrade":false,"eNPassantGiven":true,"pawnUpgrade":{"pawnName":{"name":""},"upgradedPiece":{"name":""},"pawnPreviousPosition":{"row":"","column":""},"pawnCurrentPosition":{"row":"","column":""},"upgradedPiecePosition":{"row":"","column":""}},"castle":{"kingName":{"name":""},"rockName":{"name":""},"kingPreviousPosition":{"row":"","column":""},"kingCurrentPosition":{"row":"","column":""},"rockPreviousPosition":{"row":"","column":""},"rockCurrentPosition":{"row":"","column":""},"rightCastle":false,"leftCastle":false},"enPassantPlayed":{"beatenPawnName":{"name":""},"beatingPawnName":{"name":""},"beatenPawnPosition":{"row":"","column":""},"beatingPawnPreviousPosition":{"row":"","column":""},"beatingPawnCurrentPosition":{"row":"","column":""}},"enPassantGiven":{"nativePlayer":{"userId":"five1"},"opponentPlayer":{"userId":"five12"},"nativeEnPassantPiece":{"name":"BP7"},"nativePiecePreviousPosition":{"row":"2","column":"g"},"nativePieceCurrentPosition":{"row":"4","column":"g"},"rightOpponentEnPassantPiece":true,"leftOpponentEnPassantPiece":false,"opponentRightEnPassantPiece":{"name":"PP1"},"opponentLeftEnPassantPiece":{"name":""},"opponentRightPiecePreviousPosition":{"row":"4","column":"h"},"opponentRightPieceNextPossiblePosition":{"row":"3","column":"g"},"opponentLeftPiecePreviousPosition":{"row":"","column":""},"opponentLeftPieceNextPossiblePosition":{"row":"","column":""},"enPassantExpired":false},"nativeNextPossiblePositions":{"BP6":[{"row":"4","column":"f"},{"row":"3","column":"f"}],"BP7":[{"row":"5","column":"g"}],"BP8":[{"row":"3","column":"h"}],"BLN":[{"row":"3","column":"c"},{"row":"3","column":"a"}],"BP1":[{"row":"4","column":"a"},{"row":"3","column":"a"}],"BP2":[{"row":"4","column":"b"},{"row":"3","column":"b"}],"BP3":[{"row":"4","column":"c"},{"row":"3","column":"c"}],"BP4":[{"row":"4","column":"d"},{"row":"3","column":"d"}],"BP5":[{"row":"4","column":"e"},{"row":"3","column":"e"}],"BLR":[],"BRN":[{"row":"3","column":"h"},{"row":"3","column":"f"}],"BRR":[],"BLB":[],"BQ":[],"BK":[],"BRB":[{"row":"2","column":"g"},{"row":"3","column":"h"}]},"opponentNextPossiblePositions":{"PLB":[],"PP8":[{"row":"5","column":"a"},{"row":"6","column":"a"}],"PRR":[],"PP5":[{"row":"5","column":"d"},{"row":"6","column":"d"}],"PP4":[{"row":"5","column":"e"},{"row":"6","column":"e"}],"PP7":[{"row":"5","column":"b"},{"row":"6","column":"b"}],"PP6":[{"row":"5","column":"c"},{"row":"6","column":"c"}],"PP1":[{"row":"3","column":"h"},{"row":"3","column":"g"}],"PP3":[{"row":"5","column":"f"},{"row":"6","column":"f"}],"PP2":[{"row":"5","column":"g"},{"row":"6","column":"g"}],"PLR":[],"PQ":[],"PLN":[{"row":"6","column":"h"},{"row":"6","column":"f"}],"PK":[],"PRB":[],"PRN":[{"row":"6","column":"c"},{"row":"6","column":"a"}]},"nativeReturnSet":true,"opponentReturnSet":true},"check":{"kingUnderCheck":{"name":""},"checkGivenBy":[null,null],"kingPosition":{"row":"","column":""},"checkGivenPosition":[null,null],"opponentKingNextPossiblePosition":{},"allowedPiecesOfOpponentWithAllowedPositions":{}},"checkMate":{"checkMateBy":{"userId":""},"checkMateTo":{"userId":""}},"sImpleMove":false,"pIeceBeaten":false,"sPecialMove":true,"cHeck":false,"cHeckMate":false,"playedBy":{"userId":"five1"},"toBeRecievedBy":{"userId":"five12"},"played":true,"recieved":false,"moveSkipped":false,"hostBlockedPieces":false,"clientBlockedPieces":false}';   //str = str.substring(1, str.length-1);
    lastMove = JSON.parse(str);
}

//this function is for testing purpose
function enableOpponentControl() {
    if (team === 'pink') {
        $('.brown').attr('draggable', 'true');
    } else if (team === 'brown') {
        $('.pink').attr('draggable', 'true');
    }
}






function displayVaribles() {
    var accessPieces;
    var locations = ['a1', 'a2', 'a3', 'a4','a5', 'a6', 'a7', 'a8',
                    'b1', 'b2', 'b3', 'b4','b5', 'b6', 'b7', 'b8',
                    'c1', 'c2', 'c3', 'c4','c5', 'c6', 'c7', 'c8',
                    'd1', 'd2', 'd3', 'd4','d5', 'd6', 'd7', 'd8',
                    'e1', 'e2', 'e3', 'e4','e5', 'e6', 'e7', 'e8',
                    'f1', 'f2', 'f3', 'f4','f5', 'f6', 'f7', 'f8',
                    'g1', 'g2', 'g3', 'g4','g5', 'g6', 'g7', 'g8',
                    'h1', 'h2', 'h3', 'h4','h5', 'h6', 'h7', 'h8'];
    
    //this is for next possible moves highlighted boxes
    $('#chessBoard')
            .on('mouseenter', 'img', function() {
                if (team === 'pink') {
                    accessPieces = Pieces.pink;
                } else if (team === 'brown') {
                    accessPieces = Pieces.brown;
                }
                if(isMyControl() === true){
                    var pieceId = this.id;
                    $.each(accessPieces, function(id, value) {
                        if (value.name === pieceId) {
                            $.each(value.nextPossiblePositions, function(id, value) {

                                $('#' + value).css({'background-color': 'green'});
                                
                            });
                        }
                    });
                }
            })
            .on('mouseleave', 'img', function(){
                if (team === 'pink') {
                    accessPieces = Pieces.pink;
                } else if (team === 'brown') {
                    accessPieces = Pieces.brown;
                }
                $.each(locations, function(id, value) {
                    $('#' + value).css({'background-color': ''});
                });
            });
            
   
}

function initializeCompetitorList(){
    
     //this is for competitor list window
    var selectedCompetitor = "";
    
    $('#competitorList').on('click', '.competitorId' , function(){
        
        if($(this).css('background-color') === 'rgb(0, 0, 255)'){       //checks if background-color is blue
            $('.competitorId').css({'background-color': ''});
            $('#selectedCompetitor').html('');
        }else{
            $('.competitorId').css({'background-color': ''});
            $(this).css({'background-color': 'blue'});
            $('#selectedCompetitor').html('');
            selectedCompetitor = "<span class='invitation' data-uniqueId='uniquestring123abc' data-inviterId='" + cookie + "' data-invitedId='" + $(this).html() + "' data-tableNumber='" + tableNumber + 
                                  "' data-text='' data-maximumGameTime='" + maximumGameTime + "' data-timePerMove='" + maximumTimePerMove + "'></span>";
            $('#selectedCompetitor').html(selectedCompetitor);
        }
    });
}

function initializePieces(){
    Pieces = {
        pink: [
            {
                name: "PP1",
                nextPossiblePositions: []
            },
            {
                name: "PP2",
                nextPossiblePositions: []
            },
            {
                name: "PP3",
                nextPossiblePositions: []
            },
            {
                name: "PP4",
                nextPossiblePositions: []
            },
            {
                name: "PP5",
                nextPossiblePositions: []
            },
            {
                name: "PP6",
                nextPossiblePositions: []
            },
            {
                name: "PP7",
                nextPossiblePositions: []
            },
            {
                name: "PP8",
                nextPossiblePositions: []
            },
            {
                name: "PRR",
                nextPossiblePositions: []
            },
            {
                name: "PLR",
                nextPossiblePositions: []
            },
            {
                name: "PRN",
                nextPossiblePositions: []
            },
            {
                name: "PLN",
                nextPossiblePositions: []
            },
            {
                name: "PRB",
                nextPossiblePositions: []
            },
            {
                name: "PLB",
                nextPossiblePositions: []
            },
            {
                name: "PQ",
                nextPossiblePositions: []
            },
            {
                name: "PK",
                nextPossiblePositions: []
            }
        ],
        brown: [
            {
                name: "BP1",
                nextPossiblePositions: []
            },
            {
                name: "BP2",
                nextPossiblePositions: []
            },
            {
                name: "BP3",
                nextPossiblePositions: []
            },
            {
                name: "BP4",
                nextPossiblePositions: []
            },
            {
                name: "BP5",
                nextPossiblePositions: []
            },
            {
                name: "BP6",
                nextPossiblePositions: []
            },
            {
                name: "BP7",
                nextPossiblePositions: []
            },
            {
                name: "BP8",
                nextPossiblePositions: []
            },
            {
                name: "BRR",
                nextPossiblePositions: []
            },
            {
                name: "BLR",
                nextPossiblePositions: []
            },
            {
                name: "BRN",
                nextPossiblePositions: []
            },
            {
                name: "BLN",
                nextPossiblePositions: []
            },
            {
                name: "BRB",
                nextPossiblePositions: []
            },
            {
                name: "BLB",
                nextPossiblePositions: []
            },
            {
                name: "BQ",
                nextPossiblePositions: []
            },
            {
                name: "BK",
                nextPossiblePositions: []
            }
        ]
    };
}
        
function resetToKingLocations(){
    toKingLocations = [
        /*object would be like the following
         {
         name = "";
         locations = []
         }
         */
        {
            name: 'increasingColumn',
            locations: []
        },
        {
            name: 'increasingRow',
            locations: []
        },
        {
            name: 'decreasingColumn',
            locations: []
        },
        {
            name: 'decreasingRow',
            locations: []
        },
        {
            name: 'increasingRowIncreasingColumn',
            locations: []
        },
        {
            name: 'increasingRowDecreasingColumn',
            locations: []
        },
        {
            name: 'decreasingRowDecreasingColumn',
            locations: []
        },
        {
            name: 'decreasingRowIncreasingColumn',
            locations: []
        }
    ];
}
        
function initializeRestrictedPiece(){
    restrictedPiece = [
        /*object type would be like to following
         {
         restrictedPiece: "",
         restrictedBecauseOf: "",
         restrictedFor: "",
         allowedPositions: []
         }
         */
    ];
}
        
function initializeLastMove(){
    lastMove = null;
}

function initializeChessPlayBoard() {
    $.getJSON('InitializeChessPlayBoard', {userId: cookie, tableNumber: tableNumber, team: team}, function(data) {
        setPieceArray(data);
    });
    initializeToKingLocations();
    disablePieces();
}

function initializeClientSideChessPlayBoard(){
    if(team === 'brown'){
        initializeClientSideHostChessPlayBoard();
    }else if(team === 'pink'){
        initializeClientSidePlayerChessPlayBoard();
    }
}

function initializeClientSidePlayerChessPlayBoard(){
    $('#chessBoard').html('');
    $('#chessBoard').html(
            '<table>'+
                '<tr><td><div class="boardNumber">1</div></td><td><div class="black" id="h1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown R.png" id="BRR" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="g1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown N.png" id="BRN" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="f1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown B.png" id="BRB" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="e1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown K.png" id="BK" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="d1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown Q.png" id="BQ" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="c1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown B.png" id="BLB" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="b1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown N.png" id="BLN" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="a1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown R.png" id="BLR" class="brown" draggable="true" ondragstart="drag(event)"/></div></td></tr>'+
                '<tr><td><div class="boardNumber">2</div></td><td><div class="white" id="h2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP8" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="g2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP7" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="f2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP6" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="e2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP5" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="d2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP4" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="c2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP3" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="b2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP2" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="a2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP1" class="brown" draggable="true" ondragstart="drag(event)"/></div></td></tr>'+
                '<tr><td><div class="boardNumber">3</div></td><td><div class="black" id="h3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="g3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="f3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="e3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="d3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="c3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="b3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="a3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td></tr>'+
                '<tr><td><div class="boardNumber">4</div></td><td><div class="white" id="h4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="g4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="f4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="e4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="d4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="c4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="b4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="a4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td></tr>'+
                '<tr><td><div class="boardNumber">5</div></td><td><div class="black" id="h5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="g5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="f5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="e5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="d5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="c5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="b5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="a5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td></tr>'+
                '<tr><td><div class="boardNumber">6</div></td><td><div class="white" id="h6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="g6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="f6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="e6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="d6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="c6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="b6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="a6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td></tr>'+
                '<tr><td><div class="boardNumber">7</div></td><td><div class="black" id="h7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP1" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="g7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP2" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="f7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP3" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="e7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP4" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="d7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP5" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="c7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP6" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="b7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP7" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="a7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP8" class="pink" draggable="true" ondragstart="drag(event)"/></div></td></tr>'+
                '<tr><td><div class="boardNumber">8</div></td><td><div class="white" id="h8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 R.png" id="PLR" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="g8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 N.png" id="PLN" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="f8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 B.png" id="PLB" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="e8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 K.png" id="PK" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="d8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 Q.png" id="PQ" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="c8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 B.png" id="PRB" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="b8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 N.png" id="PRN" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="a8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 R.png" id="PRR" class="pink" draggable="true" ondragstart="drag(event)"/></div></td></tr>'+
                '<tr><td><td><div class="boardAlphabet">h</div></td><td><div class="boardAlphabet">g</div></td><td><div class="boardAlphabet">f</div></td><td><div class="boardAlphabet">e</div></td><td><div class="boardAlphabet">d</div></td><td><div class="boardAlphabet">c</div></td><td><div class="boardAlphabet">b</div></td><td><div class="boardAlphabet">a</div></td></tr>'+
            '</table>'
            );
}

function initializeClientSideHostChessPlayBoard(){
    $('#chessBoard').html('');
    $('#chessBoard').html(
            '<table>'+
                '<tr><td><div class="boardNumber">8</div></td><td><div class="black" id="a8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 R.png" id="PRR" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="b8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 N.png" id="PRN" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="c8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 B.png" id="PRB" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="d8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 Q.png" id="PQ" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="e8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 K.png" id="PK" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="f8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 B.png" id="PLB" draggable="true" class="pink" ondragstart="drag(event)"/></div></td><td><div class="black" id="g8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 N.png" id="PLN" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="h8" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 R.png" id="PLR" class="pink" draggable="true" ondragstart="drag(event)"/></div></td></tr>'+
                '<tr><td><div class="boardNumber">7</div></td><td><div class="white" id="a7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP8" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="b7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP7" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="c7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP6" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="d7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP5" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="e7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP4" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="f7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP3" draggable="true" class="pink" ondragstart="drag(event)"/></div></td><td><div class="white" id="g7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP2" class="pink" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="h7" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Red2 P.png" id="PP1" class="pink" draggable="true" ondragstart="drag(event)"/></div></td></tr>'+
                '<tr><td><div class="boardNumber">6</div></td><td><div class="black" id="a6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="b6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="c6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="d6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="e6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="f6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="g6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="h6" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td></tr>'+
                '<tr><td><div class="boardNumber">5</div></td><td><div class="white" id="a5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="b5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="c5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="d5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="e5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="f5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="g5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="h5" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td></tr>'+
                '<tr><td><div class="boardNumber">4</div></td><td><div class="black" id="a4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="b4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="c4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="d4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="e4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="f4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="g4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="h4" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td></tr>'+
                '<tr><td><div class="boardNumber">3</div></td><td><div class="white" id="a3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="b3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="c3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="d3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="e3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="f3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="white" id="g3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td><td><div class="black" id="h3" ondrop="drop(event)" ondragover="allowDrop(event)"></div></td></tr>'+
                '<tr><td><div class="boardNumber">2</div></td><td><div class="black" id="a2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP1" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="b2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP2" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="c2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP3" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="d2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP4" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="e2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP5" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="f2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP6" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="g2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP7" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="h2" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown P.png" id="BP8" class="brown" draggable="true" ondragstart="drag(event)"/></div></td></tr>'+
                '<tr><td><div class="boardNumber">1</div></td><td><div class="white" id="a1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown R.png" id="BLR" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="b1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown N.png" id="BLN" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="c1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown B.png" id="BLB" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="d1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown Q.png" id="BQ" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="e1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown K.png" id="BK" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="f1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown B.png" id="BRB" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="white" id="g1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown N.png" id="BRN" class="brown" draggable="true" ondragstart="drag(event)"/></div></td><td><div class="black" id="h1" ondrop="drop(event)" ondragover="allowDrop(event)"><img src="images/Brown R.png" id="BRR" class="brown" draggable="true" ondragstart="drag(event)"/></div></td></tr>'+
                '<tr><td><td><div class="boardAlphabet">a</div></td><td><div class="boardAlphabet">b</div></td><td><div class="boardAlphabet">c</div></td><td><div class="boardAlphabet">d</div></td><td><div class="boardAlphabet">e</div></td><td><div class="boardAlphabet">f</div></td><td><div class="boardAlphabet">g</div></td><td><div class="boardAlphabet">h</div></td></tr>'+
            '</table>'
            );
}

function setPieceArray(data) {
    var piecesVariable = '';
    if (team === 'pink') {
        piecesVariable = Pieces.pink;
    } else if (team === 'brown') {
        piecesVariable = Pieces.brown;
    }
    var temp;
    $.each(piecesVariable, function(id, clientValue) {
        temp = clientValue.name;
        $.each(data, function(key, receievedValue) {
            if (temp === key) {
                $.each(receievedValue, function(id, value) {
                    clientValue.nextPossiblePositions.push(value);
                });
            }
        });
    });
}

function disablePieces() {
    $('.pink').attr('draggable', 'false');
    $('.brown').attr('draggable', 'false');
}

function initializeToKingLocations() {
    calculateLocationsInIncreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInIncreasingRowAndSetToRestrictedPieceData();
    calculateLocationsInDecreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInDecreasingRowAndSetToRestrictedPieceData();
    calculateLocationsInIncreasingRowIncreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInIncreasingRowDecreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInDecreasingRowDecreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInDecreasingRowIncreasingColumnAndSetToRestrictedPieceData();
}

function initializeButtons(){
    $('#sendRequest').click(function(){
        sendProposal();
    });
    
    $('#findCompetitor').click(function(){
        if(isHost() === true){
            $('#competitorList table').html('');
            $('#competitorList table').html('<tr><th>Player ID</th><th>Rating</th></tr>');
            loadPlayersInInvitationList(cookie);
        }
    });
    
    joinGameButton();
    joinBoardErrorButton();
    denyButton();
    joinBoardResponseOkButton();
    enableDisableChat();
    bootPlayer();
    exitBoard();
}

function restartTime(){
    $('#gameTime').TimeCircles().restart();
    $('#timePerMove').TimeCircles().restart();
}

function getAndSetGameStarted(){
    $.getJSON('GetStartGame', {cookie: cookie, tableNumber: tableNumber}, function(data) {
        if (data.gameStarted === false) {
            setGameStarted(false);
        }
    });
}








(function() {
    poll = function() {
        console.log('polling');

        $.getJSON("ChessBoardServlet", {cookie: cookie, tableNumber: tableNumber}, function(data) {
            var playerTableData = "<tr id='tableHeader'><th>Player ID</th><th>Rating</th></tr>";
            $.each(data.player, function(key, value) {
                console.log(value);
                playerTableData = playerTableData + "<tr class='tableList'><td>" + value.userId + "</td><td>" + value.rating + "</td></tr>";
            });
            $("#playerData").html(playerTableData);
            
            if($('.tableList').length === 1){
                stopAllTimes();
                disablePieces();
                enableStartGame();
                if(isHost() === true){
                    disableBootPlayer();
                    enableSendRequest();
                }
            }else if($('.tableList').length === 2){
                if(isHost() === true){
                    enableBootPlayer();
                    disableSendRequest();
                }
            }
            
            $.each(data.messages.messages, function(key, value) {
                console.log(value.message);
                if (value.message !== "") {
                    if(value.message.indexOf("uniquestring123abc") !== -1){
                        $('#joinBoardRequest').html('');
                        $('#joinBoardRequest').html(value.message);
                        if($('#joinBoardRequest').html() !== ''){
                            showRequestTab();
                        }
                    }else if(value.message.indexOf("uniquestringabc123") !== -1){
                        $('#joinBoardResponse').html(value.message);
                        $('#showText > span').html("<span style='font-weight: bold'>Request denied: Reason: </span>" + $('.invitationResponse').attr('data-text'));
                        $('#showText').show();
                    }else{
                        var chatWindowData = $("#chatWindow").html();
                        chatWindowData = chatWindowData + "<br>" + value.message;
                        $("#chatWindow").html(chatWindowData);
                        $("#chatWindow").scrollTop(document.getElementById("chatWindow").scrollHeight);
                    }
                }
            });
        });
    },
            pollInterval = setInterval(function() {
                poll();
            }, 5000);
})();

(function() {
    startGame = function() {
        //console.log('getTime');
        if (gameStarted === false) {
            $.getJSON('GetStartGame', {cookie: cookie, tableNumber: tableNumber}, function(data) {
                //console.log("Time Per move : " + data.timePerMove);
                //console.log("Game Time : " + data.gameTime);
                //console.log("Current player control is :" + data.currentPlayerControl);
                console.log("gameStarted : " + gameStarted);

                if (data.gameStarted === true) {
                    restartTime();
                    setCurrentPlayerControl(data.currentPlayerControl);
                    gameStarted = true;
                    if(isMyControl() === true){
                        enableNativeControl();
                    }
                    $('#playBoardMessage').hide();
                    console.log("currentPlayerControl : " + data.currentPlayerControl);
                }
            });
        }
    },
    getMove = function() {
        if (gameStarted === true) {
            if (isMyControl() === false) {
                $.getJSON('GetMove', {userId: cookie, tableNumber: tableNumber}, function(data) {
                    setCurrentPlayerControl(data.currentPlayerControl);
                    if (isMyControl(data.currentPlayerControl) === true) {
                        //visualizeCurrentPlayerControl();
                        reInitializeTimePerMove(data.timePerMove);
                        console.log('current player control get move : ' + currentPlayerControl);
                        if (data.dataAdded === true) {
                            lastMove = JSON.parse(data.lastMove);
                            if (isMoveSkipped() === true) {
                                enableNativeControl();
                            } else {

                                if (isSimpleMove() === true) {
                                    updatePlayedPieceNextPossiblePositions(lastMove.simpleMove);
                                    movePiece(lastMove.simpleMove.pieceMoved.name, lastMove.simpleMove.previousPosition, lastMove.simpleMove.currentPosition);
                                    enableNativeControl();
                                }

                                if (lastMove.pIeceBeaten === true && lastMove.sPecialMove === false) {
                                    updatePlayedPieceNextPossiblePositions(lastMove.pieceBeaten);
                                    removePieceAt(lastMove.pieceBeaten.beatenPiecePosition.column + lastMove.pieceBeaten.beatenPiecePosition.row);
                                    movePiece(lastMove.pieceBeaten.beatenBy.name, lastMove.pieceBeaten.beatingPiecePreviousPosition, lastMove.pieceBeaten.beatingPieceCurrentPosition);
                                    enableNativeControl();
                                }

                                if (lastMove.sPecialMove === true) {
                                    if (lastMove.specialMove.cAstle === true && lastMove.pIeceBeaten === false) {
                                        movePiece(lastMove.specialMove.castle.kingName.name, lastMove.specialMove.castle.kingPreviousPosition, lastMove.specialMove.castle.kingCurrentPosition);
                                        movePiece(lastMove.specialMove.castle.rockName.name, lastMove.specialMove.castle.rockPreviousPosition, lastMove.specialMove.castle.rockCurrentPosition);
                                    }

                                    if (lastMove.specialMove.pAwnUpgrade === true) {
                                        if (lastMove.pIeceBeaten === true) {
                                            removePiece(lastMove.pieceBeaten.pieceBeaten.name);
                                        }
                                        movePiece(lastMove.specialMove.pawnUpgrade.pawnName.name, lastMove.specialMove.pawnUpgrade.pawnPreviousPosition, lastMove.specialMove.pawnUpgrade.pawnCurrentPosition);
                                        replacePiece(lastMove.specialMove.pawnUpgrade.pawnName.name, lastMove.specialMove.pawnUpgrade.upgradedPiece.name);
                                    }

                                    if (lastMove.specialMove.eNPassantGiven === true) {
                                        movePiece(lastMove.specialMove.enPassantGiven.nativeEnPassantPiece.name, lastMove.specialMove.enPassantGiven.nativePiecePreviousPosition, lastMove.specialMove.enPassantGiven.nativePieceCurrentPosition);
                                    }

                                    if (lastMove.specialMove.eNPassantPlayed === true) {
                                        removePieceAt(lastMove.specialMove.enPassantPlayed.beatenPawnPosition.column + lastMove.specialMove.enPassantPlayed.beatenPawnPosition.row);
                                        movePiece(lastMove.specialMove.enPassantPlayed.beatingPawnName.name, lastMove.specialMove.enPassantPlayed.beatingPawnPreviousPosition, lastMove.specialMove.enPassantPlayed.beatingPawnCurrentPosition);
                                    }
                                    updatePlayedPieceNextPossiblePositions(lastMove.specialMove);
                                    enableNativeControl();
                                }
                                recalculateRestrictedPieces();

                                
                                if (isCheckMate() === true) {
                                    checkMate();
                                }else if (lastMove.cHeck === true) {
                                    disableNativeControl();
                                    removePiecesFromAllowedListThatAreCommonWithRestrictedPieces();
                                    setAllPlayerReturnNextPossbileMoves(lastMove.check.allowedPiecesOfOpponentWithAllowedPositions);  
                                    showCheckNotification();
                                    enableAllowedPieces();
                                }
                            }
                        }
                    }
                });
            }
        }
    },
    pollInterval = setInterval(function() {
        startGame();
        getMove();
    }, 1000);
})();





//get move functions

function enableAllowedPieces() {
    var allowedPiece = lastMove.check.allowedPiecesOfOpponentWithAllowedPositions;

    $.each(allowedPiece, function(key, value) {
        $('#' + key).attr('draggable', 'true');
    });
}

function removePiecesFromAllowedListThatAreCommonWithRestrictedPieces() {
    var allowedPiece = lastMove.check.allowedPiecesOfOpponentWithAllowedPositions;

    $.each(allowedPiece, function(allowedPieceId, allowedPieceValue) {
        $.each(restrictedPiece, function(restrictedPieceId, restrictedPieceValue) {
            if (restrictedPieceValue.restrictedPiece === allowedPieceId) {
                delete allowedPiece[allowedPieceId];
            }
        });
    });
}

function replacePiece(pawnName, upgradedPiece) {
    var upgradedPieceSrc = getOpponentPieceSrc(upgradedPiece);
    $('#' + pawnName).attr('id', upgradedPiece);
    $('#' + upgradedPiece).attr('src', upgradedPieceSrc);
}

function getOpponentPieceSrc(upgradedPiece) {
    var src = '';
    if (team === 'pink') {
        switch (upgradedPiece.charAt(2)) {
            case 'R':
                src = 'images/Brown R.png';
                break;
            case 'B':
                src = 'images/Brown B.png';
                break;
            case 'Q':
                src = 'images/Brown Q.png';
                break;
            case 'N':
                src = 'images/Brown N.png';
                break;
        }
    } else if (team === 'brown') {
        switch (upgradedPiece.charAt(2)) {
            case 'R':
                src = 'images/Red2 R.png';
                break;
            case 'B':
                src = 'images/Red2 B.png';
                break;
            case 'Q':
                src = 'images/Red2 Q.png';
                break;
            case 'N':
                src = 'images/Red2 N.png';
                break;
        }
    }

    return src;
}

function removePieceAt(location){
    var pieces;
    var pieceName = $('#' + location).children()[0].id;
    
    $('#' + pieceName).remove();
    if (team === 'pink') {
        pieces = Pieces.pink;
    } else if (team === 'brown') {
        pieces = Pieces.brown;
    }

    $.each(pieces, function(id, value) {
        if (value.name === pieceName) {
            value.name = 'dead';
            value.locations = [];
        }
    });
}

function removePiece(pieceName) {
    var pieces;
    $('#' + pieceName).parent().html();
    if (team === 'pink') {
        pieces = Pieces.pink;
    } else if (team === 'brown') {
        pieces = Pieces.brown;
    }

    $.each(pieces, function(id, value) {
        if (value.name === pieceName) {
            value.name = 'dead';
            value.locations = [];
        }
    });
}

function movePiece(pieceName, previousPosition, currentPosition) {
    var previousPositionString = '';
    var currentPositionString = '';
    previousPositionString = previousPosition.column + previousPosition.row;
    currentPositionString = currentPosition.column + currentPosition.row;

    var children = $('#' + previousPositionString).children();
    if (children[0].id === pieceName) {
        $('#' + currentPositionString).html(children[0]);
        $('#' + previousPositionString).html();
    }
}

function updatePlayedPieceNextPossiblePositions(data) {
    if (data.opponentReturnSet === true) {
        setAllPlayerReturnNextPossbileMoves(data.opponentNextPossiblePositions);
    }
}

function checkMate() {
    showWinnerNotification(lastMove.checkMate.checkMateBy.userId);
    stopAllTimes();
    disablePieces();
    setCurrentPlayerControl('');
    if(isHost() === true){
        $.getJSON('RedevelopServerSideChessPlayBoard', {userId: cookie, tableNumber: tableNumber}, function(data){
            if(data.redevelopServerSideChessPlayBoardResponse === false){
                alert('There is some problem in server side please create a new chess baord for another game');
            }else if(data.redevelopServerSideChessPlayBoardResponse === true){
                $('#startGame').attr('disabled', false);
            } 
        });
    }else if(isHost() === false){
        $('#startGame').attr('disabled', false);
    }
}

function enableNativeControl() {
    if (team === 'pink') {
        $('.pink').attr('draggable', 'true');
    } else if (team === 'brown') {
        $('.brown').attr('draggable', 'true');
    }
}

function isMoveSkipped() {
    var result = false;

    if (lastMove.moveSkipped === true) {
        result = true;
    }
    return result;
}

function stopAllTimes(){
    $('#gameTime').TimeCircles().stop();
    $('#timePerMove').TimeCircles().stop();
}

//end get move functions







//game related functions
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("pieceName", ev.target.id);
    ev.dataTransfer.setData("previousPosition", ev.target.parentNode.id);
}

function drop(ev) {
    var pieceName = ev.dataTransfer.getData("pieceName");
    var previousPositionId = ev.dataTransfer.getData("previousPosition");
    var currentPosition = getPosition(ev.target);
    console.log("prevousPosition : " + previousPositionId);
    console.log("currentPosition : " + currentPosition.id);
    console.log("pieceName : " + pieceName);
    if(gameStarted === true){
        if (isValidGameTime() === true) {
            if (isValidTimePerMove() === true) {
                if (isValidMove(currentPosition, pieceName) === true) { 

                    appentPiece(pieceName, ev);
                    //disableNativeControl();       for testing purpose
                    if (isCastle(pieceName, currentPosition) === true) {
                        console.log('castle move');
                        //console.log('current position before rock moved : ' + currentPosition.id);
                        moveRockToCastle(currentPosition);
                        //console.log('current position after rock moved : ' + currentPosition.id);
                        setCastlingData(pieceName, currentPosition.id, previousPositionId);
                        console.log(lastMove);
                    } else if (isEnPassantGiven(pieceName, previousPositionId, currentPosition.id) === true) {
                        console.log('enpassant given');
                        setEnPassantGivenData(pieceName, previousPositionId, currentPosition.id);
                        console.log(JSON.stringify(lastMove));
                    } else if (isPawnUpGraded(pieceName, currentPosition.id)) {
                        console.log('pawn upgraded');
                        setUpGradedPawn(ev.target.id, pieceName, previousPositionId, currentPosition.id);
                        return;
                    } else if (isPieceBeaten(ev.target.id) === true) {
                        console.log('piece beaten');
                        setPieceBeatenData(ev.target.id, pieceName, previousPositionId, currentPosition.id);
                        console.log(lastMove);
                    } else if (isEnPassantDue() === true) {
                        console.log('enpassant due');
                        console.log(JSON.stringify(lastMove));
                        var enPassantPlayed = false;
                        if (isEnPassantPlayed(pieceName, previousPositionId, currentPosition.id) === true) {
                            enPassantPlayed = true;
                            console.log('enpassant played');
                            setEnPassantPlayedData(pieceName, previousPositionId, currentPosition.id);
                            destroyBeatenPawn(currentPosition.id);
                            console.log(JSON.stringify(lastMove));
                        } else {
                            if (lastMove !== null) {
                                console.log('enpassant expired');
                                setEnPassantExpired(true);
                                console.log(lastMove.specialMove.eNPassantGiven);
                            }
                        }

                        if (enPassantPlayed === false) {
                            console.log('simple move');
                            setSimpleMoveData(pieceName, previousPositionId, currentPosition.id);
                            console.log(JSON.stringify(lastMove));
                        }
                    } else {
                        console.log('simple move');
                        setSimpleMoveData(pieceName, previousPositionId, currentPosition.id);
                         console.log(JSON.stringify(lastMove));
                    }
                }else{
                    return;     //if placed to invalid position function will quit imediately
                }
                /*
                else {
                    setToPreviousPosition(pieceName, previousPositionId);
                }
                */
            } else {
                setElementryData();     //this is for move skipped
            }
        } else {
            setElementryData();     //this is for move skipped
        }

        resetTimePerMove();
        //stopTimePerMove();
        disableNativeControl();     

        $.post('PlayerMove', {userId: cookie, lastMove: JSON.stringify(lastMove), team: team, tableNumber: tableNumber}, function(data) {

            console.log(JSON.stringify(data.lastMove));
            copyData(data.lastMove);
            setCurrentPlayerControl(data.currentPlayerControl);
            if (lastMove.moveSkipped === false) {
                if (isSimpleMove() === true) {
                    updatePlayerPieceNextPossibleMoves(lastMove.simpleMove);
                }

                if (lastMove.sImpleMove === true) {
                    if (lastMove.sPecialMove === false) {
                        if(lastMove.pIeceBeaten === true){
                            updatePlayerPieceNextPossibleMoves(lastMove.pieceBeaten);
                        }
                    }
                }

                if (lastMove.sPecialMove === true) {
                    var specialMove = lastMove.specialMove;
                    if ((specialMove.cAstle === true && lastMove.pIeceBeaten === false)
                            || specialMove.pAwnUpgrade === true || specialMove.eNPassantGiven === true
                            || specialMove.eNPassantPlayed === true) {

                        updatePlayerPieceNextPossibleMoves(specialMove);
                    }
                }

                console.log(Pieces);

                if (isKingMoved(pieceName) === true) {
                    calculateToKingLocations();
                }

                recalculateRestrictedPieces();

                if (isCheckMate() === true) {
                    checkMate();
                }else if(isCheckGiven() === true) {
                    showCheckNotification();
                }
            }
        });
    }
}

//end game related functions






//restricted piece methods

function calculateToKingLocations(){
    calculateLocationsInIncreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInIncreasingRowAndSetToRestrictedPieceData();
    calculateLocationsInDecreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInDecreasingRowAndSetToRestrictedPieceData();
    calculateLocationsInIncreasingRowIncreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInIncreasingRowDecreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInDecreasingRowDecreasingColumnAndSetToRestrictedPieceData();
    calculateLocationsInDecreasingRowIncreasingColumnAndSetToRestrictedPieceData();
}

function recalculateRestrictedPieces() {
    var restrictedPieceSet = [];
    var commonNativePiecePositions = [];
    restrictedPiece = [];      //before repopulating restricted pieces this array must need to be cleared
    $.each(toKingLocations, function(id, value) {
        if (isDigonal(value.name) === true) {
            restrictedPieceSet = isNativePieceAndOpponentQueenOrBishopSitting(value.locations);
        } else {
            restrictedPieceSet = isNativePieceAndOpponentQueenOrRockSitting(value.locations);
        }

        if (restrictedPieceSet !== null) {
            commonNativePiecePositions = getPieceNextPossiblePositionsMatchingWithThisAxisOrDigonal(restrictedPieceSet, value.locations);
            setPositionsToRestrictedPieceDataAgainstThisPiece(restrictedPieceSet, commonNativePiecePositions);
        }
    });
}

function showCheckNotification() {
    $('#checkGiven').show();
    $('#checkGiven').hide(5000);
}

function isCheckGiven() {
    var result = false;

    if (lastMove.cHeck === true) {
        result = true;
    }
    return result;
}

function showWinnerNotification(checkMateBy) {
    $('#checkMate').html(checkMateBy + ' Wins');
    $('#checkMate').show();
}

function isCheckMate() {
    var result = false;
    if (lastMove.cHeckMate === true) {
        result = true;
    }

    return result;
}

function setPositionsToRestrictedPieceDataAgainstThisPiece(restrictedPieceSet, commonNativePiecePositions) {

    restrictedPiece.push({
        restrictedPiece: restrictedPieceSet[1],
        restrictedBecauseOf: restrictedPieceSet[2],
        restrictedFor: restrictedPieceSet[0],
        allowedPositions: commonNativePiecePositions
    });
}

function getPieceNextPossiblePositionsMatchingWithThisAxisOrDigonal(restrictedPieceSet, locations) {
    var restrictedPieceName = restrictedPieceSet[1];
    var restrictedPieceLocations = [];
    var restrictedPieceData;
    var result = [];
    if (team === 'pink') {
        restrictedPieceData = Pieces.pink;
    } else if (team === 'brown') {
        restrictedPieceData = Pieces.brown;
    }

    $.each(restrictedPieceData, function(id, value) {
        if (value.name === restrictedPieceName) {
            for(var i=0; i<value.nextPossiblePositions.length; i++){
                restrictedPieceLocations[i] = value.nextPossiblePositions[i];
            }
        }
    });

    result = getCommonLocations(restrictedPieceLocations, locations);

    return result;
}

function getCommonLocations(restrictedPieceLocations, locations) {
    var commonLocations = [];

    for (var i = 0; i < restrictedPieceLocations.length; i++) {
        for (var j = 0; j < locations.length; j++) {
            if (restrictedPieceLocations[i] === locations[j]) {
                commonLocations.push(locations[j]);
            }
        }
    }
    return commonLocations;
}

function isNativePieceAndOpponentQueenOrRockSitting(locations) {
    var nativePieceSitting = false;
    var opponentQueenOrBishopSitting = false;
    var restrictedPieceSet = [];
    var children;
    for (var i = 0; i < locations.length; i++) {
        if (document.getElementById(locations[i]).childElementCount > 0) {
            children = document.getElementById(locations[i]).childNodes;
            if (isNativePiece(children[0].id) === true) {
                if (nativePieceSitting === false) {
                    nativePieceSitting = true;
                    restrictedPieceSet.push(getKing());
                    restrictedPieceSet.push(children[0].id);
                } else {
                    break;
                }
            } else {
                if (nativePieceSitting === true) {
                    if (isQueenOrRock(children[0].id) === true) {
                        opponentQueenOrBishopSitting = true;
                        restrictedPieceSet.push(children[0].id);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (nativePieceSitting === true && opponentQueenOrBishopSitting === true) {
            break;
        }
    }

    if (nativePieceSitting === false || opponentQueenOrBishopSitting === false) {
        restrictedPieceSet = null;
    }

    return restrictedPieceSet;
}

function isQueenOrRock(pieceName) {
    var result = false;
    if(pieceName.length === 3){
        if (pieceName.charAt(2) === 'Q' || pieceName.charAt(2) === 'R') {
            result = true;
        }
    }else if(pieceName.length === 2){
        if(pieceName.charAt(1) === 'Q'){
            result = true;
        }
    }

    return result;
}

function isNativePieceAndOpponentQueenOrBishopSitting(locations) {
    var nativePieceSitting = false;
    var opponentQueenOrBishopSitting = false;
    var restrictedPieceSet = [];
    var children;
    for (var i = 0; i < locations.length; i++) {
        if (document.getElementById(locations[i]).childElementCount > 0) {
            children = document.getElementById(locations[i]).childNodes;
            if (isNativePiece(children[0].id) === true) {
                if (nativePieceSitting === false) {
                    nativePieceSitting = true;
                    restrictedPieceSet.push(getKing());
                    restrictedPieceSet.push(children[0].id);
                } else {
                    break;
                }
            } else {
                if (nativePieceSitting === true) {
                    if (isQueenOrBishop(children[0].id) === true) {
                        opponentQueenOrBishopSitting = true;
                        restrictedPieceSet.push(children[0].id);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (nativePieceSitting === true && opponentQueenOrBishopSitting === true) {
            break;
        }
    }

    if (nativePieceSitting === false || opponentQueenOrBishopSitting === false) {
        restrictedPieceSet = null;
    }

    return restrictedPieceSet;
}

function isNativePiece(pieceName) {
    var result = false;

    if (team.toUpperCase().charAt(0) === pieceName.charAt(0)) {
        result = true;
    }

    return result;
}

function isQueenOrBishop(pieceName) {
    var result = false;
    if(pieceName.length === 3){
        if (pieceName.charAt(2) === 'Q' || pieceName.charAt(2) === 'B') {
            result = true;
        }
    }else if(pieceName.length === 2){
        if(pieceName.charAt(1) === 'Q'){
            result = true;
        }
    }

    return result;
}

function isDigonal(name) {
    var result = false;
    switch (name) {
        case 'increasingRowIncreasingColumn':
        case 'increasingRowDecreasingColumn':
        case 'decreasingRowDecreasingColumn':
        case 'decreasingRowIncreasingColumn':
            result = true;
            break;
    }

    return result;
}
//end restricted piece methods







//update to king locations functions

function calculateLocationsInDecreasingRowIncreasingColumnAndSetToRestrictedPieceData() {
    var decreasingRowIncreasingColumnLocations = [];
    decreasingRowIncreasingColumnLocations = calculateLocations(0, 1, 1, 0);
    $.each(toKingLocations, function(id, value) {
        if (value.name === 'decreasingRowIncreasingColumn') {
            value.locations = decreasingRowIncreasingColumnLocations;
        }
    });
}

function calculateLocationsInDecreasingRowDecreasingColumnAndSetToRestrictedPieceData() {
    var decreasingRowDecreasingColumnLocations = [];
    decreasingRowDecreasingColumnLocations = calculateLocations(0, 0, 1, 1);
    $.each(toKingLocations, function(id, value) {
        if (value.name === 'decreasingRowDecreasingColumn') {
            value.locations = decreasingRowDecreasingColumnLocations;
        }
    });
}

function calculateLocationsInIncreasingRowDecreasingColumnAndSetToRestrictedPieceData() {
    var increasingRowDecreasingColumnLocations = [];
    increasingRowDecreasingColumnLocations = calculateLocations(1, 0, 0, 1);
    $.each(toKingLocations, function(id, value) {
        if (value.name === 'increasingRowDecreasingColumn') {
            value.locations = increasingRowDecreasingColumnLocations;
        }
    });
}

function calculateLocationsInIncreasingRowIncreasingColumnAndSetToRestrictedPieceData() {
    var increasingRowIncreasingColumnLocations = [];
    increasingRowIncreasingColumnLocations = calculateLocations(1, 1, 0, 0);
    $.each(toKingLocations, function(id, value) {
        if (value.name === 'increasingRowIncreasingColumn') {
            value.locations = increasingRowIncreasingColumnLocations;
        }
    });
}

function calculateLocationsInDecreasingRowAndSetToRestrictedPieceData() {
    var decreasingRowLocations = [];
    decreasingRowLocations = calculateLocations(0, 0, 1, 0);
    $.each(toKingLocations, function(id, value) {
        if (value.name === 'decreasingRow') {
            value.locations = decreasingRowLocations;
        }
    });
}

function calculateLocationsInDecreasingColumnAndSetToRestrictedPieceData() {
    var decreasingColumnLocations = [];
    decreasingColumnLocations = calculateLocations(0, 0, 0, 1);
    $.each(toKingLocations, function(id, value) {
        if (value.name === 'decreasingColumn') {
            value.locations = decreasingColumnLocations;
        }
    });
}

function calculateLocationsInIncreasingRowAndSetToRestrictedPieceData() {
    var increasingRowLocations = [];
    increasingRowLocations = calculateLocations(1, 0, 0, 0);
    $.each(toKingLocations, function(id, value) {
        if (value.name === 'increasingRow') {
            value.locations = increasingRowLocations;
        }
    });
}

function calculateLocationsInIncreasingColumnAndSetToRestrictedPieceData() {
    var increasingColumnLocations = [];
    increasingColumnLocations = calculateLocations(0, 1, 0, 0);
    $.each(toKingLocations, function(id, value) {
        if (value.name === 'increasingColumn') {
            value.locations = increasingColumnLocations;
        }
    });
}

function calculateLocations(up, right, down, left) {
    var kingPosition = getKingLocation(getKing());
    var locations = [];
    var previousLocation = kingPosition;
    do {
        previousLocation = getLocation(up, right, down, left, previousLocation);
        if (previousLocation !== null) {
            locations.push(previousLocation);
        }
    } while (previousLocation !== null)

    return locations;
}

function getLocation(up, right, down, left, previousLocation) {
    var nextLocation = '';
    var newRowAscii = previousLocation.charCodeAt(1);
    var newColumnAscii = previousLocation.charCodeAt(0);
    if (team === 'brown') {
        if (up !== 0) {
            newRowAscii = (newRowAscii + up);
        } else if (down !== 0) {
            newRowAscii = (newRowAscii - down);
        }

        if (right !== 0) {
            newColumnAscii = (newColumnAscii + right);
        } else if (left !== 0) {
            newColumnAscii = (newColumnAscii - left);
        }
    } else if (team === 'pink') {
        if (up !== 0) {
            newRowAscii = (newRowAscii - up);
        } else if (down !== 0) {
            newRowAscii = (newRowAscii + down);
        }

        if (right !== 0) {
            newColumnAscii = (newColumnAscii - right);
        } else if (left !== 0) {
            newColumnAscii = (newColumnAscii + left);
        }
    }

    if (newColumnAscii >= 97 && newColumnAscii <= 104 &&
            newRowAscii >= 49 && newRowAscii <= 56) {
        nextLocation = String.fromCharCode(newColumnAscii, newRowAscii);
    } else {
        nextLocation = null;
    }

    return nextLocation;
}

function getKingLocation(kingName) {
    var kingLocation = '';
    kingLocation = $('#' + kingName).parent().attr('id');
    return kingLocation;
}

function isKingMoved(pieceName) {
    var result = false;
    if (isKing(pieceName) === true) {
        result = true;
    }

    return result;
}

//end to king locations functions






//start data returned functions
function updatePlayerPieceNextPossibleMoves(data) {
    if (data.nativeReturnSet === true) {
        setAllPlayerReturnNextPossbileMoves(data.nativeNextPossiblePositions);
    }
}

function setAllPlayerReturnNextPossbileMoves(data) {
    var pieceData = Pieces;
    
    if (team === 'pink') {
        pieceData = Pieces.pink;
    } else if (team === 'brown') {
        pieceData = Pieces.brown;
    }

    $.each(pieceData, function(id, value){
        value.nextPossiblePositions = [];
    });
    
    $.each(data, function(key, positions) {
        $.each(pieceData, function(id, value) {
            if (value.name === key) {
                value.nextPossiblePositions = getNextPossiblePositions(positions);
            }
        });
    });
}

function getNextPossiblePositions(positions) {
    var nextPossiblePositionsAsString = [];

    $.each(positions, function(id, rowColumn) {
        nextPossiblePositionsAsString.push(rowColumn.column + rowColumn.row);
    });

    return nextPossiblePositionsAsString;
}

function isSimpleMove() {
    var result = false;
    if (lastMove.sImpleMove === true) {
        result = true;
    }

    return result;
}

function copyData(data) {
    lastMove = JSON.parse(data);
}
//end data returned functions





//else part
function setElementryData() {
    createLastMoveObject();
    console.log(lastMove);
    lastMove.moveSkipped = true;
    lastMove.playedBy.userId = cookie;
    lastMove.toBeRecievedBy.userId = getOpponent();
    lastMove.played = true;
}
//end else part




//simple move functions

function setSimpleMoveData(pieceName, previousPositionId, currentPositionId) {
    createLastMoveObject();
    lastMove.sImpleMove = true;
    lastMove.playedBy.userId = cookie;
    lastMove.toBeRecievedBy.userId = getOpponent();
    lastMove.played = true;
    lastMove.simpleMove.pieceMoved.name = pieceName;
    lastMove.simpleMove.previousPosition.row = previousPositionId.charAt(1);
    lastMove.simpleMove.previousPosition.column = previousPositionId.charAt(0);
    lastMove.simpleMove.currentPosition.row = currentPositionId.charAt(1);
    lastMove.simpleMove.currentPosition.column = currentPositionId.charAt(0);
}
//end simple move functions





//special move functions






//Enpassant played functions

function setEnPassantPlayedData(pieceName, previousPositionId, currentPositionId) {
    var pieceBeaten = document.getElementById(getOnePositionDown(currentPositionId)).childNodes[0].id;

    createLastMoveObject();
    lastMove.sPecialMove = true;
    lastMove.playedBy.userId = cookie;
    lastMove.toBeRecievedBy.userId = getOpponent();
    lastMove.played = true;
    lastMove.specialMove.eNPassantPlayed = true;
    lastMove.specialMove.enPassantPlayed.beatenPawnName.name = pieceBeaten;
    lastMove.specialMove.enPassantPlayed.beatingPawnName.name = pieceName;
    lastMove.specialMove.enPassantPlayed.beatenPawnPosition.row = getOnePositionDown(currentPositionId).charAt(1);
    lastMove.specialMove.enPassantPlayed.beatenPawnPosition.column = getOnePositionDown(currentPositionId).charAt(0);
    lastMove.specialMove.enPassantPlayed.beatingPawnPreviousPosition.row = previousPositionId.charAt(1);
    lastMove.specialMove.enPassantPlayed.beatingPawnPreviousPosition.column = previousPositionId.charAt(0);
    lastMove.specialMove.enPassantPlayed.beatingPawnCurrentPosition.row = currentPositionId.charAt(1);
    lastMove.specialMove.enPassantPlayed.beatingPawnCurrentPosition.column = currentPositionId.charAt(0);
}

function destroyBeatenPawn(currentPositionId) {

    removePawnFromChessBoard(currentPositionId);
    setEnPassantExpired(true);
}

function removePawnFromChessBoard(currentPositionId) {
    document.getElementById(getOnePositionDown(currentPositionId)).innerHTML = '';
}

function setEnPassantExpired(boolean) {
    if (lastMove !== null) {
        lastMove.specialMove.enPassantGiven.enPassantExpired === boolean;
    }
}

function isEnPassantPlayed(pieceName, previousPositionId, currentPositionId) {
    var result = false;

    if (isEnPassantDue() === true) {
        if (isPawn(pieceName) === true) {
            if (isPlayedDiagonally(previousPositionId, currentPositionId) === true) {
                if (isPawnPlayedMatchWithOpponentCanPassantData(pieceName) === true) {
                    if (isPieceSitOneDownPosition(currentPositionId) === true) {
                        if (doesPieceMatchOpponentCanPassantData(currentPositionId) === true) {
                            result = true;
                        }
                    }
                }
            }
        }
    }

    return result;
}

function doesPieceMatchOpponentCanPassantData(currentPositionId) {
    var result = false;
    if (document.getElementById(getOnePositionDown(currentPositionId)).childNodes[0].id === lastMove.specialMove.enPassantGiven.nativeEnPassantPiece.name) {
        result = true;
    }

    return result;
}

function isPieceSitOneDownPosition(currentPositionId) {
    var result = false;
    if (document.getElementById(getOnePositionDown(currentPositionId)).hasChildNodes() === true) {
        result = true;
    }
    return result;
}

function getOnePositionDown(currentPositionId) {
    var result = '';
    var brownRows = ['1', '2', '3', '4', '5', '6', '7', '8'];
    var brownColumns = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
    var pinkRows = ['8', '7', '6', '5', '4', '3', '2', '1'];
    var pinkColumns = ['h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'];

    if (team === 'brown') {
        for (var i = 0; i < brownRows.length; i++) {
            if (currentPositionId.charAt(1) === brownRows[i]) {
                result = currentPositionId.charAt(0) + brownRows[i - 1];
            }
        }
    } else if (team === 'pink') {
        for (var i = 0; i < pinkRows.length; i++) {
            if (currentPositionId.charAt(1) === pinkRows[i]) {
                result = currentPositionId.charAt(0) + pinkRows[i - 1];
            }
        }
    }
    console.log('one position down : ' + result);
    return result;
}

function isPawnPlayedMatchWithOpponentCanPassantData(pieceName) {
    var result = false;

    if (lastMove.specialMove.enPassantGiven.rightOpponentEnPassantPiece === true) {
        if (lastMove.specialMove.enPassantGiven.opponentRightEnPassantPiece.name === pieceName) {
            result = true;
        }
    }

    if (lastMove.specialMove.enPassantGiven.leftOpponentEnPassantPiece === true) {
        if (lastMove.specialMove.enPassantGiven.opponentLeftEnPassantPiece.name === pieceName) {
            result = true;
        }
    }
    return result;
}

function isPlayedDiagonally(previousPositionId, currentPositionId) {
    var result = false;
    if (previousPositionId.charAt(0) !== currentPositionId.charAt(0)) {
        if (previousPositionId.charAt(1) !== currentPositionId.charAt(1)) {
            result = true;
        }
    }

    return result;
}

function isEnPassantDue() {
    var result = false;
    if (lastMove !== null) {
        if (lastMove.sPecialMove === true) {
            if (lastMove.specialMove.eNPassantGiven === true) {
                if (lastMove.specialMove.enPassantGiven.enPassantExpired === false) {
                    result = true;
                }
            }
        }
    }

    return result;
}
//End Enpassant played






//piece beaten functions

function isPieceBeaten(target) {
    var result = false;
    if (isPosition(target) === false) {
        result = true;
    }

    return result;
}

function setPieceBeatenData(pieceBeaten, pieceName, previousPositionId, currentPositionId) {
    createLastMoveObject();
    lastMove.pIeceBeaten = true;
    lastMove.playedBy.userId = cookie;
    lastMove.toBeRecievedBy.userId = getOpponent();
    lastMove.played = true;
    lastMove.pieceBeaten.pieceBeaten.name = pieceBeaten;
    lastMove.pieceBeaten.beatenBy.name = pieceName;
    lastMove.pieceBeaten.beatingPiecePreviousPosition.row = previousPositionId.charAt(1);
    lastMove.pieceBeaten.beatingPiecePreviousPosition.column = previousPositionId.charAt(0);
    lastMove.pieceBeaten.beatingPieceCurrentPosition.row = currentPositionId.charAt(1);
    lastMove.pieceBeaten.beatingPieceCurrentPosition.column = currentPositionId.charAt(0);
    lastMove.pieceBeaten.beatenPiecePosition.row = currentPositionId.charAt(1);
    lastMove.pieceBeaten.beatenPiecePosition.column = currentPositionId.charAt(0);

}

//end of piece beaten functions




//pawn upgraded funcitons

function setUpGradedPieceData(target, pieceName, result, previousPositionId, currentPositionId) {
    createLastMoveObject();
    lastMove.sPecialMove = true;
    lastMove.playedBy.userId = cookie;
    lastMove.toBeRecievedBy.userId = getOpponent();
    lastMove.played = true;
    lastMove.specialMove.pAwnUpgrade = true;
    lastMove.specialMove.pawnUpgrade.pawnName.name = pieceName;
    lastMove.specialMove.pawnUpgrade.upgradedPiece.name = result;
    lastMove.specialMove.pawnUpgrade.pawnPreviousPosition.row = previousPositionId.charAt(1);
    lastMove.specialMove.pawnUpgrade.pawnPreviousPosition.column = previousPositionId.charAt(0);
    lastMove.specialMove.pawnUpgrade.pawnCurrentPosition.row = currentPositionId.charAt(1);
    lastMove.specialMove.pawnUpgrade.pawnCurrentPosition.column = currentPositionId.charAt(0);
    lastMove.specialMove.pawnUpgrade.upgradedPiecePosition.row = currentPositionId.charAt(1);
    lastMove.specialMove.pawnUpgrade.upgradedPiecePosition.column = currentPositionId.charAt(0);
    if (isPieceBeaten(target) === true) {
        console.log('piece beaten pawn upgrade data');
        lastMove.pIeceBeaten = true;
        lastMove.playedBy.userId = cookie;
        lastMove.toBeRecievedBy.userId = getOpponent();
        lastMove.played = true;
        lastMove.pieceBeaten.pieceBeaten.name = target;
        lastMove.pieceBeaten.beatenBy.name = pieceName;
        lastMove.pieceBeaten.beatingPiecePreviousPosition.row = previousPositionId.charAt(1);
        lastMove.pieceBeaten.beatingPiecePreviousPosition.column = previousPositionId.charAt(0);
        lastMove.pieceBeaten.beatingPieceCurrentPosition.row = currentPositionId.charAt(1);
        lastMove.pieceBeaten.beatingPieceCurrentPosition.column = currentPositionId.charAt(0);
        lastMove.pieceBeaten.beatenPiecePosition.row = currentPositionId.charAt(1);
        lastMove.pieceBeaten.beatenPiecePosition.column = currentPositionId.charAt(0);
    }
    console.log(lastMove);
}

function replacePreviousPawnWithUpGradedPiece(upGradedPieceName, pieceName) {
    //replacing data
    $.each(Pieces, function(id, value) {
        if (id === team) {
            $.each(value, function(id, value) {
                if (value.name === pieceName) {
                    value.name = upGradedPieceName;
                    value.nextPossiblePositions = [];
                    console.log(value.name);
                    console.log(value.nextPossiblePositions);
                }
            });
        }
    });

    //replacing image i.e. replacing visual affects
    $('#' + pieceName).attr('src', getUpgradedPieceImageSrc(upGradedPieceName));
    $('#' + pieceName).attr('id', upGradedPieceName);
}

function getUpgradedPieceImageSrc(upGradedPieceName) {
    var result = '';
    switch (upGradedPieceName[2]) {
        case 'R':
            if (team === 'brown') {
                result = "images/Brown R.png";
            } else if (team === 'pink') {
                result = "images/Red2 R.png";
            }
            break;
        case 'N':
            if (team === 'brown') {
                result = "images/Brown N.png";
            } else if (team === 'pink') {
                result = "images/Red2 N.png";
            }
            break;
        case 'B':
            if (team === 'brown') {
                result = "images/Brown B.png";
            } else if (team === 'pink') {
                result = "images/Red2 B.png";
            }
            break;
        case 'Q':
            if (team === 'brown') {
                result = "images/Brown Q.png";
            } else if (team === 'pink') {
                result = "images/Red2 Q.png";
            }
            break;
    }

    return result;
}

function setUpGradedPawn(target, pieceName, previousPositionId, currentPositionId, ev) {
    var result = '';

    $('#choosePiece').show();
    $('#choosePiece img')
            .one('mouseenter', function() {
                $(this).css({'cursor': 'pointer'});
            })
            .one('click', function() {
                result = $(this).attr('id');
                $(this).attr('id', getNewIdForUpgradedPiece(result));
                console.log(result);
                replacePreviousPawnWithUpGradedPiece(result, pieceName);
                setUpGradedPieceData(target, pieceName, result, previousPositionId, currentPositionId);
                $('#choosePiece').hide();
                postRequisitePawnUpgrade(pieceName);
                console.log(lastMove);
            });
            
    return result;
}

function getNewIdForUpgradedPiece(id) {
    var newName = '';

    var temp = parseInt(id.charAt(1));
    temp++;
    newName = id[0] + temp + id[2];

    return newName;
}

function isPawnUpGraded(pieceName, currentPosition) {
    var result = false;

    if (isOpponentBase(currentPosition) === true) {
        if (isPawn(pieceName) === true) {
            result = true;
        }
    }
    return result;
}

function isOpponentBase(position) {
    var result = false;
    var brownRows = ['1', '2', '3', '4', '5', '6', '7', '8'];
    var pinkRows = ['8', '7', '6', '5', '4', '3', '2', '1'];
    if (team === 'brown') {
        if (position[1] === brownRows[brownRows.length - 1]) {
            result = true;
        }
    } else if (team === 'pink') {
        if (position[1] === pinkRows[pinkRows.length - 1]) {
            result = true;
        }
    }

    return result;
}

function postRequisitePawnUpgrade(pieceName){
    resetTimePerMove();
    //stopTimePerMove();
    disableNativeControl();     

    $.post('PlayerMove', {userId: cookie, lastMove: JSON.stringify(lastMove), team: team, tableNumber: tableNumber}, function(data) {

        console.log(JSON.stringify(data.lastMove));
        copyData(data.lastMove);
        setCurrentPlayerControl(data.currentPlayerControl);
        if (lastMove.moveSkipped === false) {
            if (isSimpleMove() === true) {
                updatePlayerPieceNextPossibleMoves(lastMove.simpleMove);
            }

            if (lastMove.sImpleMove === true) {
                if (lastMove.sPecialMove === false) {
                    if(lastMove.pIeceBeaten === true){
                        updatePlayerPieceNextPossibleMoves(lastMove.pieceBeaten);
                    }
                }
            }

            if (lastMove.sPecialMove === true) {
                var specialMove = lastMove.specialMove;
                if ((specialMove.cAstle === true && lastMove.pIeceBeaten === false)
                        || specialMove.pAwnUpgrade === true || specialMove.eNPassantGiven === true
                        || specialMove.eNPassantPlayed === true) {

                    updatePlayerPieceNextPossibleMoves(specialMove);
                }
            }

            console.log(Pieces);

            if (isKingMoved(pieceName) === true) {
                calculateToKingLocations();
            }

            recalculateRestrictedPieces();

            if (isCheckMate() === true) {
                checkMate();
            }else if(isCheckGiven() === true) {
                showCheckNotification();
            }
        }
    });
}

//end pawn upgraded functions






//Enpassant Given function

function isEnPassantGiven(pieceName, previousPositionId, currentPositionId) {
    var enPassantGiven = false;
    if (isPawn(pieceName) === true) {
        if (team === 'brown') {
            if (previousPositionId.charAt(1) === '2' && currentPositionId.charAt(1) === '4') {
                console.log('valid prevois current position');
                if (validdateRightLeftEnpassantConditions(currentPositionId) === true) {
                    enPassantGiven = true;
                }
            }
        } else if (team === 'pink') {
            if (previousPositionId.charAt(1) === '7' && currentPositionId.charAt(1) === '5') {
                if (validdateRightLeftEnpassantConditions(currentPositionId) === true) {
                    enPassantGiven = true;
                }
            }
        }
    }

    return enPassantGiven;
}

function setEnPassantGivenData(pieceName, previousPositionId, currentPositionId) {
    createLastMoveObject();
    lastMove.sPecialMove = true;
    lastMove.playedBy.userId = cookie;
    lastMove.toBeRecievedBy.userId = getOpponent();
    lastMove.played = true;
    lastMove.specialMove.eNPassantGiven = true;
    lastMove.specialMove.enPassantGiven.nativePlayer.userId = cookie;
    lastMove.specialMove.enPassantGiven.opponentPlayer.userId = getOpponent();
    lastMove.specialMove.enPassantGiven.nativeEnPassantPiece.name = pieceName;
    lastMove.specialMove.enPassantGiven.nativePiecePreviousPosition.row = previousPositionId.charAt(1);
    lastMove.specialMove.enPassantGiven.nativePiecePreviousPosition.column = previousPositionId.charAt(0);
    lastMove.specialMove.enPassantGiven.nativePieceCurrentPosition.row = currentPositionId.charAt(1);
    lastMove.specialMove.enPassantGiven.nativePieceCurrentPosition.column = currentPositionId.charAt(0);
    lastMove.specialMove.enPassantGiven.rightOpponentEnPassantPiece = isOpponentPawnSittingRight(currentPositionId);
    lastMove.specialMove.enPassantGiven.leftOpponentEnPassantPiece = isOpponentPawnSittingLeft(currentPositionId);
    lastMove.specialMove.enPassantGiven.opponentRightEnPassantPiece.name = getOpponentRightEnPassantPiece(currentPositionId, lastMove.specialMove.enPassantGiven.rightOpponentEnPassantPiece);
    lastMove.specialMove.enPassantGiven.opponentLeftEnPassantPiece.name = getOpponentLeftEnPassantPiece(currentPositionId, lastMove.specialMove.enPassantGiven.leftOpponentEnPassantPiece);
    lastMove.specialMove.enPassantGiven.opponentRightPiecePreviousPosition.row = getOpponentRightPiecePreviousPosition(lastMove.specialMove.enPassantGiven.opponentRightEnPassantPiece.name, lastMove.specialMove.enPassantGiven.rightOpponentEnPassantPiece).charAt(1);
    lastMove.specialMove.enPassantGiven.opponentRightPiecePreviousPosition.column = getOpponentRightPiecePreviousPosition(lastMove.specialMove.enPassantGiven.opponentRightEnPassantPiece.name, lastMove.specialMove.enPassantGiven.rightOpponentEnPassantPiece).charAt(0);
    lastMove.specialMove.enPassantGiven.opponentRightPieceNextPossiblePosition.row = getOpponentRightPieceNextPossiblePosition(lastMove.specialMove.enPassantGiven.opponentRightPiecePreviousPosition, lastMove.specialMove.enPassantGiven.rightOpponentEnPassantPiece).charAt(1);
    lastMove.specialMove.enPassantGiven.opponentRightPieceNextPossiblePosition.column = getOpponentRightPieceNextPossiblePosition(lastMove.specialMove.enPassantGiven.opponentRightPiecePreviousPosition, lastMove.specialMove.enPassantGiven.rightOpponentEnPassantPiece).charAt(0);
    lastMove.specialMove.enPassantGiven.opponentLeftPiecePreviousPosition.row = getOpponentLeftPiecePreviousPosition(lastMove.specialMove.enPassantGiven.opponentLeftEnPassantPiece.name, lastMove.specialMove.enPassantGiven.leftOpponentEnPassantPiece).charAt(1);
    lastMove.specialMove.enPassantGiven.opponentLeftPiecePreviousPosition.column = getOpponentLeftPiecePreviousPosition(lastMove.specialMove.enPassantGiven.opponentLeftEnPassantPiece.name, lastMove.specialMove.enPassantGiven.leftOpponentEnPassantPiece).charAt(0);
    lastMove.specialMove.enPassantGiven.opponentLeftPieceNextPossiblePosition.row = getOpponentLeftPieceNextPossiblePosition(lastMove.specialMove.enPassantGiven.opponentLeftPiecePreviousPosition, lastMove.specialMove.enPassantGiven.leftOpponentEnPassantPiece).charAt(1);
    lastMove.specialMove.enPassantGiven.opponentLeftPieceNextPossiblePosition.column = getOpponentLeftPieceNextPossiblePosition(lastMove.specialMove.enPassantGiven.opponentLeftPiecePreviousPosition, lastMove.specialMove.enPassantGiven.leftOpponentEnPassantPiece).charAt(0);
    lastMove.specialMove.enPassantGiven.enPassantExpired = false;
}

function getOpponentLeftPieceNextPossiblePosition(opponentLeftPieceCurrentPosition, isOpponentPawnSitting) {
    var result = '';
    if (isOpponentPawnSitting === true) {
        result = getPositionUpLeft(opponentLeftPieceCurrentPosition);
    }

    return result;
}

function getPositionUpLeft(position) {
    var result = '';
    var brownRows = ['1', '2', '3', '4', '5', '6', '7', '8'];
    var brownColumns = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
    var pinkRows = ['8', '7', '6', '5', '4', '3', '2', '1'];
    var pinkColumns = ['h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'];
    if (team === 'pink') {
        for (var i = 0; i < brownColumns.length; i++) {
            if (position.column === brownColumns[i]) {
                result = brownColumns[i - 1];
                break;
            }
        }
        for (var i = 0; i < brownRows.length; i++) {
            if (position.row === brownRows[i]) {
                result = result + brownRows[i + 1];
                break;
            }
        }
    } else if (team === 'brown') {
        for (var i = 0; i < pinkColumns.length; i++) {
            if (position.column === pinkColumns[i]) {
                result = pinkColumns[i - 1];
                break;
            }
        }
        for (var i = 0; i < pinkRows.length; i++) {
            if (position.row === pinkRows[i]) {
                result = result + pinkRows[i + 1];
                break;
            }
        }
    }

    return result;
}

function getOpponentLeftPiecePreviousPosition(opponentLeftEnPassantPiece, isOpponentPawnSitting) {
    var result = '';
    if (isOpponentPawnSitting === true) {
        result = document.getElementById(opponentLeftEnPassantPiece).parentNode.id;
    }
    return result;
}

function getOpponentRightPieceNextPossiblePosition(opponentRightPieceCurrentPosition, isOpponentPawnSitting) {
    var result = '';
    if (isOpponentPawnSitting === true) {
        result = getPositionUpRight(opponentRightPieceCurrentPosition);
    }

    return result;
}

function getPositionUpRight(position) {
    var result = '';
    var brownRows = ['1', '2', '3', '4', '5', '6', '7', '8'];
    var brownColumns = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
    var pinkRows = ['8', '7', '6', '5', '4', '3', '2', '1'];
    var pinkColumns = ['h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'];
    if (team === 'pink') {
        for (var i = 0; i < brownColumns.length; i++) {
            if (position.column === brownColumns[i]) {
                result = brownColumns[i + 1];
                break;
            }
        }
        for (var i = 0; i < brownRows.length; i++) {
            if (position.row === brownRows[i]) {
                result = result + brownRows[i + 1];
                break;
            }
        }
    } else if (team === 'brown') {
        for (var i = 0; i < pinkColumns.length; i++) {
            if (position.column === pinkColumns[i]) {
                result = pinkColumns[i + 1];
                break;
            }
        }
        for (var i = 0; i < pinkRows.length; i++) {
            if (position.row === pinkRows[i]) {
                result = result + pinkRows[i + 1];
                break;
            }
        }
    }

    return result;
}

function getOpponentRightPiecePreviousPosition(opponentRightEnPassantPiece, isOpponentPawnSitting) {
    var result = '';
    if (isOpponentPawnSitting === true) {
        result = document.getElementById(opponentRightEnPassantPiece).parentNode.id;
    }
    return result;
}

function getOpponentLeftEnPassantPiece(currentPositionId, isOpponentPawnSitting) {
    var result = '';
    if (isOpponentPawnSitting === true) {
        result = document.getElementById(getPositionLeft(currentPositionId)).childNodes[0].id;
    }

    return result;
}

function getOpponentRightEnPassantPiece(currentPositionId, isOpponentPawnSitting) {
    var result = '';
    if (isOpponentPawnSitting === true) {
        result = document.getElementById(getPositionRight(currentPositionId)).childNodes[0].id;
    }

    return result;
}

function validdateRightLeftEnpassantConditions(currentPositionId) {
    var enPassantGiven = false;
    if (isValidPositionRight(currentPositionId) === true) {
        console.log('valid right position');
        if (isOpponentPawnSittingRight(currentPositionId) === true) {
            enPassantGiven = true;
        }
    }
    if (isValidPositionLeft(currentPositionId) === true) {
        console.log('valid left position');
        if (isOpponentPawnSittingLeft(currentPositionId) === true) {
            enPassantGiven = true;
        }
    }

    return enPassantGiven;
}

function isValidPositionLeft(currentPositionId) {
    var dangerousColumn = '';
    var validPosition = false;
    if (team === 'pink') {
        dangerousColumn = 'h';
    } else if (team === 'brown') {
        dangerousColumn = 'a';
    }

    if (currentPositionId.charAt(0) !== dangerousColumn) {
        validPosition = true;
    }

    return validPosition;
}

function isValidPositionRight(currentPositionId) {
    var dangerousColumn = '';
    var validPosition = false;
    if (team === 'pink') {
        dangerousColumn = 'a';
    } else if (team === 'brown') {
        dangerousColumn = 'h';
    }

    if (currentPositionId.charAt(0) !== dangerousColumn) {
        validPosition = true;
    }

    return validPosition;
}

function isOpponentPawnSittingLeft(currentPositionId) {
    var leftPosition = getPositionLeft(currentPositionId);
    //console.log(leftPosition);
    var result = false;
    if (leftPosition !== '') {
        var pieceNames = document.getElementById(leftPosition).childNodes;
        if (pieceNames.length > 0) {
            //console.log('piece sitting');
            if (isOpponentPiece(pieceNames[0].id) === true) {
                if (isPawn(pieceNames[0].id) === true) {
                    //console.log('opponent piece pawn valid');
                    result = true;
                }
            }
        }
    }
    return result;
}

function getPositionLeft(position) {
    var result = '';
    var brownRows = ['1', '2', '3', '4', '5', '6', '7', '8'];
    var brownColumns = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
    var pinkRows = ['8', '7', '6', '5', '4', '3', '2', '1'];
    var pinkColumns = ['h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'];
    if (team === 'brown') {
        for (var i = 1; i < brownColumns.length; i++) {
            if (position.charAt(0) === brownColumns[i]) {
                result = brownColumns[i - 1] + position.charAt(1);
                break;
            }
        }
    } else if (team === 'pink') {
        for (var i = 1; i < pinkColumns.length; i++) {
            if (position.charAt(0) === pinkColumns[i]) {
                result = pinkColumns[i - 1] + position.charAt(1);
                break;
            }
        }
    }

    return result;
}

function isOpponentPawnSittingRight(currentPositionId) {
    var rightPosition = getPositionRight(currentPositionId);
    var result = false;

    if (rightPosition !== '') {
        var pieceNames = document.getElementById(rightPosition).childNodes;
        if (pieceNames.length > 0) {
            if (isOpponentPiece(pieceNames[0].id) === true) {
                if (isPawn(pieceNames[0].id) === true) {
                    console.log('opponent piece pawn valid');
                    result = true;
                }
            }
        }
    }

    return result;
}

function isOpponentPiece(pieceName) {
    var result = false;
    if (team === 'brown') {
        if (pieceName[0] === 'P') {
            result = true;
        }
    } else if (team === 'pink') {
        if (pieceName[0] === 'B') {
            result = true;
        }
    }

    return result;
}

function getPositionRight(position) {
    var result = '';
    var brownRows = ['1', '2', '3', '4', '5', '6', '7', '8'];
    var brownColumns = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];
    var pinkRows = ['8', '7', '6', '5', '4', '3', '2', '1'];
    var pinkColumns = ['h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'];
    if (team === 'brown') {
        for (var i = 0; i < brownColumns.length - 1; i++) {         //here length-1 is used to avoid array out of bound exception
            if (position.charAt(0) === brownColumns[i] && brownColumns[i]) {
                result = brownColumns[i + 1] + position.charAt(1);
                break;
            }
        }
    } else if (team === 'pink') {
        for (var i = 0; i < pinkColumns.length - 1; i++) {
            if (position.charAt(0) === pinkColumns[i]) {
                result = pinkColumns[i + 1] + position.charAt(1);
                break;
            }
        }
    }

    return result;
}

function isPawn(pieceName) {
    var isPawn = false;
    if (pieceName.charAt(1) === 'P') {
        isPawn = true;
    }

    return isPawn;
}
//End Enpassant Given 





//castle functions

function isCastle(pieceName, currentPosition) {
    var result = false;
    if (isKing(pieceName) === true) {
        if (isPlayedToCastlingPosition(currentPosition) === true) {
            result = true;
        }
    }
    return result;
}

function moveRockToCastle(currentPosition) {
    if (team === 'brown') {
        moveBrownRockToCastle(currentPosition);
    } else if (team === 'pink') {
        movePinkRockToCastle(currentPosition);
    }
}

function moveBrownRockToCastle(currentPosition) {
    if (currentPosition.id === 'g1') {
        moveRightBrownRockToCastle();
    } else if (currentPosition.id === 'c1') {
        moveLeftBrownRockToCastle();
    }
}

function movePinkRockToCastle(currentPosition) {
    if (currentPosition.id === 'g8') {
        moveLeftPinkRockToCastle();
    } else if (currentPosition.id === 'c8') {
        moveRightPinkRockToCastle();
    }
}

function moveRightBrownRockToCastle() {
    document.getElementById('f1').appendChild(document.getElementById('BRR'));
    if (document.getElementById('h1').hasChildNodes() === true) {
        document.getElementById('h1').removeChild(document.getElementById('BRR'));
    }
}

function moveLeftBrownRockToCastle() {
    document.getElementById('d1').appendChild(document.getElementById('BLR'));
    if (document.getElementById('a1').hasChildNodes() === true) {
        document.getElementById('a1').removeChild(document.getElementById('BLR'));
    }
}

function moveLeftPinkRockToCastle() {
    document.getElementById('f8').appendChild(document.getElementById('PLR'));
    if (document.getElementById('h8').hasChildNodes() === true) {
        document.getElementById('h8').removeChild(document.getElementById('PLR'));
    }
}

function moveRightPinkRockToCastle() {
    document.getElementById('d8').appendChild(document.getElementById('PRR'));
    if (document.getElementById('a8').hasChildNodes() === true) {
        document.getElementById('a8').removeChild(document.getElementById('PRR'));
    }
}

function setCastlingData(pieceName, kingCurrentPositionId, kingPreviousPositionId) {
    createLastMoveObject();
    lastMove.sPecialMove = true;
    lastMove.playedBy.userId = cookie;
    lastMove.toBeRecievedBy.userId = getOpponent();
    lastMove.played = true;
    lastMove.specialMove.cAstle = true;
    lastMove.specialMove.castle.kingName.name = pieceName;
    lastMove.specialMove.castle.rockName.name = getTheCastlingRock(kingCurrentPositionId);
    lastMove.specialMove.castle.kingPreviousPosition.row = kingPreviousPositionId.charAt(1);
    lastMove.specialMove.castle.kingPreviousPosition.column = kingPreviousPositionId.charAt(0);
    lastMove.specialMove.castle.kingCurrentPosition.row = kingCurrentPositionId.charAt(1);
    lastMove.specialMove.castle.kingCurrentPosition.column = kingCurrentPositionId.charAt(0);
    lastMove.specialMove.castle.rockPreviousPosition.row = getCastlingRockPreviousPosition(getTheCastlingRock(kingCurrentPositionId)).charAt(1);
    lastMove.specialMove.castle.rockPreviousPosition.column = getCastlingRockPreviousPosition(getTheCastlingRock(kingCurrentPositionId)).charAt(0);
    lastMove.specialMove.castle.rockCurrentPosition.row = getCastlingRockCurrentPosition(getTheCastlingRock(kingCurrentPositionId)).charAt(1);
    lastMove.specialMove.castle.rockCurrentPosition.column = getCastlingRockCurrentPosition(getTheCastlingRock(kingCurrentPositionId)).charAt(0);
    lastMove.specialMove.castle.rightCastle = isRightCastle(kingCurrentPositionId);
    lastMove.specialMove.castle.leftCastle = isLeftCastle(kingCurrentPositionId);

}
//end of castle functions

//end special move functions






//validity check functions

function isValidMove(currentPosition, pieceName) {
    var nonRestrictedPosition = false;
    var validPiecePosition = false;      
    if (isInRestrictedPiece(pieceName) === true) {
        if (isInRestrictedPieceAndAllowedPosition(pieceName, currentPosition) === true) { //this will look in the restricted piece data array
            nonRestrictedPosition = true;
        }
    } else {
        nonRestrictedPosition = true;       //since this piece is not present in non restricted piece positions so we set this variable true
    }
    if (isValidPiecePosition(pieceName, currentPosition) === true) {  //this will check in next possible moves
        validPiecePosition = true;
    }

    return (nonRestrictedPosition === true && validPiecePosition === true);
}

function isInRestrictedPiece(pieceName) {
    var result = false;
    $.each(restrictedPiece, function(id, value) {
        if (value.restrictedPiece === pieceName) {
            result = true;
        }
    });

    return result;
}

function isInRestrictedPieceAndAllowedPosition(pieceName, currentPosition) {
    var result = false;
    $.each(restrictedPiece, function(id, value) {
        if (value.restrictedPiece === pieceName) {
            $.each(value.allowedPositions, function(id, value) {
                if (value === currentPosition.id) {
                    result = true;
                }
            });
        }
    });

    return result;
}

function isValidPiecePosition(pieceName, currentPosition) {
    var result = false;

    $.each(Pieces, function(id, value) {
        if (id === team) {
            $.each(value, function(id, value) {
                if (value.name === pieceName) {
                    $.each(value.nextPossiblePositions, function(id, value) {
                        if (value === currentPosition.id) {
                            result = true;
                        }
                    });
                }
            });
        }
    });

    return result;
}
//end validity check functions






//piece related functions
function setToPreviousPosition(pieceName, previousPositionId) {
    document.getElementById(previousPositionId).appendChild(document.getElementById(pieceName));
}

function appentPiece(pieceName, ev) {
    if (isPosition(ev.target.id) === true) {
        ev.target.appendChild(document.getElementById(pieceName));
    } else {
        var parent = ev.target.parentNode;
        console.log(parent.id);
        parent.removeChild(ev.target);
        parent.appendChild(document.getElementById(pieceName));
    }
    ev.preventDefault();
}

function isKing(pieceName) {
    var result = false;
    if (pieceName === getKing()) {
        result = true;
    }

    return result;
}

function getKing() {
    var king = '';
    if (team === 'brown') {
        king = 'BK';
    } else if (team === 'pink') {
        king = 'PK';
    }

    return king;
}

function getTheCastlingRock(kingPosition) {
    var rockName = '';
    if (kingPosition === 'g1') {
        rockName = 'BRR';
    } else if (kingPosition === 'c1') {
        rockName = 'BLR';
    } else if (kingPosition === 'g8') {
        rockName = 'PLR';
    } else if (kingPosition === 'c8') {
        rockName = 'PRR';
    }

    return rockName;
}
//end piece related functions






//position related functions

function isPosition(target) {
    var result = true;
    if (target[0] === 'P' || target[0] === 'B') {
        result = false;
    }

    return result;
}

function getPosition(positionOrPiece) {
    var position = '';
    if (isPosition(positionOrPiece.id) === true) {
        position = positionOrPiece;
    } else {
        position = positionOrPiece.parentNode;
    }

    return position;
}

function isPlayedToCastlingPosition(currentPosition) {
    var playedToCastlingPosition = false;

    $.each(getCastlingPositions(), function(id, value) {
        if (value === currentPosition.id) {
            playedToCastlingPosition = true;
        }
    });

    return playedToCastlingPosition;
}

function getCastlingPositions() {
    var castlingPositions = [];
    if (team === 'brown') {
        castlingPositions.push('g1');
        castlingPositions.push('c1');
    } else if (team === 'pink') {
        castlingPositions.push('g8');
        castlingPositions.push('c8');
    }

    return castlingPositions;
}

function getCastlingRockPreviousPosition(rockName) {
    var previousPosition = '';
    if (rockName === 'BRR') {
        previousPosition = 'h1';
    } else if (rockName === 'BLR') {
        previousPosition = 'a1';
    } else if (rockName === 'PRR') {
        previousPosition = 'a8';
    } else if (rockName === 'PLR') {
        previousPosition = 'h8';
    }

    return previousPosition;
}

function getCastlingRockCurrentPosition(rockName) {
    var currentPosition = '';
    if (rockName === 'BRR') {
        currentPosition = 'f1';
    } else if (rockName === 'BLR') {
        currentPosition = 'd1';
    } else if (rockName === 'PRR') {
        currentPosition = 'd8';
    } else if (rockName === 'PLR') {
        currentPosition = 'f8';
    }

    return currentPosition;
}

function isRightCastle(kingCurrentPositionId) {
    var rightCastle = false;
    if (team === 'brown') {
        if (kingCurrentPositionId[0] === 'g') {
            rightCastle = true;
        }
    } else if (team === 'pink') {
        if (kingCurrentPositionId[0] === 'c') {
            rightCastle = true;
        }
    }
    return rightCastle;
}

function isLeftCastle(kingCurrentPositionId) {
    var rightCastle = false;
    if (team === 'brown') {
        if (kingCurrentPositionId[0] === 'c') {
            rightCastle = true;
        }
    } else if (team === 'pink') {
        if (kingCurrentPositionId[0] === 'g') {
            rightCastle = true;
        }
    }

    return rightCastle;
}
//end position related functions






//timer related fucntions
function initializeTime() {
    $.getJSON("GetTimeSettings", {cookie: cookie, tableNumber: tableNumber}, function(data) {
        initializeGameTime(data.gameTime);
        initializeTimePerMove(data.timePerMove);

    });
}

function initializeGameTime(gameTime) {
    
    maximumGameTime = gameTime;
    $('#gameTime').attr("data-timer", gameTime);
    $('#gameTime').TimeCircles({
        start: false,
        animation: "ticks",
        count_past_zero: false,
        use_background: true,
        direction: "Counter-clockwise",
        time: {
            Days: {show: false},
            Hours: {color: "#C0C8CF"},
            Minutes: {color: "#999"},
            Seconds: {color: "#C0C8CF"}
        }
    });
}

function initializeTimePerMove(timePerMove) {
    maximumTimePerMove = timePerMove;
    $('#timePerMove').attr("data-timer", timePerMove);
    $('#timePerMove').TimeCircles({
        start: false,
        animation: "ticks",
        count_past_zero: false,
        use_background: true,
        direction: "Counter-clockwise",
        time: {
            Days: {show: false},
            Hours: {color: "#C0C8CF"},
            Minutes: {color: "#999"},
            Seconds: {color: "#C0C8CF"}
        }
    }).addListener(function(unit, value, total) {
        if (total === 0)
        {
            console.log("timer expired listener");
            timerExpired();
        }
    });
}

function timerExpired() {
    console.log("timer expired function");
    if (isMyControl() === true) {
        console.log("timer expired function");
        setSkipMoveData();
        //resetTimePerMove();
        //stopTimePerMove();
        //disableNativeControl();  testing purpose commented
        $.post('PlayerMove', {userId: cookie, lastMove: JSON.stringify(lastMove), team: team, tableNumber: tableNumber}, function(data) {
            setCurrentPlayerControl(data.currentPlayerControl);
            reInitializeTimePerMove(data.timePerMove);
            //visualizeCurrentPlayerControl();
            console.log('current Player control : ' + data.currentPlayerControl);
            console.log('time per move received : ' + data.timePerMove);
            console.log('team : ' + team);
        }, 'json');
    }
}

function reInitializeTimePerMove(timePerMove) {
    $('#timePerMove').TimeCircles().destroy();
    initializeTimePerMove(timePerMove);
    $('#timePerMove').TimeCircles().start();
}

function restartTimePerMove(timePerMove) {
    setTimePerMove(timePerMove);
    $('#timePerMove').TimeCircles();
}

function stopTimePerMove() {
    $('#timePerMove').TimeCircles().stop();
}

function resetTimePerMove() {
    restartTimer();
}

function setSkipMoveData() {
    setElementryData();
}

function startTimer() {
    $('#gameTime').TimeCircles().start();
    $('#timePerMove').TimeCircles().start();
}

function restartTimer() {
    $('#timePerMove').TimeCircles().restart();
}

function isValidGameTime() {
    var result = false;

    if ($('#gameTime').TimeCircles().getTime() > 0) {
        result = true;
    }

    return result;
}

function isValidTimePerMove() {
    var result = false;
    if ($('#timePerMove').TimeCircles().getTime() > 0) {
        result = true;
    }

    return result;
}

function setTimePerMove(timePerMove) {
    $('#timePerMove').attr("data-timer", timePerMove);
}

//end of timer related functions







//player list and chat window functions

function initializePlayerListAndChatWindow() {
    $.getJSON("ChessBoardServlet", {cookie: cookie, tableNumber: tableNumber}, function(data) {
        initializePlayerListWindow(data);
        initializeChatWindow();
    });
}

function initializePlayerListWindow(data) {
    var playerTableData = "<tr id='tableHeader'><th>Player ID</th><th>Rating</th></tr>";
    $.each(data.player, function(key, value) {
        console.log(value);
        playerTableData = playerTableData + "<tr class='tableList'><td>" + value.userId + "</td><td>" + value.rating + "</td></tr>";
    });
    $("#playerData").html(playerTableData);
}

function initializeChatWindow() {
    var chatWindowData = "<span class='joinBoardMessage'> Welcome to the Chess Board!</span><br>";
    $("#chatWindow").html(chatWindowData);
}

function sendMessage() {
    var chatWindowData;
    var inputTextData;
    var sendMessage;
    $("#send").click(function() {
        chatWindowData = $("#chatWindow").html();
        inputTextData = $("#inputText").val();
        if(inputTextData !== ''){
            chatWindowData = chatWindowData + "<br>" + "<span class='sender'>" + cookie + ": </span>" + inputTextData;
            $("#chatWindow").html(chatWindowData);

            $("#chatWindow").scrollTop(document.getElementById("chatWindow").scrollHeight);
            sendMessage = "<span class='receiver'>" + cookie + ": </span>" + inputTextData;
            $("#inputText").val("");

            $.post("SendMessageChessBoard", {message: sendMessage, userId: cookie, tableNumber: tableNumber});
        }
    });

    $('#inputText').keypress(function(event) {
        if (event.keyCode === 10 || event.keyCode === 13) {
            $('#send').click();
        }
    });
}
//end of player list and chat window functions







//player control related functions

function visualizeCurrentPlayerControl() {
    $.each($('.tableList > td'), function(id, value) {
        if (currentPlayerControl === $(this).html()) {
            $(this).css('background-color', 'green');
        }
    });
}

function disableNativeControl() {
    if (team === 'pink') {
        $('.pink').attr('draggable', 'false');
    } else if (team === 'brown') {
        $('.brown').attr('draggable', 'false');
    }
}

function setCurrentPlayerControl(playerName) {
    currentPlayerControl = playerName;
}

function isMyControl() {
    var isMyControl = false;

    if (currentPlayerControl === cookie) {
        isMyControl = true;
    }

    return isMyControl;
}

//end of player control related functions






//global variable related functions

function setGameStarted(boolean){
    gameStarted = boolean;
}

function isHost(){
    var isHost = false;
    
    if(team === 'pink'){
        isHost = false;
    }else if(team === 'brown'){
        isHost = true;
    }
    
    return isHost;
}

function getPerson() {
    var person = null;
    var temp = function() {
        var a = window.location.href,
                b = a.lastIndexOf("/");
        return a.substr(b + 1);
    }();

    if (temp === 'chess_board_host.html') {
        person = 'host';
    } else if (temp === 'chess_board_player.html') {
        person = 'player';
    }

    return person;
}

function getTeam(person) {
    var team = null;
    if (person === 'host') {
        team = 'brown';
    } else if (person === 'player') {
        team = 'pink';
    }

    return team;
}

function createLastMoveObject() {
    var stringLastMove = '{"simpleMove":{"pieceMoved":{"name":""},"previousPosition":{"row":"","column":""},"currentPosition":{"row":"","column":""},"nativeNextPossiblePositions":{},"opponentNextPossiblePositions":{},"nativeReturnSet":false,"opponentReturnSet":false},"pieceBeaten":{"pieceBeaten":{"name":""},"beatenBy":{"name":""},"beatingPiecePreviousPosition":{"row":"","column":""},"beatingPieceCurrentPosition":{"row":"","column":""},"beatenPiecePosition":{"row":"","column":""},"nativeNextPossiblePositions":{},"opponentNextPossiblePositions":{},"nativeReturnSet":false,"opponentReturnSet":false},"specialMove":{"eNPassantPlayed":false,"cAstle":false,"pAwnUpgrade":false,"eNPassantGiven":false,"pawnUpgrade":{"pawnName":{"name":""},"upgradedPiece":{"name":""},"pawnPreviousPosition":{"row":"","column":""},"pawnCurrentPosition":{"row":"","column":""},"upgradedPiecePosition":{"row":"","column":""}},"castle":{"kingName":{"name":""},"rockName":{"name":""},"kingPreviousPosition":{"row":"","column":""},"kingCurrentPosition":{"row":"","column":""},"rockPreviousPosition":{"row":"","column":""},"rockCurrentPosition":{"row":"","column":""},"rightCastle":false,"leftCastle":false},"enPassantPlayed":{"beatenPawnName":{"name":""},"beatingPawnName":{"name":""},"beatenPawnPosition":{"row":"","column":""},"beatingPawnPreviousPosition":{"row":"","column":""},"beatingPawnCurrentPosition":{"row":"","column":""}},"enPassantGiven":{"nativePlayer":{"userId":""},"opponentPlayer":{"userId":""},"nativeEnPassantPiece":{"name":""},"nativePiecePreviousPosition":{"row":"","column":""},"nativePieceCurrentPosition":{"row":"","column":""},"rightOpponentEnPassantPiece":false,"leftOpponentEnPassantPiece":false,"opponentRightEnPassantPiece":{"name":""},"opponentLeftEnPassantPiece":{"name":""},"opponentRightPiecePreviousPosition":{"row":"","column":""},"opponentRightPieceNextPossiblePosition":{"row":"","column":""},"opponentLeftPiecePreviousPosition":{"row":"","column":""},"opponentLeftPieceNextPossiblePosition":{"row":"","column":""},"enPassantExpired":true},"nativeNextPossiblePositions":{},"opponentNextPossiblePositions":{},"nativeReturnSet":false,"opponentReturnSet":false},"check":{"kingUnderCheck":{"name":""},"checkGivenBy":[null,null],"kingPosition":{"row":"","column":""},"checkGivenPosition":[null,null],"opponentKingNextPossiblePosition":{},"allowedPiecesOfOpponentWithAllowedPositions":{}},"checkMate":{"checkMateBy":{"userId":""},"checkMateTo":{"userId":""}},"sImpleMove":false,"pIeceBeaten":false,"sPecialMove":false,"cHeck":false,"cHeckMate":false,"playedBy":{"userId":""},"toBeRecievedBy":{"userId":""},"played":false,"recieved":false,"moveSkipped":false,"hostBlockedPieces":false,"clientBlockedPieces":false}';
    lastMove = JSON.parse(stringLastMove);
}

function getOpponent() {
    var opponent = '';
    var temp = '';
    $('.tableList > td').each(function() {
        temp = $(this).html();
        if (isNaN(temp) === true) {
            if (temp !== cookie) {
                console.log(temp);
                opponent = temp;
            }
        }
    });
    return opponent;
}
//end global variable related functions






//invitation List functions

function loadPlayersInInvitationList(userId){

    $.getJSON('GetCompetitors', {userId: userId, tableNumber: tableNumber}, function(data){
        var competitorsList = '';
        competitorsList = $('#competitorList table').html();
        
        for(var i in data){
            competitorsList = competitorsList + "<tr><td class='competitorId'>" + i + '</td><td>' + data[i] + '</td></tr>';
        }
        
        $('#competitorList table').html(competitorsList);
    });
}

function sendProposal(){
    
    var invitation = $('#selectedCompetitor').html();
    if(invitation === null || invitation === ''){
        alert("Please select A Competitor First");
    }else{
        var invitedId = $('#selectedCompetitor').children('.invitation').attr('data-invitedId');
        $.post('SendRequest', {userId: cookie, tableNumber: tableNumber, invitation: invitation, invitedId: invitedId});
        $('#sendRequest').attr('disabled', true);
    }
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
       $('#sendRequest').attr('disabled', false);
    });
}

//end of invitation List functions







//enable/disable chat function
function enableDisableChat(){
    
    $('#enableDisableChat > button').click(function(){
        revertChessBoardStatusVisually(this);
        $.post('EnableDisableChat', {userId: cookie, tableNumber: tableNumber}, function(data){
            if(data.status === false){
                revertChessBoardStatusVisually();
            }
        });
    });
}

function revertChessBoardStatusVisually(pointer){
    if($(pointer).html() === 'Disable Chat'){
        $(pointer).html('Enable Chat');
        $('#inputText').attr('disabled', true);
        $('#send').attr('disabled', true);
    }else if($(pointer).html() === 'Enable Chat'){
        $(pointer).html('Disable Chat');
        $('#inputText').attr('disabled', false);
        $('#send').attr('disabled', false);
    }
}

//end of enable/disable chat functions






//boot player functions

function bootPlayer(){
    $('#bootPlayer').click(function(){
        if($('#checkMate').is(':visible') === true){
            gameStarted = false;
        }
        if(gameStarted === false){
            $.post('BootPlayer', {userId: cookie, tableNumber: tableNumber});
            $('#startGame').attr('disabled', false);
            stopAllTimes();
            disablePieces();
        }else{
            alert('Game has been started now. Finish the game first');
        }
    });
}

function exitBoard(){
    $('#exitBoard').click(function(){
        if(confirm('Are you sure you want to exit the Chess Board') === true){
            $.post('ExitBoard', {userId: cookie, tableNumber: tableNumber}, function(data){
                if(data.exitDone === true){
                    window.close();
                }
            });
        }
    });
}

//end of boot player functions





//button functions

function enableStartGame(){
    $('#startGame').attr('disabled', false);
}

function disableBootPlayer(){
    $('#bootPlayer').attr('disabled', true);
}

function enableSendRequest(){
    $('#sendRequest').attr('disabled', false);
}

function disableStartGame(){
    $('#startGame').attr('disabled', true);
}

function enableBootPlayer(){
    $('#bootPlayer').attr('disabled', false);
}

function disableSendRequest(){
    $('#sendRequest').attr('disabled', true);
}
//end button functions