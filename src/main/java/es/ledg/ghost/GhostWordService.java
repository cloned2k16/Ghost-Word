package es.ledg.ghost;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ledg.BaseObj;
import es.ledg.ghost.db.GhostDictionaryFactory;
import es.ledg.ghost.db.IGhostDictionary;

import javax.ws.rs.core.MultivaluedMap;

@Component
@Path("/GHOST")
public class GhostWordService extends BaseObj {

    @Autowired
    Transaction transaction;

    IGhostDictionary dictionary;

    public GhostWordService() {
        try {
            dictionary = GhostDictionaryFactory.instance("solo");
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.err("Exception %s\n%s", e.getMessage(), errors);
        }
    }

    @POST
    public Response handleCommand(MultivaluedMap<String, String> formParams) {
        transaction.setDictionary(dictionary);
        log.err("%s", formParams);
        String result = transaction.handleCommand(formParams.getFirst("move"), formParams.getFirst("char"));
        return Response.status(200).entity(result).build();
    }

    @GET
    @Path("{sub: [a-zA-Z/]+}")
    public Response checkWord(@PathParam("sub") String word) {

        transaction.setDictionary(dictionary);

        String result = transaction.check(word);

        return Response.status(200).entity(result).build();

    }

}