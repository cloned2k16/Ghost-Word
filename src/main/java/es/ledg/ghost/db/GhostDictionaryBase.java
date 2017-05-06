package es.ledg.ghost.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import es.ledg.BaseObj;



/**
 * This class provides a minimum common behavior implementation to any possible Dictionary Implementation
 * to implement any customized indexing dictionary you have to extend this class 
 * the class handles already the opening of the RAW dictionary file and the optimization of reducing it
 * by removing unwanted words the it will call {@link #addWord(String)} to pass any 'good' word
 * @author Paolo Lioy
 *
 */
public abstract class                   GhostDictionaryBase         
extends                                 BaseObj
implements                              IGhostDictionary            {
    
    
    protected static    String                  wordListFileName            =   "none.lst";  
    protected static    int                     minWordLength               =   4; 
    protected static    int                     numberOfPlayers             =   2;
    
    protected           boolean                 isDictionaryLoaded          =   false;
    protected           long                    totalNumbreOfCharInRawFile  =  -1;
    protected           int                     totalNumbreOfWordInRawFile  =  -1;
    protected           int                     totalNumbreOfWordReduced    =  -1;
    protected           long                    totalNumbreOfCharReduced    =  -1;
    
    private             boolean                 keepSmallWords              =   false;

    @Override //implements
    public void                         setMinWordLenght            (int        numCh)              {
     minWordLength = numCh;
    }

    @Override //implements
    public void                         setWordListFileName         (String     fileName)           {
     wordListFileName = fileName;
    }

    @Override //implements
    public void                         setNumberOfPlayers          (int numPlayers)                {
     numberOfPlayers=numPlayers;
    }
    @Override //implements
    public int                          getNumberOfPlayers          ()                              {
        return numberOfPlayers;
    }
    

    /**
     * this method reads and parse the RAW dictionary file which is expected to be already in alphabetic order
     * this implementation would throw away any words which contain as prefix
     * another word of at least {@link #minWordLength} chars
     * it also would throw away any word shorter than {@link #minWordLength} depending on the value of {@link #keepSmallWords}
     */
    @Override //implements
    public void                         init                        ()                              {
     File f = new File (wordListFileName);
     log.dbg("check if file: '%s' exists..", f.getAbsoluteFile());
     if (f.exists()){
      totalNumbreOfWordInRawFile=0;
      totalNumbreOfCharInRawFile=0;  
      totalNumbreOfWordReduced  =0;
      totalNumbreOfCharReduced  =0;
      log.dbg("YESss!");
      
      try {
        BufferedReader br   =new BufferedReader(new FileReader(wordListFileName));
        String lastGoodWord ="@";
        String word         =null;
        
        
        //given that the input list is in alphabetic order
        //and that words that contains another word as a prefix are useless in this game
        //we significantly reduce the dictionary by removing those words ...
        //lastGoodWord works as pivot if the next words still contain it as a prefix they can be safely removed
        while (null!=(word=br.readLine())){
            int wLen = word.length();
            boolean isLongWord=(wLen>=minWordLength);
            if (!word.startsWith(lastGoodWord)) {   
                 if (isLongWord){
                     addWord(word);
                     lastGoodWord  =word;
                 }
                 else {
                  if (keepSmallWords) addWord   (word);
                               //       addSmall    (word);                    
                 }
                totalNumbreOfWordReduced++; 
                totalNumbreOfCharReduced+=word.length();
                }
                else {
                 //words extending an already existing one ... 
                }
            totalNumbreOfWordInRawFile++;
            totalNumbreOfCharInRawFile+=word.length();

            
        }
        br.close();
        isDictionaryLoaded = true;
      } 
      catch (Exception e) {
        log.err("FATAL: %s", e.getLocalizedMessage());
      }
      
      log.dbg("number of words: %s", this.getRawWordCount());
     }
    }
    
    @Override //implements
    public void                         start                       ()                              {
     //TODO implement this in a multithreading application
    }

    @Override //implements
    public long                         getRawCharCount             ()                              {
     return totalNumbreOfCharInRawFile;
    }

    @Override //implements
    public int                          getRawWordCount             ()                              {
     return totalNumbreOfWordInRawFile;
    }

    @Override //implements
    public int                          getRawReducedWordCount      ()                              {
     return totalNumbreOfWordReduced;
    }

    @Override //implements
    public long                         getRawReducedCharCount      ()                              {
        return totalNumbreOfCharReduced;
    }

    protected abstract void             addWord                     (String word)                   ;
    
    @Override //implements
    public    abstract IGameDictionary  getGameDictionary           (String pfx, int numPlayers);
    
    @Override //implements
    public    abstract TrieLeaf         getLeaf                     (String currentWord);

    @Override
    public void                         finalize                    ()                              {
        log.dbg("*** %s.finalize()",this);
    }

};
