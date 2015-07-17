package ch.maxant.javascript_everywhere_demo;

import ch.maxant.javascript_everywhere.Engine;

import java.io.IOException;
import java.util.Arrays;

import javax.ejb.Stateless;
import javax.script.ScriptException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

/** a JAX-RS REST Service which runs the Javascript on the server */
@ApplicationPath("x")
@Path("/services")
@Stateless
public class Services extends Application {

    private Engine engine;

    public Services() throws ScriptException, IOException {
        long start = System.currentTimeMillis();

        engine = new Engine(Arrays.asList("lodash-3.10.0.js", "rules.js"));

        System.out.println("Setup JS Framework in " + (System.currentTimeMillis() - start) + " ms");
    }

    @POST
    @Path("rule419")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Result rule419(Person[] people) throws ScriptException, IOException, NoSuchMethodException {

        long start = System.currentTimeMillis();

        String result = engine.invoke(people, "maxant", "rule419");

        System.out.println("Executed JS in " + (System.currentTimeMillis() - start) + " ms");

        return new Result(result);
    }

}
