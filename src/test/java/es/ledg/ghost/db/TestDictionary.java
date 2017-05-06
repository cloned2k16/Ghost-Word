package es.ledg.ghost.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDictionary {

    @Test
    public void testWordCount() {
        IGhostDictionary dictionary = null;
        try {
            dictionary = GhostDictionaryFactory.instance("solo");
        } catch (Exception e) {
            fail("can't get an instance of the dictionary");
        }

        assertNotNull("an IGhostDictionary must exist!", dictionary);

        assertEquals(178691, dictionary.getRawWordCount());                                             // empiric test (depends on the actual dictionary file!)
        assertEquals(46118, dictionary.getRawReducedWordCount());                                       // effective number of words by the game point of view
                                                                                                        // all words wich starts with an existing word prefix
                                                                                                        // make no sense for the game cause 
                                                                                                        // he game will effectively end before you can actually
                                                                                                        // compose any longer word than the root prefix

        assertEquals(1584476, dictionary.getRawCharCount());                                            // just to check something more
    }

    @Test
    public void testPlayers() {
        IGhostDictionary dictionary = null;
        int numPlayers = 0;

        try {
            dictionary = GhostDictionaryFactory.instance("solo");
        } catch (Exception e) {
            fail("can't get an instance of the dictionary");
        }

        assertNotNull("an IGhostDictionary must exist!", dictionary);
        numPlayers = dictionary.getNumberOfPlayers();
        assertEquals(2, numPlayers); // static setup !
    }

    @Test
    public void testStatistics() {
        IGhostDictionary dictionary = null;
        IGameDictionary dict = null;

        try {
            dictionary = GhostDictionaryFactory.instance("solo");
        } catch (Exception e) {
            fail("can't get an instance of the dictionary");
        }

        assertNotNull("an IGhostDictionary must exist!", dictionary);

        dict = dictionary.getGameDictionary("A", 2);

        assertNotNull("an IGameDictionary must exist!", dict);

        assertEquals("es.ledg.ghost.db.GameDictionary[ 3339] {[1716][1623]} : WINNER", dict.toString());

        dict = dictionary.getGameDictionary("B", 2);

        assertNotNull("an IGameDictionary must exist!", dict);

        assertEquals("es.ledg.ghost.db.GameDictionary[ 2049] {[ 930][1119]} : LOSER", dict.toString());

        dict = dictionary.getGameDictionary("N", 2);

        assertNotNull("an IGameDictionary must exist!", dict);

        assertEquals("es.ledg.ghost.db.GameDictionary[ 1899] {[ 983][ 916]} : WINNER", dict.toString());

        dict = dictionary.getGameDictionary("W", 2);

        assertNotNull("an IGameDictionary must exist!", dict);

        assertEquals("es.ledg.ghost.db.GameDictionary[  667] {[ 280][ 387]} : LOSER", dict.toString());

        dict = dictionary.getGameDictionary("Z", 2);

        assertNotNull("an IGameDictionary must exist!", dict);

        assertEquals("es.ledg.ghost.db.GameDictionary[  255] {[ 115][ 140]} : LOSER", dict.toString());

    }

}
