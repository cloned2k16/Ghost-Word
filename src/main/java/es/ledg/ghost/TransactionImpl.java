package es.ledg.ghost;

import es.ledg.ghost.db.IGhostDictionary;
import es.ledg.ghost.db.IGameDictionary;
import es.ledg.ghost.db.TrieLeaf;

import com.google.gson.Gson;

import es.ledg.BaseObj;

public class TransactionImpl extends BaseObj implements Transaction {
    IGhostDictionary dictionary = null;

    private String jsonMsg(String status, String msg, String currWord) {
        Gson gson = new Gson();
        GhostResponse gr = new GhostResponse(status, msg, currWord);
        return gson.toJson(gr);
    }

    @Override
    public void setDictionary(IGhostDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public String check(String word) {
        word = word.toUpperCase();

        String answer = null;
        IGameDictionary dict;
        TrieLeaf isLeaf = dictionary.getLeaf(word);

        if (null != isLeaf) {
            return jsonMsg("YOU_LOSE_BY_WORD_MATCH", isLeaf.word, "");
        } else {
            dict = dictionary.getGameDictionary(word, dictionary.getNumberOfPlayers());
            log.dbg("we got %d words", dict.size());
            if (null != dict && dict.size() > 0) {
                if (dict.amGoingToWin()) {
                    answer = dict.getWordAsWinner();
                } else {
                    answer = dict.getWordAsLooser();
                }
                if (((answer.length() - word.length()) == 1)) {
                    return jsonMsg("YOU_WIN", answer, "");
                }
            } else { // no dictionary for the prefix
                return jsonMsg("YOU_LOSE_UNKNOWN_WORD", word, "");
            }
        }
        boolean willWin = dict.amGoingToWin();

        log.dbg("IM_GONNA_%s :: %s", (willWin ? "WIN" : "LOSE"), dict);
        return jsonMsg("PLAY", answer, "" + answer.charAt(word.length()));
    }

    @Override
    public String handleCommand(String cmd, String word) {
        switch (cmd) {
        case "new":
            return jsonMsg("NEW_GAME", "you play!", "");
        case "play":
            return check(word);
        case "test":
            return check(word);
        }
        return "[" + cmd + "]";
    }
}