package ua.hav.domain.commands.user;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.commands.Command;
import ua.hav.domain.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Юля on 18.08.2016.
 */
public class FilterUser implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> names = Service.get(request);
        System.out.println("names = " + names);
        request.setAttribute("searchUser", names);
        request.setAttribute("users", DAOFactory.getUserDAO().findAllLike(names));
//        request.setAttribute("books", DAOFactory.getBookDAO().findAll());
        return "users";
    }
}
