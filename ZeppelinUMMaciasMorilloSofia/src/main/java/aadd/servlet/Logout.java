package aadd.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet {
    
    /**
	 * Servlet implementando Lougout - sin su propia anotación porque lo vamos a configurar desde web.xml
	 */
	private static final long serialVersionUID = 1L;
	public Logout() {
        super();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.invalidate(); //desloguea? invalida sesion, espera un segundo y vuelve a la pagina de la que venias
        String referer = request.getHeader("referer");
        response.setHeader("refresh", "1; URL=" + referer);
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ZeppelinUM</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Cerrando sesión ...</p>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}