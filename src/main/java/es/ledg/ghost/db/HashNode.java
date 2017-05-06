package es.ledg.ghost.db;

import es.ledg.BaseObj;

public class HashNode extends BaseObj {

    private static int numPlayers = 2;
    private String word;
    private int len;
    private int looserIs;

    public HashNode(String w) {
        if (null != w) {
            word = w;
            len = w.length();
            updateLooser();
        } else {
            len = 0;
            looserIs = -1;
        }
    }

    public static int numPlayers() {
        return numPlayers;
    }

    public static void setNumPlayers(int n) {
        numPlayers = n;
    }

    public void updateLooser() {
        looserIs = ((len - 1) % numPlayers) + 1;
    }

    public int looserIs() {
        return looserIs;
    }

    public int lenght() {
        return len;
    }

    public String word() {
        return word;
    }

    @Override
    public String toString() {
        return String.format("[%d] (%2d) '%s'", looserIs, len, word);
    }

};
