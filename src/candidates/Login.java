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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession() != null) {
			request.getSession().invalidate();
		}		
		HttpSession session = request.getSession();		
		String user = (String)session.getAttribute("user");
		String signUp;
		String msg = null;
		if(request.getParameter("signUp") == null){
			signUp = "1";
		}
		else {
			signUp = (String)request.getParameter("signUp");
			if(request.getParameter("msg") != null) {
				if(request.getParameter("msg").equals("1")) {
					msg = "Username already taken. Please select a different username!";
				}
				else {
	        		msg = "Username or password didn't match!";
				}				
			}
				
		}			
		request.setAttribute("user", user);
		request.setAttribute("msg", msg);
		request.setAttribute("signUp", signUp);
		request.getRequestDispatcher("/WEB-INF/Login.jsp").forward( request, response );
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = (String)request.getParameter("name").trim().toLowerCase();
		String pass = (String)request.getParameter("pass");
		String action = (String)request.getParameter("hid");
		String msg; 
		String permissions = null;
		int users = 0;
		int userId = 0;
		if(!name.equalsIgnoreCase("") && pass != "") {
			if(name.equalsIgnoreCase("cysun") && pass.equals("abcd")) {				
				permissions = "admin";
			}
			else {
				permissions = "regular";
			}
		}
		
		Connection c = null;
        try
        {
            String url = "jdbc:mysql://localhost/cs3220stu20?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu20";
            String password = "bOHYxRHq";
            
            c = DriverManager.getConnection( url, username, password );
            PreparedStatement preparedStatement = null;
            String sql;    
            
            if(action.equalsIgnoreCase("create")) {               	
            	ResultSet rs;            	
            	sql =  "select * from Users where name = ?";
    			preparedStatement = c.prepareStatement(sql);
    			preparedStatement.setString(1,name);
    			rs = preparedStatement.executeQuery();    	
                while( rs.next() )
                {
                	users++;
                }
            	if(users >= 1) {
            		response.sendRedirect("Login?signUp=1&msg=1");
            	}
            	else {
            		sql =  "insert into Users (name, password, permissions) values (?,?,?)";
        			preparedStatement = c.prepareStatement(sql);
        			preparedStatement.setString(1,name);
        			preparedStatement.setString(2,pass);
        			preparedStatement.setString(3,permissions);
        			preparedStatement.executeUpdate();    
        			
        			sql =  "select * from Users where name = ?";
        			preparedStatement = c.prepareStatement(sql);
        			preparedStatement.setString(1,name);
        			rs = preparedStatement.executeQuery();    	        			
        			while( rs.next() )
    	            {
        				userId = rs.getInt("id");
    	            }
                    session.setAttribute("user", name);
    				session.setAttribute("permissions", permissions);
    				session.setAttribute("userId", userId);
                    c.close();                    
                    response.sendRedirect("Candidate");	
            	}    			
            }
            else {
            	ResultSet rs;
            	sql =  "select * from Users where name = ? and binary password = ?";
    			preparedStatement = c.prepareStatement(sql);
    			preparedStatement.setString(1,name);
    			preparedStatement.setString(2,pass);
    			rs = preparedStatement.executeQuery();    	
                while( rs.next() )
                {
                	users++;
                	userId = rs.getInt("id");
                }
                if(users == 1) {
                	session.setAttribute("user", name);
    				session.setAttribute("permissions", permissions);	
    				session.setAttribute("userId", userId);
                	c.close();
        			response.sendRedirect("Candidate");		
                }
                else{
                	c.close();
            		response.sendRedirect("Login?signUp=0&msg=0");
                }                
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
	}

}