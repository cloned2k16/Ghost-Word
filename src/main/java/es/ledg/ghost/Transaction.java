package es.ledg.ghost;

import es.ledg.ghost.db.IGhostDictionary;

public interface Transaction {

    void setDictionary(IGhostDictionary dictionary);

    String check(String word);

    String handleCommand(String cmd, String word);

}