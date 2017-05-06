package es.ledg.ghost.db;

import java.util.Stack;


/**
  * Dictionary engine implementing a custom TRIE specifically made for this game
  * where we keep the size of TrieNode at minimum and just indexing over TrieLeaf classes which actually contains
  * the dictionary data
  * {@link TrieNode#TrieNode(char)}
  * 
  */
       
public class                            TrieDictionary                      
extends                                 GhostDictionaryBase         {
    
    public  TrieNode                    root = new TrieNode((char)0);

    /**
     *  debugging purpose ...
     */
    public  void                        showContent                 ()                              {
        log.dbg("showContent entry point: %c (%6d)",root.children.ch,TrieNode.numNodes);
        int maxNumLogs=100;
        TrieNode            node    =   root;
        Stack<TrieNode>     stack   =   new Stack<TrieNode>();
        while (node.children != null 
            || node.sibling  != null
            || node.leaf     != null) {
         String str=".";    
         TrieNode child = node;
         while (child!=null) { 
           str+="["+child+"]"; 
           if (child.sibling!=null){
               TrieNode slib=child.sibling;
               stack.push(slib);
           }
           child=child.children; 
         }
         if (0<--maxNumLogs) log.dbg("_%s", str);
         if (stack.size()==0) break;
         else                   node=stack.pop();
        }
    }

    /**
     * adds a word to the TRIE structure by adding the required nodes if missing and finally append the leaf to the last node 
     * @param leaf
     * @throws TrieNodeException
     */
    public  void                        addLeaf                     (TrieLeaf leaf)                 
     throws                             TrieNodeException                                           {
        //log.dbg("addLeaf: %s", leaf);
        String  word    = leaf.word;
        int     len     = word.length();
        TrieNode node   = root;
        for (int n=0; n<len; n++){
          char ch=word.charAt(n);
          TrieNode child=node.getChild(ch);
          if (child==null)  {
           node=node.addtChild(ch);
          }
          else              {
           node=child;
          }
        }// node is our leaf node
        if (node.leaf!=null) {
          throw new TrieNodeException("Collision at [%s] !!!",node);            
        }
        else node.leaf = leaf;
        
    }

    /**
     * we pass through the word by wrapping it first into a TrieLeaf
     * 
     * @see es.ledg.ghost.db.GhostDictionaryBase#addWord(java.lang.String)
     */
    @Override //abstract
    public void                         addWord                     (String word)                   {
        TrieLeaf leaf=new TrieLeaf(word);
        try {
         addLeaf(leaf);
        } 
        catch (TrieNodeException e) { e.printStackTrace();  }
    }
    
    /**
      * 
      * @param pfx
      * @return 
      * if exists a {@link TrieNode#TrieNode(char)} object at the root of the word passed in argument pfx
      */
    public  TrieNode                    getPrefixRoot               (String pfx)                    {
     TrieNode node=root;
     int len=pfx.length();
//   String path="[";
     for (int n=0; n<len; n++){
       char ch=pfx.charAt(n);
       node=node.getChild(ch);
       if (node==null) break;
//     path+=node.ch;
     }
//   log.dbg("getPrefixRoot: [%s] : %s. %s", pfx,path,node);
     return node;
    }
    
   /**
     * 
     * @param pfx
     * @param numPlayers
     * @return
     * a {@link GameDictionary#GameDictionary(int)} object indexing all the {@link TrieLeaf#TrieLeaf(String)}
     * nodes which are children of the node 'pfx' and by the way arrange it by number of players ready to be used 
     * by the game engine 
     */
    @Override //abstract
    public  GameDictionary              getGameDictionary           (String pfx,int numPlayers)     {
     
     GameDictionary rslt    = new GameDictionary(numPlayers);
     TrieNode       stNode  = getPrefixRoot (pfx);
                    rslt.setRootPrefix      (pfx);
                    rslt.setRootNode        (stNode);
     if (stNode==null || null!=stNode.leaf) { // The End!
      log.dbg("TheEnd. [%s][%s]", pfx,stNode);
      
     }
     else buildDictionary(stNode.children,rslt);
     return rslt;
    }
    
    /**
     * builds a GameDictionary which is a subset starting at stNode prefix
     * @param stNode
     * @param rslt
     */
    private void                        buildDictionary             (TrieNode stNode, GameDictionary rslt) {
        TrieNode        node    = stNode;
        Stack<TrieNode> stack   = new Stack<TrieNode>();
        while ( null!=node ) {
            if (null!=node.leaf)        {   rslt.addWord(node.leaf);
            }
            if (null!=node.sibling)     {   stack.push(node.sibling);
            }
            if (null!=node.children)    {   node=node.children;
            }
            else {
                if (0==stack.size()) node=null;
                else                 node=stack.pop();
            }
        }
    }
    
    /**
     * reTRIEve (if exist) the leaf representing the parameter @param currentWord 
     * 
     * @see es.ledg.piksel.ghost.db.GhostDictionaryBase#getLeaf(java.lang.String)
     */
    @Override //abstract
    public TrieLeaf                     getLeaf                     (String currentWord) {
        TrieNode node=  getPrefixRoot   (currentWord);
        log.dbg("get Leaf: %s : %s",currentWord,node );
        if (node!=null) return node.leaf;
        return null;
    }

};

