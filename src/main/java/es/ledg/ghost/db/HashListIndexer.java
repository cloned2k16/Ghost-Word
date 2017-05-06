package es.ledg.ghost.db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

import es.ledg.BaseObj;

public class                            HashListIndexer 
extends                                 BaseObj                     {
    private 
     Hashtable <String,HashNodeList>    validWords  = new Hashtable<String,HashNodeList>();
    private 
     Hashtable <String,HashNode>        shortWords  = new Hashtable<String,HashNode>    ();
    private 
     LinkedList<HashNode>               allWords    = new LinkedList<HashNode>          ();
    private 
     LinkedList<String>                 validPfx    = new LinkedList<String>            ();
    
    
    private int                         minNumChar  ;   
    private int                         numWords    ;
    private int                         numShort    ;
    private int                         maxWordLength;
    private String                      maxWordLengthS;
    
    private int                         maxPfxLen;

    public                              HashListIndexer             (int chMin)                 {
     minNumChar = chMin;
     maxPfxLen  = 4;
     numWords   = 0;
     numShort   = 0;
    }
     
    private void                        addWord_                    (String pfx,HashNode node)      {
     if (validWords.containsKey(pfx)) {
      HashNodeList nodeList = validWords.get(pfx);
      nodeList.add(node);
     }
     else validWords.put(pfx,new HashNodeList(pfx,node));
     validPfx.add(pfx);
    }
    
    public void                         addWord                     (String w)                  {
     w=w.toUpperCase();             //words tu uppercase
     int len=w.length();
     if (len>maxWordLength){
         maxWordLength=len;
         maxWordLengthS=w;
     }
     
     HashNode node=new HashNode(w); //wrap each word
     allWords.add(node);            //add it to complete list index
         
     if (len<minNumChar) {          // for short word we only need to check if exist ...
      shortWords.put(w,node);
      numShort++;
      return;
     }
     
     for (int i=1; i<=maxPfxLen; i++){ 
      if (len>=i) addWord_(w.substring(0,i),node); 
     }
     numWords++;
    }

    public void                         saveDictionaryFor           (String pfx) 
            throws                      IOException                                                 {
        HashNodeList list=validWords.get(pfx);
        if (null!=list){
            FileWriter fileWriter   = new FileWriter("dictionaryFor_"+pfx+".txt");
            BufferedWriter fo       = new BufferedWriter(fileWriter);
            fo.write("num words: "+list.size());
            log.dbg("dictionary for '%s' .size() =%d",pfx,list.size());
            list.sortByLen();
            int odds [] = {0,0,0,0} ; //warning this will fail with a bigger number of players!!
            for (HashNode wn:list.list){
             String txt=String.format("[%s",wn);
             odds [wn.looserIs()-1]++;
            //log.dbg(txt);
             fo.write(txt+"\r\n");
            }
            int n=0;
            for (int odd:odds) fo.write("\r\nodds["+(++n)+"] = "+odd);
            fo.close();
        }
        else log.dbg("there are no words for [%s] prefix..", pfx);
    }
    
    public void                         showRootList                () 
            throws                      IOException                                                 {
        log.dbg("total added words %d : %d", numWords,numShort);
        log.dbg("max word length: %d is %s",maxWordLength,maxWordLengthS);
        int numPrefixes=0;
        int    maxDeep      [] = new int    [maxPfxLen] ;
        String deeperPfx    [] = new String [maxPfxLen] ;
        FileWriter fileWriter=null;
        fileWriter = new FileWriter("dictionary report.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        log.dbg("num prefixes: %d", validPfx.size());       
        for (String pfx : validPfx) {
         HashNodeList list=validWords.get(pfx);
         if (null!=list) {
          numPrefixes++;
          int numChildren=list.size();
          int pfxIdx=pfx.length()-1;
          if (numChildren>maxDeep[pfxIdx]) {
                          maxDeep[pfxIdx]=numChildren;
           deeperPfx[pfxIdx] = pfx;
          }
          String txt=String.format("[%s] : %s", pfx  ,list.toString());
          bufferedWriter.write(txt+"\r\n");
         }
        }
        bufferedWriter.close();
        log.dbg("numPrefixes: %d ",numPrefixes);
        for (int i =0; i<maxPfxLen; i++){
            log.dbg("pfx[%d] deep: %5d is: [%-4s]",i,maxDeep[i],deeperPfx[i]);
        }
        
    }
    

};
