package ua.nure.kn.mironova.usermanagement.web;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.kn.mironova.usermanagement.User;
import ua.nure.kn.mironova.usermanagement.db.DAOFactory;
import ua.nure.kn.mironova.usermanagement.db.DatabaseException;
import ua.nure.kn.mironova.usermanagement.db.UserDAO;

public class BrowseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getAttribute("addButton") != null) {
			add(req, resp);
		}else if (req.getAttribute("editButton") != null) {
			edit(req, resp);
		}else if (req.getAttribute("deleteButton") != null) {
			delete(req, resp);
		}else if (req.getAttribute("detailsButton") != null) {
			details(req, resp);
		}else {
			browse(req, resp);
		}
	}

	private void browse(HttpServletRequest req, HttpServletResponse resp) {
		Collection<User> users;
			try {
				users = DAOFactory.getInstance().getUserDAO().findAll();
				req.getSession().setAttribute("users", users);
				req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			} catch (DatabaseException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String idStr = req.getParameter("id");
		if (idStr == null || idStr.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		try {
			User user = DAOFactory.getInstance().getUserDAO().find(new Long(idStr));
			req.getSession().setAttribute("user", user);
		}catch(Exception e) {
			req.setAttribute("error", "ERROR:"+e.toString());
			req.getRequestDispatcher("/browser.jsp").forward(req,resp);
		}
		req.getRequestDispatcher("/details").forward(req, resp);
		
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id == null || id.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		try {
			UserDAO userDao = DAOFactory.getInstance().getUserDAO();
			User user = userDao.find(new Long(id));
			userDao.delete(user);
			req.setAttribute("message", user.getFullName() + " was deleted");
			browse(req, resp);
		} catch (Exception e) {
			req.setAttribute("error", e.toString());
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
	}

	private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idStr = req.getParameter("id");
		if (idStr == null || idStr.trim().length() == 0) {
			req.setAttribute("error", "You must select a user");
			req.getRequestDispatcher("/browse.jsp").forward(req, resp);
			return;
		}
		try {
			User user = DAOFactory.getInstance().getUserDAO().find(new Long(idStr));
			req.getSession().setAttribute("user", user);
		}catch(Exception e) {
			req.setAttribute("error", "ERROR:"+e.toString());
			req.getRequestDispatcher("/browser.jsp").forward(req,resp);
		}
		req.getRequestDispatcher("/edit").forward(req, resp);
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/add").forward(req, resp);
	}
	
}
