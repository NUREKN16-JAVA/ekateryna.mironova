package ua.nure.kn.mironova.usermanagement;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUser {
	private User user;
	
	private static final String FULL_NAME_ETALONE = "Ivanov, Ivan";
	private static final String LAST_NAME = "Ivanov";
	private static final String FIRST_NAME = "Ivan";
	
	//Тест актуальный на дату 14.10.2018
	private static final int CURRENT_YEAR = 2018;
	private static final int YEAR_OF_BIRTH = 1998;
	
	// Тест Возраста 1 - для случая, где день рождения уже прошел,
	// но месяц еще идет в этом году
	private static final int ETALONE_AGE_1 = CURRENT_YEAR - YEAR_OF_BIRTH;
	private static final int DAY_OF_BIRTH_1 = 10;
	private static final int MONTH_OF_BIRTH_1 = 10;
	
	@Test public void testGetAge1() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_1, DAY_OF_BIRTH_1);
		user.setDateofBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_1, user.getAge());
	}
		
	// Тест Возраста 2 - для случая, где день рождения был вчера
	private static final int ETALONE_AGE_2 = CURRENT_YEAR - YEAR_OF_BIRTH;
	private static final int DAY_OF_BIRTH_2 = 13;
	private static final int MONTH_OF_BIRTH_2 = 10;
		
	@Test public void testGetAge2() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_2, DAY_OF_BIRTH_2);
		user.setDateofBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_2, user.getAge());
	}
		
	// Тест Возраста 3 - для случая, где день рождения сегодня
	private static final int ETALONE_AGE_3 = CURRENT_YEAR - YEAR_OF_BIRTH;
	private static final int DAY_OF_BIRTH_3 = 14;
	private static final int MONTH_OF_BIRTH_3 = 10;
			
	@Test public void testGetAge3() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_3, DAY_OF_BIRTH_3);
		user.setDateofBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_3, user.getAge());
	}
		
	// Тест Возраста 4 - для случая, где день рождения был в предыдущем месяце
	private static final int ETALONE_AGE_4 = CURRENT_YEAR - YEAR_OF_BIRTH;
	private static final int DAY_OF_BIRTH_4 = 14;
	private static final int MONTH_OF_BIRTH_4 = 9;
				
	@Test public void testGetAge4() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_4, DAY_OF_BIRTH_4);
		user.setDateofBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_4, user.getAge());	
	}
	
	// Тест Возраста 5 - для случая, где день рождения будет в следующем месяце
	private static final int ETALONE_AGE_5 = CURRENT_YEAR - YEAR_OF_BIRTH - 1;
	private static final int DAY_OF_BIRTH_5 = 14;
	private static final int MONTH_OF_BIRTH_5 = 11;
					
	@Test public void testGetAge5() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_5, DAY_OF_BIRTH_5);
		user.setDateofBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_5, user.getAge());	
	}
	
	// Тест Возраста 6 - для случая, где день рождения будет в текущем месяце
	private static final int ETALONE_AGE_6 = CURRENT_YEAR - YEAR_OF_BIRTH - 1;
	private static final int DAY_OF_BIRTH_6 = 20;
	private static final int MONTH_OF_BIRTH_6 = 10;
					
	@Test public void testGetAge6() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_6, DAY_OF_BIRTH_6);
		user.setDateofBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_6, user.getAge());	
	}
	
	// Тест Возраста 7 - для случая, где дата рождения - сегодняшний день
	private static final int ETALONE_AGE_7 = 0;
	private static final int DAY_OF_BIRTH_7 = 14;
	private static final int MONTH_OF_BIRTH_7 = 10;
					
	@Test public void testGetAge7() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(CURRENT_YEAR, MONTH_OF_BIRTH_7, DAY_OF_BIRTH_7);
		user.setDateofBirth(calendar.getTime());
		assertEquals(ETALONE_AGE_7, user.getAge());	
	}
	
	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetFullName() {
		user.setFirstName(FIRST_NAME);
		user.setLastName(LAST_NAME);
		assertEquals(FULL_NAME_ETALONE, (String) (user.getFullName()));
	}

}