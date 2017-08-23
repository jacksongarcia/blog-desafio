package com.herokuapp.blogdf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class ControllerServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
            HttpServletResponse response) 
            throws ServletException, IOException {

        //String parametro = request.getParameter("logica");
        String nomeDaClasse = "com.herokuapp.blogdf.MainPage" ;

        try {
            Class classe = Class.forName(nomeDaClasse);

            Logic logica = (Logic) classe.newInstance();
            String pagina = logica.executa(request, response);

            request.getRequestDispatcher(pagina).forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
              "A lógica de negócios causou uma exceção", e);
        }
    }
}