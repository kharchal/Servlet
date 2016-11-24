package ua.hav.domain.commands.genre;

import ua.hav.domain.Genre;
import ua.hav.domain.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Юля on 16.08.2016.
 */
public class GenreForm implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("act", "Save genre");
        request.setAttribute("genre", new Genre());
        return "genreform";
    }
}
