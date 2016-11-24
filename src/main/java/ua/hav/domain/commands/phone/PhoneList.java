package ua.hav.domain.commands.phone;

import ua.hav.dao.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Юля on 24.08.2016.
 */
public class PhoneList implements ua.hav.domain.commands.Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("phones", DAOFactory.getPhoneDAO().findAll());
        return "phones";
    }
}
