package es.ledg.ghost.db;

public interface                        IGameDictionary             {

    int                                 size                        ();
    public String                       getRootPrefix               ();
    public String                       wordList                    ();
    public String                       getWordAsWinner             ();
    public String                       getWordAsLooser             ();
    public boolean                      amGoingToWin                ();
    
};
