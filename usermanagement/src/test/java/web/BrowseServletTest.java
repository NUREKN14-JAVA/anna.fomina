package web;

//import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

//import org.junit.Before;
//import org.junit.Test;

import KN_14_5_Fomina.User;
import KN_14_5_Fomina.web.BrowseServlet;


public class BrowseServletTest extends MockServletTestCase {

	public void setUp() throws Exception {
		super.setUp();
		createServlet(BrowseServlet.class);
	}

	public void testBrowse() {

		User user = new User(666L, "James", "Spader", LocalDate.now());

		List<User> list = Collections.singletonList(user);
		getMockUserDao().expectAndReturn("findAll", list);
		doGet();

		Collection<?> collection = (Collection<?>) getWebMockObjectFactory().getMockSession().getAttribute("users");
		assertNotNull("Could not find collection of users in session", collection);
		assertSame(list,collection);
	}

	public void testEdit() {

	    User user = new User(666L, "James", "Spader", LocalDate.now());

	    getMockUserDao().expectAndReturn("find", 666L, user);
	    addRequestParameter("editButton", "Edit");
	    addRequestParameter("id", "666");
	    doPost();

	    User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
	    assertNotNull("Could not find user in session", user);
	    assertSame(user, userInSession);
	    
	}
	
	public void testDelete() {
	       
	       User user = new User(666L, "James", "Spader", LocalDate.now());
	       getMockUserDao().expectAndReturn("find", 666L, user);
	       getMockUserDao().expect("delete", user);
	       List<User> list = new ArrayList<User>();
	       
	       getMockUserDao().expectAndReturn("findAll", list);
	       
	       addRequestParameter("deleteButton", "Delete");
	       addRequestParameter("id", "666");
	       doPost();
	       
	       String deletionResult = (String) getWebMockObjectFactory().getMockSession().getAttribute("result");
	       assertNotNull("Deletion failed", deletionResult);
	       assertSame("ok", deletionResult);
	       
	       List<User> users = (List<User>) getWebMockObjectFactory().getMockSession().getAttribute("users");
	       assertNotNull("Couldn't find users in session", users);
	       assertSame(list,users);
	}
	
	public void testDetails() {
	    
	        User user = new User(666L, "James", "Spader", LocalDate.now());
	        getMockUserDao().expectAndReturn("find", 666L, user);
	        addRequestParameter("detailsButton", "Details");
	        addRequestParameter("id", "666");
	        doGet();
	        
	        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
	        assertNotNull("Could not find user in session", user);
	        assertSame(user, userInSession);
	        
	}

}