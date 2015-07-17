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
