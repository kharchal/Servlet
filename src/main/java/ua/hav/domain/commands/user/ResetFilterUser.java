package ua.hav.domain.commands.user;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Юля on 19.08.2016.
 */
public class ResetFilterUser implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setAttribute("books", DAOFactory.getBookDAO().findAll());
        request.setAttribute("users", DAOFactory.getUserDAO().findAll());
        return "users";
    }
}
