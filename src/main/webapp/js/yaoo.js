  var d                                 = {};
// ------------------------------------ --------------------------- ----------------------------------------------------
  d.doc                                 = document;
// ------------------------------------ --------------------------- ----------------------------------------------------
  d.out                                 = function                  (msg)   {  this.doc.write(msg + '<hr>');            };
// ------------------------------------ --------------------------- ----------------------------------------------------
  d.log                                 = function                  ()      { if (console.log) console.log.apply(console,arguments); };
// ------------------------------------ --------------------------- ----------------------------------------------------
  d.warn                                = function                  (o)     {  console.warn     ('WARNING: '    , o);           };
// ------------------------------------ --------------------------- ----------------------------------------------------
  d.info                                = function                  (o)     {  console.info     (">>> "         , o);           };
// ------------------------------------ --------------------------- ----------------------------------------------------
  d.err                                 = function                  (o)     {  console.error    ('ERROR: '      , o);           };
// ------------------------------------ --------------------------- ----------------------------------------------------
// ------------------------------------ --------------------------- ----------------------------------------------------
  var yaoo                              = yaoo              || {};    
  var console                           = console           || {}; // IE
// ------------------------------------ --------------------------- ----------------------------------------------------
  var warn                              = function                  (msg)                                               {     
         if (d.warn)        d.warn          (msg);
    else if (console.warn)  console.warn    (msg);
    else                    alert('WARNING' +msg);
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  var log                               = function                  (msg)                                               {     
         if (d.log)         d.log.apply     (this,arguments);
    else if (console.log)   console.log     (msg);
    else ;//WTF!!
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  var toString                          = function                  (o)                                                 {
   var s = '[ ' + typeof o + ': ';
   for (m in o) s += ',' + m + ' = ' + o[m];
   return s + ' ]';
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  var xEach                             = function                  (A, fea)                                            {
   if (typeof fea != "function") { throw new TypeError('not a function: '+fea);   }
   else for (var i in A)  fea(A[i], i, A);
  };
// ------------------------------------ ------------------------------------------------------------
  var _chckIE                           = function                  ()                                                  {
   _isIE = navigator.appName == 'Microsoft Internet Explorer';
   _isIEv = (/MSIE (\d+\.\d+);/.test(navigator.userAgent));
   _IEver = new Number(RegExp.$1);
   log('is IE> ' + _isIEv + ' ver.' + _IEver);
   return _isIEv;
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  var imIE                              = _chckIE();
  var baseURI                           = document.scripts[0].baseURI; ;
// ------------------------------------ --------------------------- ----------------------------------------------------
  var nEl                               = function                  (name)                                              {
   return document.createElement(name);
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  var aEl                               = function                  (pare, ele)                                         {
   pare.appendChild(ele);
   return ele;
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  var showAll                           = function                  (oo)                                                {
   var s = '[' + oo + ']..';
   for (p in oo) s += ' ,' + p + '=' + p.value;
   return s;
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  yaoo.myName                           = function                  (a)                                                 { 
   return a.callee.name;
  }
// ------------------------------------ --------------------------- ----------------------------------------------------
  yaoo.addHScript                       = function                  (src, cb)                                           {
    var d = document;
    var h = d.getElementsByTagName("head")[0];
    var s = d.createElement('script');
    s.type = 'text/javascript';
    s.src = src;
    if (imIE) {
      s.onreadystatechange = function () {
        if (s.readyState == 'complete'
           || s.readyState == 'loaded') {
          s.onreadystatechange = "";
          if (cb)
            setTimeout(cb, 30);
        }
        else log('skipping: '+s.readyState);
      };
    } else
    s.onload = cb;
    h.appendChild(s);
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  yaoo.addCss                           = function                  (src, cb)                                           {
    var _   = document;
    var h   = _.getElementsByTagName("head")[0];
    var s   = _.createElement('link');
    s.type  = 'text/css';
    s.rel   = 'stylesheet';
    s.href  = src;
    if (cb) s.onload = cb;
    h.appendChild(s);
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  yaoo.ByI                              = function                  (id)                                                {  
   return document.getElementById(id); 
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  yaoo.decodeAndpers                    = function                  (src)                                               {
    var dv=document.createElement('DIV');
    dv.innerHTML = src;
    return dv.firstChild.nodeValue;
  };
// ------------------------------------ --------------------------- ----------------------------------------------------
  var c4ll3r                            = function                  ()                                                  { 
      return arguments.callee.caller.arguments.callee.caller; 
  };  
//------------------------------------ --------------------------- ----------------------------------------------------
  var getParameterByName                = function                  (name)    {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
        var regexS = "[\\?&]" + name + "=([^&#]*)";
        var regex = new RegExp(regexS);
        var results = regex.exec(window.location.search);
        if(results == null)
          return "";
        else
          return decodeURIComponent(results[1].replace(/\+/g, " "));
  };
//------------------------------------ --------------------------- ----------------------------------------------------
