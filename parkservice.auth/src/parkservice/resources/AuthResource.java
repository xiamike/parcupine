package parkservice.resources;


/*
 * 1.  web xml to map service class package and url pattern
 * 2.  java pojo's for request and response
 * 3.  Resource class that has annotations and methods to call.  
 * 
 * */

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import com.parq.server.dao.UserDao;
import com.parq.server.dao.model.object.User;

import parkservice.model.AuthRequest;
import parkservice.model.AuthResponse;

@Path("/auth")
public class AuthResource {
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AuthResponse login(JAXBElement<AuthRequest> input){
		AuthRequest info = input.getValue();
		UserDao userDb = new UserDao();
		//getByEmail throws exception when user is not in db, aka rs=null.
		//thus user must be in db or this will throw exception.  
		User user = userDb.getUserByEmail(info.getEmail());

		/*  Dao layer needs to support these fields in table "User"
 			fname TEXT(64) NOT NULL, 
 			lname TEXT(64) NOT NULL,
 			parkstate TINYINT(1) NOT NULL,
 			parkloc POINT NOT NULL,
		 */

		if(user.getPassword().equals(info.getPassword())){
			//if the password for the email matches, return user info.  
			AuthResponse x = new AuthResponse();
			x.setFname(user.getEmail());
			x.setLname(user.getUserName());
			x.setPhone(""+user.getUserID());
			return x;
		}else{
			return null;
		}

	}

	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Auth Service";
	}
}
