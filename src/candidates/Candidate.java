package candidates;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Candidate
 */
@WebServlet("/Candidate")
public class Candidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Candidate() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CandidateEntry> entries = new ArrayList<>();
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("user");
		String permissions = (String) session.getAttribute("permissions");
		Connection c = null;
        try
        {
            String url = "jdbc:mysql://localhost/cs3220stu20?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu20";
            String password = "bOHYxRHq";
            
            c = DriverManager.getConnection( url, username, password ); 
            ResultSet rs;
            Statement stmt;
                        		
            stmt = c.createStatement();
            rs = stmt.executeQuery( "select * from Candidates" ); 
            while( rs.next() )
            {
            	entries.add(new CandidateEntry(rs.getInt("id"),rs.getString("name"),rs.getString("specialties"),rs.getString("presentation"),rs.getString("rating")));
            }
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
		
        request.setAttribute("entries", entries);
        request.setAttribute("user", user);
        request.setAttribute("permissions", permissions);
        request.getRequestDispatcher("/WEB-INF/DisplayCandidates.jsp").forward( request, response );

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}