package ua.nure.kn.mironova.usermanagement.web;

import org.junit.Before;
import org.junit.Test;

public class DetailsServletTest extends MockServletTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		createServlet(DetailsServlet.class);
	}

	@Test
    public void testOkButton() {
        addRequestParameter("okButton", "Ok");
        doPost();
        assertNull(getWebMockObjectFactory().getMockRequest().getAttribute("error"));
    }

}
