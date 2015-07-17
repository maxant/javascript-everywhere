package ch.maxant.javascript_everywhere_demo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.script.ScriptException;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.maxant.javascript_everywhere.Engine;

/** proof that it also runs in plain Java environment */
public class JavasideJSTest {

	private static Engine engine;

    @BeforeClass
    public static void setup() throws ScriptException, IOException {
        //instantiate engine
        engine = new Engine(Arrays.asList("lodash-3.10.0.js", "rules.js"));
    }

    @Test
    public void testOK() throws Exception {

        //prepare data model - the input to the javascript
        Person[] people = new Person[]{new Person("John"), new Person("Ant")};

        //invoke engine
        String result = engine.invoke(people, "maxant", "rule419");

        //evaluate output
        assertEquals("OK", result);
    }

    @Test
    public void testFail() throws Exception {

        //prepare data model - the input to the javascript
        Person[] people = new Person[]{new Person("John"), new Person("John")};

        //invoke engine
        String result = engine.invoke(people, "maxant", "rule419");

        //evaluate output
        assertEquals("Scam", result);
    }

    @Test
    public void testPerformance() throws Exception {
    	
    	Person[] people = new Person[]{new Person("John"), new Person("John")};

    	StringBuffer sb = new StringBuffer();
    	final double NUM_RUNS = 1000.0;
    	long start = System.currentTimeMillis();
		for(int i = 0; i < NUM_RUNS; i++){
    		String result = engine.invoke(people, "maxant", "rule419");
    		sb.append(result);
    	}
		System.out.println("averaged " + new DecimalFormat("0.##").format((System.currentTimeMillis()-start)/ NUM_RUNS) + "ms per run");
    	System.out.println(sb);
    }
    
    /*
     * OLD
     * 
     * String rule = "_(input).filter(function(e){ println(e.getName()); return e.getName() == \"John\"; }).first()"; //watch out,
     * === doesnt work here! Perhaps since a java.lang.String aint the same as a javascript literal string? String wrapper =
     * "function rule9443(input) {return " + rule + "; }"; engine.eval(wrapper);
     */
}
