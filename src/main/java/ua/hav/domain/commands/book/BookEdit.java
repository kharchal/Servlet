package ua.hav.domain.commands.book;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Юля on 12.08.2016.
 */
public class BookEdit implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("id = " + id);
        request.setAttribute("book", DAOFactory.getBookDAO().find(id));
        request.setAttribute("act", "Update book");
        return "bookform";

    }
}
