package es.ledg.ghost;

public class GhostResponse {
    public GhostResponse(String sts, String msg, String cw) {
        status = sts;
        message = msg;
        currentWord = cw;
    }

    String status;
    String message;
    String currentWord;

    public String toString() {
        return status + "::" + message + "::" + currentWord;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GhostResponse))
            return false;
        GhostResponse r = (GhostResponse) o;
        return this.status.equals(r.status) && this.message.equals(r.message) && this.currentWord.equals(r.currentWord);
    }
}
