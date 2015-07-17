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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet which can load the Javascript and supply it to the client
 */
@WebServlet("/ScriptLoader.js")
public class ScriptLoader extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //which script should be loaded?
        String script = request.getParameter("script");

        response.setContentType("text/javascript");

        //read script from classpath and write it to response
        ClassLoader cl = this.getClass().getClassLoader();
		try (InputStream is = cl.getResourceAsStream(script)) {
            int curr = -1;
            while ((curr = is.read()) != -1) {
                response.getOutputStream().write(curr);
            }
        }
    }
}
