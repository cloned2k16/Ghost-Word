package es.ledg.ghost.db;

import java.util.Hashtable;

import es.ledg.BaseObj;


public class                            GhostDictionaryFactory      
extends                                 BaseObj                     {
    
    private static int                  maxNumOfInstances   =   1;
    private static 
     Hashtable<String,IGhostDictionary> instances           = new Hashtable<String,IGhostDictionary>();
    private static String               wordListFilePath;
    private static int                  minNumOfChars;
    private static int                  numOfPlayers;
    private static DictionaryType       type;
    
    public enum                         DictionaryType { TRIE,HASH,UNKOWN;
        public static DictionaryType    map             (String type) { return valueOf(type.toUpperCase()); }
    }
    
    /**
     * typically instance would return a singleton object,
     * but as it would be bottleneck in case of many concurrent users we let it open
     * to a more sophisticated and scalable implementation in the future
     * @param playerID   
     * @throws Exception 
     */
    public static IGhostDictionary      instance                    (String playerID) 
            throws                      Exception                                                   {
         IGhostDictionary  dict=null;
         
         int sz=instances.size();
         
         if (sz<maxNumOfInstances){
          //at the moment just work as singleton so don't care about the playerID
          //slog.dbg("using [%s] Dictionary type ...",type);   
               if (DictionaryType.TRIE.equals(type)) dict = new TrieDictionary();
          else if (DictionaryType.HASH.equals(type)) dict = new HashDictionary();
          else throw new Exception("Invalid dictionary type!");
               
          dict.setWordListFileName  (wordListFilePath);
          dict.setMinWordLenght     (minNumOfChars);
          dict.setNumberOfPlayers   (numOfPlayers);
          dict.init();
          dict.start();
          instances.put("single",dict); 
         }
         else dict = instances.get("single");
         
         return dict;
    }
    
    public static void                  setup                       ( String         pathToFile
                                                                    , DictionaryType dictionaryType
                                                                    , int mChars
                                                                    , int nPlayers)                 {
        //slog.dbg("setup(%s,%s,%d,%d)",pathToFile,dictionaryType,mChars,nPlayers);
        sout("setup(%s,%s,%d,%d)",pathToFile,dictionaryType,mChars,nPlayers);
        type                = dictionaryType;
        wordListFilePath    = pathToFile;
        minNumOfChars       = mChars;
        numOfPlayers        = nPlayers;
        
        //since we don't handle multiple concurrent instances yet
        //setup forces remove any instances
        instances.clear();
        //forcing cleanup of previous instance ...
        Runtime.getRuntime().gc();
    };

    static {
    	// force hard-coded setup, cause more sophisticated setup is beyond scope!
    	setup("data/dictionary.txt",DictionaryType.TRIE,4,2);
    }
}
