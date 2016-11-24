package ua.hav.domain.commands.book;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Юля on 19.08.2016.
 */
public class SearchByFieldsBook implements ua.hav.domain.commands.Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> names = Service.get(request);
        System.out.println("names = " + names);
        request.setAttribute("searchBook", names);
        request.setAttribute("users", DAOFactory.getUserDAO().findAll());
        request.setAttribute("books", DAOFactory.getBookDAO().findAllLike(names));
        return "out";
    }
}
