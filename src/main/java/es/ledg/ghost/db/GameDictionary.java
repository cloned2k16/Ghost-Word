package es.ledg.ghost.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import es.ledg.BaseObj;


public class                            GameDictionary       
extends                                 BaseObj
implements                              IGameDictionary             {

    private TrieNode                    root;
    private String                      rootPrefix;
    private int                         numPlayers;
    private int []                      odds;                                           
    private ArrayList<TrieLeaf>         allWords        = new ArrayList<TrieLeaf>();
    private ArrayList<TrieLeaf>         winning         = new ArrayList<TrieLeaf>();
    private ArrayList<TrieLeaf>         loosing         = new ArrayList<TrieLeaf>();
    private int                         myWinningOdds   = 0;
    private int                         myLoosingOdds   = 0;

    public void                         setRootNode                 (TrieNode stNode)               {
     root = stNode;
    }
    
    public TrieNode                     getRootNode                 ()                              {
     return root;
    }

    public String                       getRootPrefix               ()                              {
     return rootPrefix;
    }
    
    public void                         setRootPrefix               (String pfx)                    {
     rootPrefix = pfx;
    }

    /**
     * 
     * @param numPlrs
     */
    public                              GameDictionary              (int numPlrs)                   {
        numPlayers  = numPlrs;
        odds        = new int[numPlayers+1];// 1 based!
    }
    
    
    /**
     * 
     * @param leaf
     */
    public void                         addWord                     (TrieLeaf leaf)                 {
      int looser = leaf.getLooser(numPlayers);
      odds[looser]++;
      if (looser==numPlayers) {
       loosing.add(leaf);
       myLoosingOdds++;
      }
      else {
       winning.add(leaf);
       myWinningOdds++;
      }
      allWords.add(leaf);
    }
    
    /**
     * 
     * @return the size of all words contained by this dictionary
     */
    public int                          size                        ()                              {
        return allWords.size();
    }

    /**
     * 
     * @return the guessing assumption made up by the relation between the number of winning and loosing words
     */
    @Override
    public boolean                      amGoingToWin                ()                              {
        return myWinningOdds >= myLoosingOdds; //I'm optimist :D
    }

    /**
     * 
     * @return a debugging string showing internal stats
     */
    private String                      buildStats                  ()                              {
        String stats="{";
        for (int n=1; n<odds.length;n++) stats+=String.format("[%4d]", odds[n]);
        stats+="}";
        return stats;
    }
    
    @Override
    public String                       toString                    ()                              {
        String stats=buildStats();
        return String.format(getClassName()+"[%5d] %s : %s",allWords.size(), stats,amGoingToWin()?"WINNER":"LOSER");
    }
    
    /*
     * @see es.ledg.ghost.db.IGameDictionary#wordList()
     */
    @Override
    public String                       wordList                    ()                              {
        sortByLen(allWords);
        String res="\r\n";
        for (TrieLeaf lw:allWords){
            res+=lw+"\r\n";
        }
        return res;
    }

    /**
     * @param list
     */
    public void                         sortByLen                   (ArrayList<TrieLeaf> list)      {
        Collections.sort(list, new Comparator<TrieLeaf>(){
            public int compare(TrieLeaf n1,TrieLeaf n2) {
                return n1.len - n2.len;
            }
        });
    }

    /**
     * 
     * @param list
     * @return a pseudo {@link Math#random()} {@link TrieLeaf#TrieLeaf(String)} from a list passed as parameter 
     */
    private TrieLeaf                    rndPick                     (ArrayList<TrieLeaf> list)      {
     int        sz      = list.size();
     int        rndPick = (int)(Math.random()*sz);
     TrieLeaf   leaf    = list.get(rndPick);
     return leaf;
    }

    @Override
    public String                       getWordAsWinner             ()                              {
     if (winning.size()==0) return null;    
     TrieLeaf   leaf    = rndPick(winning);
     log.dbg("guess i will win with: %s",leaf);
     return leaf.word;
    }

    @Override
    public String                       getWordAsLooser             ()                              {
        if (loosing.size()==0) return null;
        sortByLen(loosing);
        ArrayList<TrieLeaf> lngstLoosing=new ArrayList<TrieLeaf>();
        int idx=loosing.size()-1;
        TrieLeaf lngst=loosing.get(idx);
        int max= lngst.len;
        lngstLoosing.add(lngst);
        while (idx>0 && (lngst=loosing.get(--idx)).len==max) lngstLoosing.add(lngst);
        log.dbg("I have %d longest loosing words",lngstLoosing.size());
        TrieLeaf    leaf    = rndPick(lngstLoosing);
        log.dbg("guess i will lose with: %s",leaf);
        return leaf.word;
    }









};
