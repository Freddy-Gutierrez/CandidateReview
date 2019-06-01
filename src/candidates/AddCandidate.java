package candidates;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class AddCandidate
 */
@WebServlet("/AddCandidate")
public class AddCandidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddCandidate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("permissions") != null && ((String) session.getAttribute("permissions")).equalsIgnoreCase("admin")) {
			request.setAttribute("user", request.getAttribute("user"));
			request.setAttribute("permissions", request.getAttribute("permissions"));
			request.getRequestDispatcher("/WEB-INF/AddCandidate.jsp").forward( request, response );  
		}
		else{
			request.setAttribute("user", request.getAttribute("user"));
			request.setAttribute("permissions", request.getAttribute("permissions"));
			request.getRequestDispatcher("/WEB-INF/Denied.jsp").forward( request, response );
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // get the user input
        String name = request.getParameter( "name" );
        String specialty = request.getParameter( "special" );
        String pres = request.getParameter("pres");
        Connection c = null;
        try
        {
            String url = "jdbc:mysql://localhost/cs3220stu20?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu20";
            String password = "bOHYxRHq";
            
            c = DriverManager.getConnection( url, username, password );
            PreparedStatement preparedStatement = null;
            String sql;                         
			sql =  "insert into Candidates (name, specialties, presentation, rating) values (?,?,?,NULL)";
			preparedStatement = c.prepareStatement(sql);
			preparedStatement.setString(1,name);
			preparedStatement.setString(2,specialty);
			preparedStatement.setString(3,pres);
			preparedStatement.executeUpdate();    			                    
            c.close();
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if( c != null ) c.close();
            }
            catch( SQLException e )
            {
                e.printStackTrace();
            }
        }
        // send the user back to the guest book page
        response.sendRedirect( "Candidate");
	}

}