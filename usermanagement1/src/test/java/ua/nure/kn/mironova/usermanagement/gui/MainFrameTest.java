package ua.nure.kn.mironova.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.kn.mironova.usermanagement.User;
import ua.nure.kn.mironova.usermanagement.db.DAOFactory;
import ua.nure.kn.mironova.usermanagement.db.DAOFactoryImpl;
import ua.nure.kn.mironova.usermanagement.db.MockDAOFactory;
import ua.nure.kn.mironova.usermanagement.db.MockUserDAO;

public class MainFrameTest extends JFCTestCase {
	private static final int COUNT_COLUMNS = 3;
	private Mock mockUserDAO;
	private List<User> users;
	private MainFrame mainFrame;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		try {
			Properties properties = new Properties();
			
			properties.setProperty("dao.factory", MockDAOFactory.class.getName());
			DAOFactory.init(properties);
			mockUserDAO = ((MockDAOFactory) DAOFactory.getInstance()).getMockUserDAO();
			mockUserDAO.expectAndReturn("findAll", new ArrayList());
			setHelper(new JFCTestHelper());
			mainFrame = new MainFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	@After
	protected void tearDown() throws Exception {
		try {
			mockUserDAO.verify();
			mainFrame.setVisible(false);
			getHelper();
			TestHelper.cleanUp(this);
			super.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Component find(Class<?> componentClass, 
			String name) {
		NamedComponentFinder finder;
		finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull("Couldn't find component '" + name + "'", component);
		return component;
	}

	@Test
	public void testBrowseControls() {
		find(JPanel.class, "browsePanel");
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(3, table.getColumnCount());
		assertEquals("UserTableModel.id", table.getColumnName(0)); 
		assertEquals("UserTableModel.first_name", table.getColumnName(1));
		assertEquals("UserTableModel.last_name", table.getColumnName(2));
		find(JButton.class, "addButton");
		find(JButton.class, "editButton");
		find(JButton.class, "deleteButton");
		find(JButton.class, "detailsButton");
		
	}
	
	@Test
	public void testAddUser() {
		String firstName = "John";
		String lastName = "Doe";
		Date now = new Date();
		User user = new User(firstName, lastName, now);
		User expectedUser = new User(new Long(1),firstName, lastName, now);
		mockUserDAO.expectAndReturn("create", user, expectedUser);
		
		ArrayList users = new ArrayList();
		users.add(expectedUser);
		mockUserDAO.expectAndReturn("findAll", users);
		
		JTable table = (JTable) find(JTable.class, "userTable");
		assertEquals(0, table.getRowCount());
		JButton addButton = (JButton) find(JButton.class, "addButton");
		getHelper().enterClickAndLeave(new MouseEventData(this, addButton));
		find(JPanel.class, "addPanel");
		JTextField firstNameField = (JTextField)find(JTextField.class, "firstNameField");
		JTextField lastNameField = (JTextField)find(JTextField.class, "lastNameField");
		JTextField dateOfBirthField = (JTextField)find(JTextField.class, "dateOfBirthField");
		JButton okButton = (JButton) find(JButton.class, "okButton");
		find(JButton.class, "cancelButton");
		
		getHelper().sendString(new StringEventData(this, firstNameField, firstName));
		
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		DateFormat formater = DateFormat.getDateInstance();
		
		String date = formater.format(now);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		
		getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
		find(JPanel.class, "browsePanel");
		table = (JTable) find(JTable.class, "userTable");
	}
}
