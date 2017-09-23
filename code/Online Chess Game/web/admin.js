/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
    loadUserIds();
    doOperation();
    signOut();
});

function signOut(){
    $('#header > table > tbody > tr > td:nth-child(3)').click(function(){
        window.location.href = "index.html";
    });
}

function loadUserIds(){
    var blockDo = '';
    
    $.getJSON('GetUserIds', {userId: 'admin'}, function(data){
        var leftTableHtml = "<tr><th>UserID</th></tr>";
        var rightTableHtml = "<tr><th colspan='3'>Operation</th></tr>";
        $(data).each(function(id, value){
            leftTableHtml = leftTableHtml + '<tr><td>' + value.userId + '</td></tr>';
            if(value.blockedAccount === true){
                blockDo = 'Unblock';
            }else if(value.blockedAccount === false){
                blockDo = 'Block';
            }
            rightTableHtml = rightTableHtml + '<tr><td class="operation">' + blockDo + '</td><td>|</td><td class="operation">delete</td></tr>';
        });
        $('#adminTable > table > tbody > tr > td:nth-child(1) > table > tbody').html(leftTableHtml);
        $('#adminTable > table > tbody > tr > td:nth-child(2) > table > tbody').html(rightTableHtml);
    });
}

function doOperation(){
    $('#adminTable > table > tbody > tr > td:nth-child(2) > table').on('click', '.operation', function(){
        
        var clickedUserId = $('#adminTable > table > tbody > tr > td:nth-child(1) > table > tbody > tr:nth-child(' + ($(this).parent('tr').index() + 1) + ') > td').html(); 
        var operation = '';
        
        if($(this).html() === 'Block'){
            operation = 'block';
        }else if($(this).html() === 'Unblock'){
            operation = 'unblock';
        }else if($(this).html() === 'delete'){
            operation = 'delete';
        }
        
        $.post('DoAdminOperation', {userId: 'admin', clickedUserId: clickedUserId, operation: operation}, function(data){
            if(data === true){
                loadUserIds();
            }
        });
    });
}