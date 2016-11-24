package ua.hav.domain.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Юля on 11.08.2016.
 */
public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
