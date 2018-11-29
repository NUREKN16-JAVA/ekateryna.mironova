package ua.nure.kn.mironova.usermanagement.db;

import static org.junit.Assert.*;

import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.hsqldb.lib.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.mironova.usermanagement.User;

public class HsqlUserDAOTest extends DatabaseTestCase{
	private HsqldbUserDAO dao = new HsqldbUserDAO();
	private ConnectionFactory connectionFactory;
	
	private static final Long ID_ETALONE = 1000L;
	private static final String FIRST_NAME_ETALONE = "Bill";
	private static final String LAST_NAME_ETALONE = "Gates";
	private static final Date DATE_OF_BIRTH_ETALONE = new Date(68, 3, 26);
	
	private static final Long ID_UPDATE = 1001L;
	private static final String FIRST_NAME_UPDATE = "Katya";
	private static final String LAST_NAME_UPDATE = "Mironova";
	private static final Date DATE_OF_BIRTH_UPDATE = new Date(98, 6, 31);
	
	private static final Long ID_DELETE = 1000L;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao.setConnectionFactory(connectionFactory);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
     * Testing method for {@link HsqldbUserDAO#create(User)}
     * 
     * @throws DatabaseException
     */
	@Test
	public void testCreate() throws DatabaseException {
		User user = new User();
		user.setFirstName("Ivan");
		user.setLastName("Ivanov");
		user.setDateofBirth(new Date());
		assertNull(user.getId());
		User userCreated = dao.create(user);
		assertNotNull(userCreated);
		assertNotNull(userCreated.getId());
		assertEquals(user.getFirstName(),userCreated.getFirstName());
		assertEquals(user.getLastName(),userCreated.getLastName());
	}
	
	/**
     * Testing method for {@link HsqldbUserDAO#findAll()}
     * 
     * @throws DatabaseException
     */
	@Test
	public void testFindAll() throws DatabaseException {
		java.util.Collection<User> collection = dao.findAll();
		assertNotNull("Collection is null", collection);
		assertEquals("Collection size size doesn't match.", 2, collection.size());
	}
	
	/**
     * Testing method for {@link HsqldbUserDAO#find(Long)}
     * 
     * @throws DatabaseException
     */
	@Test
	public void testFind() throws DatabaseException {
		User user = dao.find(ID_ETALONE);
		assertNotNull("User not found", user);
		assertEquals("Wrong id", user.getId(), ID_ETALONE);
		assertEquals("Wrong first name",user.getFirstName(), FIRST_NAME_ETALONE);
		assertEquals("Wrong last name",user.getLastName(), LAST_NAME_ETALONE);
		assertEquals("Wrong date of birth",user.getDateofBirth(), DATE_OF_BIRTH_ETALONE);
	}
	
	/**
     * Testing method for {@link HsqldbUserDAO#update(User)}
     * 
     * @throws DatabaseException
     */
	@Test
	public void testUpdate() throws DatabaseException {
		User oldUser = dao.find(ID_UPDATE);
		oldUser.setFirstName(FIRST_NAME_UPDATE);
		oldUser.setLastName(LAST_NAME_UPDATE);
		oldUser.setDateofBirth(DATE_OF_BIRTH_UPDATE);
		dao.update(oldUser);
		
		User newUser = dao.find(ID_UPDATE);
        assertEquals(FIRST_NAME_UPDATE, newUser.getFirstName());
        assertEquals(LAST_NAME_UPDATE, newUser.getLastName());
        assertEquals(DATE_OF_BIRTH_UPDATE, newUser.getDateofBirth());
	}
	
	 /**
     * Testing method for {@link HsqldbUserDAO#delete(User)}
     * 
     * @throws DatabaseException
     */
	@Test
    public void testDelete() {
        User deletedUser = new User();
        deletedUser.setId(ID_DELETE);
        try {
            dao.delete(deletedUser);
            dao.find(ID_DELETE);
            fail();
        } catch (DatabaseException e) {
            assert(e.getMessage().contains(ID_DELETE.toString()));
        }
    }
	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl(
				"org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanagement", "sa", "");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass()
				.getClassLoader()
				.getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}
