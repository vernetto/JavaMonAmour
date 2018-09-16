package ejb3.bean;

import ejb3.bean.HelloEjb;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.ejb.EJB;
import java.io.IOException;

public class HelloEjb3Servlet extends HttpServlet {
    @EJB
    private HelloEjbLocal helloEjb;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		execute(request, response);
	}

	private void execute(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
        	request.setAttribute("helloEjb", helloEjb);
        	request.setAttribute("printHello", helloEjb.printHello("Djunaedi"));
        	request.getRequestDispatcher("/HelloEjb3.jsp").forward(request, response);                            
	}
}