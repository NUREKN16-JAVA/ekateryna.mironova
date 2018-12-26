package ua.nure.kn.mironova.usermanagement.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn.mironova.usermanagement.User;
import ua.nure.kn.mironova.usermanagement.db.DAOFactory;
import ua.nure.kn.mironova.usermanagement.db.DatabaseException;

public class AddServlet extends EditServlet {

	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/add.jsp").forward(req, resp);
	}

	@Override
	protected void processUser(User user) throws DatabaseException {
		DAOFactory.getInstance().getUserDAO().create(user);
	}

}
