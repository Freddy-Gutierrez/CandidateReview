package candidates;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class EditCandidate
 */
@WebServlet("/EditCandidate")
public class EditCandidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditCandidate() {
        super();       
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("permissions") != null && ((String) session.getAttribute("permissions")).equalsIgnoreCase("admin")){
			// get the entry to be edited
	        Integer id = Integer.valueOf( request.getParameter( "id" ) );	        
	        CandidateEntry entry = null;
	        Connection c = null;
	        try
	        {
	            String url = "jdbc:mysql://localhost/cs3220stu20?useSSL=false&allowPublicKeyRetrieval=true";
	            String username = "cs3220stu20";
	            String password = "bOHYxRHq";
	            
	            c = DriverManager.getConnection( url, username, password );
	            PreparedStatement preparedStatement = null;
	            String sql; 
	            ResultSet rs;                        
				sql =  "select * from Candidates where id = ?";
				preparedStatement = c.prepareStatement(sql);
				preparedStatement.setInt(1, id);    			
				rs = preparedStatement.executeQuery();    			
	   			
	            while( rs.next() )
	            {
	            	entry = new CandidateEntry(rs.getInt("id"),rs.getString("name"),rs.getString("specialties"),rs.getString("presentation"),rs.getString("rating"));
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
	        request.setAttribute("entry", entry);
	        request.setAttribute("user", request.getAttribute("user"));
            request.setAttribute("permissions", request.getAttribute("permissions"));
		    request.getRequestDispatcher("/WEB-INF/DisplayEdit.jsp").forward( request, response );
		}
		else {
			request.setAttribute("user", request.getAttribute("user"));
            request.setAttribute("permissions", request.getAttribute("permissions"));
			request.getRequestDispatcher("/WEB-INF/Denied.jsp").forward( request, response );
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get the entry to be edited
        Integer id = Integer.valueOf( request.getParameter( "id" ) );
        Connection c = null;
        try
        {
            String url = "jdbc:mysql://localhost/cs3220stu20?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu20";
            String password = "bOHYxRHq";
            
            c = DriverManager.getConnection( url, username, password );
            PreparedStatement preparedStatement = null;
            String sql;                         
			sql =  "update Candidates set name = ?, specialties = ?, presentation = ? where id = ?";
			preparedStatement = c.prepareStatement(sql);		
			preparedStatement.setString(1, request.getParameter( "name" ));
			preparedStatement.setString(2, request.getParameter( "special" ));
			preparedStatement.setString(3, request.getParameter( "pres" ));
			preparedStatement.setInt(4, id);			
			
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

        request.getRequestDispatcher("Candidate").forward( request, response );
	}

}