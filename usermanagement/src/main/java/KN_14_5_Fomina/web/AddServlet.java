package KN_14_5_Fomina.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import KN_14_5_Fomina.User;
import KN_14_5_Fomina.db.DaoFactory;
import KN_14_5_Fomina.db.DatabaseException;

public class AddServlet extends EditServlet {

    private static final long serialVersionUID = 1L;
    
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().create(user);
    }
    
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }

}