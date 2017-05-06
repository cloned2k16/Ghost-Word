<!DOCTYPE html>
 <html>
  <head>
   <meta charset="UTF-8">
   <title>~_+ Ghost Word +_~</title>
   <link rel=stylesheet     href=css/style.css              >
   <script                  src =js/masonry.pkgd.min.js     ></script>
   <script                  src =js/jquery-1.10.2.min.js    ></script>       
   <script                  src =js/yaoo.js                 ></script>
   <script                  src =js/main.js                 ></script>
   <script                  src =js/main.init.js            ></script>      
 </head>
  <body> 
   <div id=main class=mainContainer>
    <h2 class="title animated flipInY">.-_ Ghost Word _-.</h2>
    <div id="statusBar" class="sBar dontStop bounceIn" >press 'new game'</div>
    <div id="theWord"  
        class=gWord>
     <div id=currWord class="rCHs js-masonry"
         data-masonry-options='{ "columnWidth": 26, "itemSelector": ".rCH" }'
     >
     <div class="rCH"></div><div class="rCH"></div><div class="rCH"></div><div class="rCH"></div>
     <div class="rCH"></div><div class="rCH"></div><div class="rCH"></div><div class="rCH"></div>
     <div class="rCH"></div><div class="rCH"></div><div class="rCH"></div><div class="rCH"></div>
     <div class="rCH"></div><div class="rCH"></div><div class="rCH"></div><div class="rCH"></div>
     <div class="rCH"></div><div class="rCH"></div><div class="rCH"></div><div class="rCH"></div>
     <div class="rCH"></div><div class="rCH"></div><div class="rCH"></div><div class="rCH"></div>
    </div>
   </div> 
    <div id="keyboard" 
         class="letters animated flipInX js-masonry"
         data-masonry-options='{ "columnWidth": 64, "itemSelector": ".ch" }'>
     <div class="ch">Q</div><div class="ch">W</div><div class="ch">E</div><div class="ch">R</div><div class="ch">T</div><div class="ch">Y</div><div class="ch">U</div><div class="ch">I</div><div class="ch">O</div><div class="ch">P</div>
     <div class="ch">A</div><div class="ch">S</div><div class="ch">D</div><div class="ch">F</div><div class="ch">G</div><div class="ch">H</div><div class="ch">J</div><div class="ch">K</div><div class="ch">L</div><div class="ch">&nbsp;</div>
     <div class="ch">&nbsp;</div><div class="ch">Z</div><div class="ch">X</div><div class="ch">C</div><div class="ch">V</div><div class="ch">B</div><div class="ch">N</div><div class="ch">M</div><div class="ch">&nbsp;</div><div class="ch">&nbsp;</div>
   </div>
    <div ><table height=20 width=100% border=0><tr></tr></table></div>
    <div class="js-masonry"
         data-masonry-options='{ "columnWidth": 160, "itemSelector": ".newG" }'>
         <button id=newGame class="newG">new game</button>
   </div>
    <div id=copyR class="copyR animated flipInY">Ghost Word &copy; 2013 by Paolo Lioy</div>
  </div> 
 </body>
</html>