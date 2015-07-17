/* Copyright 2015 Ant Kutschera

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. */
package ch.maxant.javascript_everywhere;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/** An encapsulation which allows Javascript to be run in the browser but also on the server in a Java environment */
public class Engine {

    private ScriptEngine engine;

    private Object referenceToJavascriptJSONInstance;

    public Engine(List<String> javascriptFilesToLoad) throws ScriptException, IOException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName("nashorn");
        if (engine == null) {
        	//java 7 fallback
            engine = engineManager.getEngineByName("JavaScript");
        }

        //preload all scripts and dependencies given by the caller
        for (String js : javascriptFilesToLoad) {
            load(engine, js);
        }

        referenceToJavascriptJSONInstance = engine.eval("JSON");
    }

    private void load(ScriptEngine engine, String scriptName) throws ScriptException, IOException {
        //fetch script file from classloader (e.g. out of a JAR) and put it into the engine
        ClassLoader cl = getClass().getClassLoader();
        try (InputStream script = cl.getResourceAsStream(scriptName)) {
            engine.eval(new InputStreamReader(script));
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T invoke(Object input, String module, String functionNameToExecute) throws JsonGenerationException,
            JsonMappingException, IOException, NoSuchMethodException, ScriptException {

        //TODO is the engine threadsafe??

        final Invocable invocable = (Invocable) engine;

        //convert data model to json and make it usable as input. if the javascript were supposed to work directly
        //with the Java input, then you'd have to add "get(" before each attribute access. this way, the javascript
        //can simply access fields like this: "input[2].name" (gets the third elements name attribute), rather than
        //say "input.get(2).getName()" like you would have to do in Java
        ObjectMapper om = new ObjectMapper();
        String dataString = om.writeValueAsString(input);
        Object data = invocable.invokeMethod(referenceToJavascriptJSONInstance, "parse", dataString); //same as: JSON.parse(dataString)

        //execute
        Object moduleRef = engine.get(module);
        Object result = invocable.invokeMethod(moduleRef, functionNameToExecute, data);
        return (T) result;
    }

    /*
     * OLD
     * 
     * Object result = invocable.invokeFunction("rule9443", (Object) people); //cast, since this tells it to use the array as the
     * first parameter, rather than using it as an array of parameters! System.out.println(((Person) result).getName());
     */
}
