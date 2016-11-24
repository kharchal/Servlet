package ua.hav.domain.commands.genre;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.Genre;
import ua.hav.domain.commands.Command;
import ua.hav.domain.service.Service;
import ua.hav.domain.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Юля on 16.08.2016.
 */
public class UpdateGenre implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Genre genre = (Genre) Service.get(request, Genre.class);
        Map<String, String> errors = new Validator<Genre>().validate(genre, Genre.class);
        if (errors.isEmpty()) {
            DAOFactory.getGenreDAO().update(genre);
            request.setAttribute("genres", DAOFactory.getGenreDAO().findAll());
            return "genres";
        } else {
            request.setAttribute("act", "Update genre");
            request.setAttribute("genre", genre);
            request.setAttribute("errors", errors);
            request.setAttribute("msg", "correct your entries");
            return "genreform";
        }
    }
}
