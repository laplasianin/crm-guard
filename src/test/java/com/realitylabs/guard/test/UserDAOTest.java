package com.realitylabs.guard.test;

import com.crm.guard.dao.UserDAO;
import com.crm.guard.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.*;

import static org.mockito.Mockito.*;


@Ignore
public class UserDAOTest {
	
	private UserDAO userDAO;
	private SessionFactory sessionFactory;
	
	@BeforeClass
	public static void beforeClass() {
	}
	
	@AfterClass
	public static void afterClass() {
	}
	
	@Before
	public void setUp() {
		userDAO = new UserDAO();
		sessionFactory = mock(SessionFactory.class);
//		userDAO.sessionFactory = sessionFactory;
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testUpdateCalled() {
		Session sessionObj = mock(Session.class);
		User userOne = new User();
		userOne.setName("pablo");
		when(sessionFactory.getCurrentSession()).thenReturn(sessionObj);
		doNothing().when(sessionObj).update(any(User.class));
//		userDAO.update(userOne);
		verify(sessionObj).update(any(User.class));
	}
}
