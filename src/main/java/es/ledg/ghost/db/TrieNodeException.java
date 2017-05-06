package es.ledg.ghost.db;

public class TrieNodeException extends Exception {

    public TrieNodeException(String msg, Object... args) {
        super(String.format(msg, args));
    }

    /**
     *
     */
    private static final long serialVersionUID = -7589685152990817768L;

}
