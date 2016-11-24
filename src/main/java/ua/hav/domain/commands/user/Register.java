package ua.hav.domain.commands.user;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.User;
import ua.hav.domain.commands.Command;
import ua.hav.domain.service.Service;
import ua.hav.domain.validator.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Юля on 12.08.2016.
 */
public class Register implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) Service.get(request, User.class);
        Validator<User> validator = new Validator<>();
        Map<String, String> errors = validator.validate(user, User.class);
        String toGo = null;
        if (errors.isEmpty()) {
            DAOFactory.getUserDAO().save(user);
            request.setAttribute("users", DAOFactory.getUserDAO().findAll());
//            request.setAttribute("books", DAOFactory.getBookDAO().findAll());
            toGo = "index";
        } else {
            request.setAttribute("errors", errors);
            request.setAttribute("msg", "correct your entries:");
            request.setAttribute("act", "Register user");
            request.setAttribute("user", user);
            toGo = "regpage";
        }
        return toGo;
    }
}
