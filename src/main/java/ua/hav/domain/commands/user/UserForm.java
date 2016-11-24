package ua.hav.domain.commands.user;

import ua.hav.domain.User;
import ua.hav.domain.commands.Command;

import javax.imageio.IIOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Юля on 12.08.2016.
 */
public class UserForm implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IIOException {
        request.setAttribute("act", "Register user");
        request.setAttribute("user", new User("your login:", "your password:", "your name:", 10, "g"));
        return "regpage";
    }
}
