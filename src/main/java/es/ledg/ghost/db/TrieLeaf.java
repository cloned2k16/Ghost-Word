package es.ledg.ghost.db;

public class TrieLeaf {

    /**
     * 
     * @param w
     */
    public TrieLeaf(String w) {
        word = w;
        len = w.length();
    }

    public int getLooser(int numPlayers) {
        return ((len - 1) % numPlayers) + 1;
    }

    public String word;
    public int len;

    public String toString() {
        return String.format("(%2d) %d [%s]", len, getLooser(2), word);
    }
}
