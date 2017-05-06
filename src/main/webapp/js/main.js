    _$                  = console || {};                                                                // IE!
//  ------------------- ------------------------------------------- --------------------------------
    var config          = {};
    config              .servletURL     = '/ghost-word/rest/GHOST/';     
    config              .keysClass      = 'ch';
//  ------------------- ------------------------------------------- --------------------------------
    var state           = {
        OFFLINE     : 0     // should ask for new game first
    ,   PLAY_TURN   : 1     // we can send our move here
    ,   WAIT_MOVE   : 2     // opponents turn usually the server turn if just 2 players
    ,   GAME_OVER   : 66    // game finished 
    };
    
    state.currentWord   = '';
    state.sessionID     = -1;                                                                           // so we can play even with cookies disabled
    state.status        = state.OFFLINE;
    state.lastQTime     = {
      time:             -1
     ,now:              function () { this.time=Date.now(); }
     ,ellpsd:           function () { return Date.now()-this.time; }
    };
    
    var answr           = { 
            PLAYING         :   'PLAY'
        ,   NEW_GAME        :   'NEW_GAME'
        ,   YOU_WIN         :   'YOU_WIN' 
        ,   YOU_LOSE        :   'YOU_LOSE_BY_WORD_MATCH'
        ,   UNKNOWN_WORD    :   'YOU_LOSE_UNKNOWN_WORD'
        ,   ERROR           :   'ERROR'
        };
        
//  ------------------- ------------------------------------------- --------------------------------
    function            delayedShowStatus                           (msg,to)                        {
     setTimeout((function(a){return function(){ showStatus(a);}})(msg),to);
    }
//  ------------------- ------------------------------------------- --------------------------------
    function            delayedShowCurrentWord                      (to)                            {   _$.log (yaoo.myName(arguments),state.currentWord);
      setTimeout((function(a){return function(){ showCurrentWord(a);}})(state.currentWord),to);
    }
//  ------------------- ------------------------------------------- --------------------------------
    _In                 = function      (rng,ch)                    {  return ((ch>=rng.charAt(0)) && (ch<=rng.charAt(1))); };
//  ------------------- ------------------------------------------- --------------------------------
    function            parseResponseMessage                        (resp)                          {
     var msg;
     try { msg=$.parseJSON( resp ); }
     catch (e){ _$.log(e); }
     return msg;
    }
//  ------------------- ------------------------------------------- --------------------------------
    function            handleComputerMove                          (resp)                          {   _$.log (yaoo.myName(arguments),resp);
     var msg = parseResponseMessage(resp);
     
     var time=state.lastQTime.ellpsd();
     var delay=666-time; 
       
     if (msg){
            if (msg.status == answr.ERROR       ){
        showStatus          ('--- ERROR! ---');
        delayedShowStatus   (msg.message    ,666);
      }
      else  if (msg.status == answr.YOU_WIN   ){
        showCurrentWord(msg.message);
        showStatus          ('^_^ YOU WIN ^_^');
      }
      else  if (msg.status == answr.YOU_LOSE){
        showCurrentWord(msg.message);
        showStatus          ('^_^ YOU LOSE ^_^');
      }
      else  if (msg.status == answr.UNKNOWN_WORD ){
        showCurrentWord(msg.message);
        showStatus          ('^_^ YOU LOSE ^_^');
        delayedShowStatus   ('^_^ UNKNOWN WORD ^_^'     ,666);
      }
      else  if (msg.status == answr.PLAYING     ){
       state.currentWord+=msg.currentWord;
       _$.log("::",msg.message);
       showStatus(msg.message);
       delayedShowCurrentWord(delay<0?0:delay);                                                         // some animation for to fast connections ..
      }
      else  if (msg.status == answr.NEW_GAME    ){
       state.sessionID = msg.sessionID;
       state.status    = state.PLAY_TURN;                                                               // unlock keyboard
       showCurrentWord(msg.currentWord);
       showStatus("New Game");
       setTimeout((function(a){return function(){ showStatus(a);}})(msg.message),333);
      }
      else {
       _$.log(msg.status);   
       showStatus('unexpected status: '+msg.status);
      }
     }
    };
