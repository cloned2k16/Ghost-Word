//  ------------------- ------------------------------------------- --------------------------------
//  onLoad ...
    $(function() {
    
        $('.'+config.keysClass).on("click", function(e) { 
         var ch=$( this ).html();
         pressFx($(this),5,44,33);
         if (_In('AZ',ch))                                                                              // only listen to valid letters
                           onKeyPressed(ch); 
        });
        
        $('#newGame').on('click',function (e) { 
         pressFx($(this),8,136,30);
         onNewGamePressed();
        });
        
        showCurrentWord('');
    });
//  ------------------- ------------------------------------------- --------------------------------
