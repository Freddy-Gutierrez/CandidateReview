package candidates;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Feedback")
public class Feedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Feedback() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int userId;
		String user;
		String permissions = null;
		if(session.getAttribute("permissions") == null) {
			userId = 0;
			user = "unauthenticated";
			session.setAttribute("user", user);
		}
		else {
			userId = (int) session.getAttribute("userId");
			user = (String) session.getAttribute("user");
			permissions = (String)session.getAttribute("permissions");
		}
			int id = Integer.parseInt(request.getParameter("id"));
			int numOfComments = 0;
			CandidateEntry cand = null;
			List<Comments> comments = new ArrayList<>();
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
		                        
					sql =  "select * from Comments where parentId = ?";
					preparedStatement = c.prepareStatement(sql);
					preparedStatement.setInt(1,id);
					rs = preparedStatement.executeQuery(); 
		            while( rs.next() )
		            {
		            	comments.add(new Comments(rs.getInt("id"),rs.getString("name"),rs.getString("rating"),rs.getString("com"),rs.getTimestamp("date")));
		            }
		            
		            sql =  "select * from Candidates where id = ?";
					preparedStatement = c.prepareStatement(sql);
					preparedStatement.setInt(1, id);    			
					rs = preparedStatement.executeQuery();    			
		   			
		            while( rs.next() )
		            {
		            	cand = new CandidateEntry(rs.getInt("id"),rs.getString("name"),rs.getString("specialties"),rs.getString("presentation"),rs.getString("rating"));
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
			numOfComments = comments.size();	
	        request.setAttribute("numOfComments", numOfComments);
			request.setAttribute("comments", comments);
			request.setAttribute("cand", cand);
			request.setAttribute("user", user);
			request.setAttribute("userId", userId);
			request.setAttribute("permissions", permissions);
			request.setAttribute("parentId", id);
		    request.getRequestDispatcher("/WEB-INF/Feedback.jsp").forward( request, response );
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    if(session.getAttribute("permissions") == null) {
	    	request.setAttribute("permissions", null);
			request.getRequestDispatcher("/WEB-INF/Denied.jsp").forward( request, response );
	    }
			
	    else {
		int userId = 0;
		int userComments = 0;
		if(session.getAttribute("userId") != null) {
			userId = (int) session.getAttribute("userId");
		}
		List<Comments> comments = new ArrayList<>();
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
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
	            
	            ResultSet rs;                
				sql =  "select * from Comments where id = ? and parentId = ?";
				preparedStatement = c.prepareStatement(sql);
				preparedStatement.setInt(1,userId);
				preparedStatement.setInt(2,id);
				rs = preparedStatement.executeQuery(); 
	            while( rs.next() )
	            {
	            	userComments++;
	            }
	            if(userComments < 1) {
		            sql =  "insert into Comments (id, name, rating, com, date, parentId) values (" + userId +  ",?,?,?,now(),?)";
					preparedStatement = c.prepareStatement(sql);
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, rating);  
					preparedStatement.setString(3, comment);  
					preparedStatement.setInt(4, id);  
					preparedStatement.executeUpdate();
					                       
					//update
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
	        if(userComments >= 1) {
				request.setAttribute("user", request.getAttribute("user"));
				request.setAttribute("permissions", request.getAttribute("permissions"));
				request.getRequestDispatcher("/WEB-INF/CommentLimit.jsp").forward( request, response );
	        }
	        else {       
	        	response.sendRedirect("Candidate");
	        }	
	    }
	}

}
