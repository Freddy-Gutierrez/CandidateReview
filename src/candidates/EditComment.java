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

@WebServlet("/EditComment")
public class EditComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditComment() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		request.setAttribute("parentId", parentId);
		request.setAttribute("rating", request.getParameter("rating"));
		request.setAttribute("com", request.getParameter("com"));
		request.setAttribute("user", request.getAttribute("user"));
		request.setAttribute("permissions", request.getAttribute("permissions"));
		request.getRequestDispatcher("/WEB-INF/EditComment.jsp").forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
		int id = Integer.parseInt(request.getParameter("id"));
		int userId = (int) session.getAttribute("userId");
		String comment = request.getParameter("comments");
		String rating = request.getParameter("rate");
        double avgRating = 0;
		int numOfComments = 0;
		 Connection c = null;
	        try
	        {
	            String url = "jdbc:mysql://localhost/cs3220stu20?useSSL=false&allowPublicKeyRetrieval=true";
	            String username = "cs3220stu20";
	            String password = "bOHYxRHq";	            
	            c = DriverManager.getConnection( url, username, password );
	            PreparedStatement preparedStatement = null;
	            String sql; 
	            sql =  "update Comments set rating = ?, com = ?, date = now() where id = ? and parentId = ?";
				preparedStatement = c.prepareStatement(sql);		
				preparedStatement.setString(1, rating);
				preparedStatement.setString(2, comment);
				preparedStatement.setInt(3, userId);
				preparedStatement.setInt(4, id);							
				preparedStatement.executeUpdate();    			            
	            

	            ResultSet rs;         
				sql =  "select * from Comments where parentId = ?";
				preparedStatement = c.prepareStatement(sql);
				preparedStatement.setInt(1,id);
				rs = preparedStatement.executeQuery(); 
				while( rs.next() )
	            {
					numOfComments++;
	            	avgRating += Double.parseDouble(rs.getString("rating"));
	            }	           
	    		avgRating = Math.round(avgRating/numOfComments*10.0)/10.0;
	    		sql =  "update Candidates set rating = ? where id = ?";
				preparedStatement = c.prepareStatement(sql);		
				preparedStatement.setString(1, Double.toString(avgRating));
				preparedStatement.setInt(2, id);
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
	        response.sendRedirect("Candidate");
	}

}