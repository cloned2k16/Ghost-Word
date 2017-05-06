package     es.ledg.ghost.db;

public interface                        IGhostDictionary            {
    
    /**
     * the minimum length for words to be considered valid
     * @param numCh
     */
    public void                         setMinWordLenght            (int        numCh);
    /**
     * the relative or absolute (during tests) path of where the plain text dictionary file is 
     * @param fileName
     */
    public void                         setWordListFileName         (String     fileName);  
    /**
     * sets the number of players even if at the moment the UI will only work for 2
     * @param numPlayers
     */
    public void                         setNumberOfPlayers          (int        numPlayers);
    /**
     * @return the number of players
     */
    public int                          getNumberOfPlayers          ();
    /**
     * 
     * @param currentWord
     * @return if exists reTRIEve currentWord as {@link TrieLeaf#TrieLeaf(String)} ..
     */
    public TrieLeaf                     getLeaf                     (String currentWord);
    /**
     * @param pfx
     * @param numPlayers
     * @return 
     * retrieve a GameDictionay for a specific prefix 
     * and number of player where most of the computing
     * required by the game is already precooked ...
     */
    public IGameDictionary              getGameDictionary           (String     pfx, int numPlayers);

    /**
     * initialize the dictionary
     */
    public void                         init                        ();
    /**
     * for future use in a Multithreading context
     */
    public void                         start                       ();

    
    /**
     * @return
     * returns the total number of words in the plain text dictionary (before reduction)
     */
    public int                          getRawWordCount             ();
    /**
     * @return
     * returns the total number of chars in the plain text dictionary (before reduction)
     */
    public long                         getRawCharCount             ();
    /**
     * @return
     * returns the total number of words in the dictionary (after reduction)
     */
    public int                          getRawReducedWordCount      ();
    /**
     * @return
     * returns the total number of chars in the dictionary (after reduction)
     */
    public long                         getRawReducedCharCount      ();
    
     
    
};