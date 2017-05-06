package es.ledg.ghost;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

import es.ledg.BaseObj;
import es.ledg.ghost.db.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext*.xml")
public class TestTransaction extends BaseObj {

    @Autowired
    Transaction transaction;

    @Test
    // @Consumes(MediaType.APPLICATION_JSON)
    public void testPlayersMoves() {

        IGhostDictionary dictionary = null;
        try {
            dictionary = GhostDictionaryFactory.instance("solo");
        } catch (Exception e) {
            fail("can't get an instance of the dictionary");
        }

        assertNotNull("an IGhostDictionary must exist!", dictionary);
        transaction.setDictionary(dictionary);

        Gson gs = new Gson();
        GhostResponse res, chk;

        res = gs.fromJson(transaction.check("AUTECOLOGIE"), GhostResponse.class);
        chk = new GhostResponse("YOU_WIN", "AUTECOLOGIES", "");
        assertEquals(chk, res);

        res = gs.fromJson(transaction.check("AUTECOLOGIES"), GhostResponse.class);
        chk = new GhostResponse("YOU_LOSE_BY_WORD_MATCH", "AUTECOLOGIES", "");
        assertEquals(chk, res);

        res = gs.fromJson(transaction.check("ACUME"), GhostResponse.class);
        chk = new GhostResponse("YOU_WIN", "ACUMEN", "");
        assertEquals(chk, res);

        res = gs.fromJson(transaction.check("ACUMEN"), GhostResponse.class);
        chk = new GhostResponse("YOU_LOSE_BY_WORD_MATCH", "ACUMEN", "");
        assertEquals(chk, res);

        res = gs.fromJson(transaction.check("HX"), GhostResponse.class);
        chk = new GhostResponse("YOU_LOSE_UNKNOWN_WORD", "HX", "");
        assertEquals(chk, res);

        // this one exist in en.txt dictionary !
        res = gs.fromJson(transaction.check("DICHLORODIFLUOROMETHANE"), GhostResponse.class);
        chk = new GhostResponse("YOU_LOSE_UNKNOWN_WORD", "DICHLORODIFLUOROMETHANE", "");
        assertEquals(chk, res);

    }

}