package     es.ledg.ghost.db;

import java.io.IOException;



public      class                       HashDictionary
extends                                 GhostDictionaryBase         {

    
    private HashListIndexer             wordListIndexer             = new HashListIndexer(minWordLength);
    
    
    @Override
    protected void                      addWord                     (String w)                      {
        wordListIndexer.addWord(w);
    }

    
    public void                         showRootList                ()                              {
     try { wordListIndexer.showRootList(); }  catch (IOException e) { e.printStackTrace();  }
    }

    public void                         saveDictionaryFor           (String pfx)                    {
     try { 
         wordListIndexer.saveDictionaryFor(pfx);
     }  catch (IOException e) { e.printStackTrace();    }
    }


    @Override
    public IGameDictionary              getGameDictionary           (String pfx, int numPlayers)    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public TrieLeaf getLeaf(String currentWord) {
        // TODO Auto-generated method stub
        return null;
    }



    
};