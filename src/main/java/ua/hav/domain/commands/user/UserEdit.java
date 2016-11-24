package ua.hav.domain.commands.user;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Юля on 12.08.2016.
 */
public class UserEdit implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("User edit.");
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("user", DAOFactory.getUserDAO().find(id));
        request.setAttribute("act", "Update user");
        return "regpage";
    }
}
