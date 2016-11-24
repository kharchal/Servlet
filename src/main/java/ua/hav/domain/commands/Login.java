package ua.hav.domain.commands;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Юля on 12.08.2016.
 */
public class Login implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        System.out.println(login + ", " + password);
        boolean res = DAOFactory.getUserDAO().login(login, password);
        System.out.println(res);
        String goTo = null;
        if (res) {
            List<User> user = DAOFactory.getUserDAO().findAllLike("login", login);
            System.out.println("user = " + user);
            User logged = user.get(1);
            request.getSession().setAttribute("logged", logged);
//            request.setAttribute("searchUser", null);
//            request.setAttribute("searchBook", null);
//            List<User> users = DAOFactory.getUserDAO().findAll();
//            users.add(0, new User());
//            System.out.println("users = " + users);
//            request.setAttribute("users", users);
//            request.setAttribute("books", DAOFactory.getBookDAO().findAll());
//            System.out.println("books = " + request.getAttribute("books"));
            goTo = "+main";
        } else {
            request.setAttribute("msg", "Incorrect login/password!");
            goTo = "index";
        }
        return goTo;
    }
}
