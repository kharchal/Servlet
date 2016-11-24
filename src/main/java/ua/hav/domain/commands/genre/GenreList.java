package ua.hav.domain.commands.genre;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.Genre;
import ua.hav.domain.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Юля on 16.08.2016.
 */
public class GenreList implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Genre> all = DAOFactory.getGenreDAO().findAll();
        System.out.println(all);
        System.out.println(DAOFactory.toString1());
        request.setAttribute("genres", all);
        return "genres";
    }
}
