package es.ledg.ghost.db;

/**
 * 
 * @author i5
 * 
 * since a Trie is quite expensive in term of memory footprint we keep it as light as possible in order 
 * to use the less amount of memory possible 
 *
 */

public class                            TrieNode                    {
    public static int                   numNodes = 0;
    
    //we keep the Trie Node as small as possible ...
    public char                         ch;         // character representing myself 
    public TrieNode                     children;   // the children chain entry point
    public TrieNode                     sibling;    // my slibing nodes
    public TrieLeaf                     leaf;       // contains complete words informations
    
    public                              TrieNode        (char c) {
        numNodes++;
        ch=c;
    }
    
    public TrieNode                     getChild        (char ch) {
        TrieNode node=children;
        while (node!=null && node.ch!=ch) node=node.sibling;
        return node;
    }
    
    public TrieNode                     addtChild       (char ch) {
        TrieNode node=children;
        if (node==null) node=children=new TrieNode(ch);
        else{
            TrieNode nodO=node;
            while(node!=null && node.ch!=ch) {nodO=node; node=node.sibling; }
            if (node==null) nodO.sibling=node=new TrieNode(ch);
        }
        return node;        
    }

    @Override
    public String                       toString        () {
        return String.format("TrieNode[%s] %s",ch==0?"root":ch,leaf);
    }
};