//  ------------------- ------------------------------------------- --------------------------------
    function            handleError                                 (jXHR,sCode,err)                    {   
        _$.log (yaoo.myName(arguments),arguments);
     var time=state.lastQTime.ellpsd();
    }
//  ------------------- ------------------------------------------- --------------------------------
    function            handleAjaxCompleted                         ()                              {   _$.log (yaoo.myName(arguments));
     //TODO: player switch animation ...
     
     //unlock player keyboard
     if (state.status == state._WAIT_MOVE) state.status = state.PLAY_TURN;
    };
//  ------------------- ------------------------------------------- --------------------------------
    function            showStatus                                  (msg)                           {   _$.log (yaoo.myName(arguments),msg);
     $('#statusBar').html('');
     $('#statusBar').html(msg);
    }
//  ------------------- ------------------------------------------- --------------------------------
    function            showCurrentWord                             (word)                          {   _$.log (yaoo.myName(arguments),word);
     state.currentWord=word;
     var n=0;
     var len=word.length;
     $('.rCH').each(function (e) {
      
      if (n<len) {
       $(this).html(word.charAt(n++));
       $(this).show();
      }
      else       $(this).hide();
     });
    }
//  ------------------- ------------------------------------------- --------------------------------
    function            mkAjaxMsg                                   (sID,mv,ch)                     {   _$.log (yaoo.myName(arguments),sID,mv,ch);
     var msg = {};
         msg.sess       = sID;
         msg.move       = mv;
         msg.char       = ch;
         return msg;
    }
//  ------------------- ------------------------------------------- --------------------------------
    function            playWithServer                              (ch)                            {   _$.log (yaoo.myName(arguments),ch);
     var msg = mkAjaxMsg(state.sessionID,'play',ch);
     sendMessage(msg);   
    }

    function            playCh                                      (ch)                            {   _$.log (yaoo.myName(arguments),ch);
          if (state.status == state.PLAY_TURN) {
      showCurrentWord(state.currentWord+ch);
      playWithServer(state.currentWord);
     }
     else if (state.status == state.WAIT_MOVE){
      alert("you should wait your turn!");
     }
     else if (state.status == state.OFFLINE){
      alert("you should press new game first! \nor wait for the server to tell you is ready ...");
     }
     else {
      alert("unexpected game status: "+state.status);
     }
    };

    function            onKeyPressed                                (ch)                            {
     playCh(ch);    
    }
//  ------------------- ------------------------------------------- --------------------------------
    function            askForNewGame                               ()                              {   _$.log (yaoo.myName(arguments));
     var msg = mkAjaxMsg(state.sessionID,'new','');
     sendMessage(msg);
    }

    function            onNewGamePressed                            ()                              {
     askForNewGame();
    }
//  ------------------- ------------------------------------------- --------------------------------
//  ------------------- ------------------------------------------- --------------------------------
//  ------------------- ------------------------------------------- --------------------------------
    function            sendMessage                                 (msg)                           {   _$.log (yaoo.myName(arguments),msg);
     $.ajax({
        type            :   "POST"
      , url             :   config.servletURL
      , data            :   msg
      , context         :   document.body
      , crossDomain     :   true
     })
     .done  (function(resp)         { config.handleResponse (resp);         })
     .fail  (function(jXHR,s,e)     { handleError           (jXHR,s,e);     })
     .always(function()             { handleAjaxCompleted   ();             })
     ;
     state.lastQTime.now();
    }
//  ------------------- ------------------------------------------- --------------------------------
    function            unpressFx                                   (e)                             {
     e.css('margin' ,e._m_);
     e.css('width'  ,e._w_);
     e.css('height' ,e._h_);
    }
//  ------------------- ------------------------------------------- --------------------------------
    function            pressFx                                     (e,m,w,h)                       {
     e._m_=e.css('margin');
     e._w_=e.css('width');
     e._h_=e.css('height');
     e.css('margin' ,m+'px');
     e.css('width'  ,w+'px');
     e.css('height' ,h+'px');
     setTimeout((function(a){return function(){ unpressFx(a); };})(e),200);
    }
//  ------------------- ------------------------------------------- --------------------------------
    config.handleResponse = handleComputerMove;
