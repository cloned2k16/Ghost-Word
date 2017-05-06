<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
  <head>
   <meta charset="UTF-8"><!-- HTML5 -->
   <title>Game Engine Test</title>
   <link rel=stylesheet     href=../css/style.css               >
   <script                  src =../js/masonry.pkgd.min.js      ></script>
   <script                  src =../js/jquery-1.10.2.min.js    ></script>       
   <script                  src =../js/yaoo.js                  ></script>
   <script                  src =../js/main.js                  ></script>
   <script><!--
   
    //  ------------------- ------------------------------------------- --------------------------------
        function            playWord                                    (w) {
         _$.log (yaoo.myName(arguments),w);
         var msg = mkAjaxMsg(state.sessionID,'test',w);
         sendMessage(msg);   
        };
    //  ------------------- ------------------------------------------- --------------------------------
    //  onLoad ...
    $(function() {
        //overriding config
        config              .servletURL     = '/ghost-word/rest/GHOST/';
        outT                = yaoo.ByI('rsltBox');
        outT.value='here we go';
        
        //Override
        function            testHandleResponse                          (resp)                          { _$.log (yaoo.myName(arguments),resp);
         var msg = parseResponseMessage(resp);
         _$.log('msg: ',msg);
         if (msg.status == answr.NEW_GAME   ) config.handleResponse_(resp);
         outT.value=resp;
         _$.log('response: ',msg);
        }       
        config.handleResponse_= config.handleResponse;
        config.handleResponse = testHandleResponse;
        $('#playW').on('click',function (e) { 
         playWord(yaoo.ByI('word').value);
        });
        
        $('#newB').on('click',function (e) { 
         onNewGamePressed();
        });
    });
//  ------------------- ------------------------------------------- --------------------------------

   // -->
  </script>
 </head>
  <body>
   <div id=main class=mainContainer>
    <button id=newB     class=newG>newGame</button>
    <input  id=word     type=text length=28></nput>
    <button id=playW    class=newG>play word</button>
    <br>
    <textarea id=rsltBox class=resBox ></textarea>
  </div>
 </body>
</html>