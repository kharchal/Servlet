package ua.hav.domain.commands.book;

import ua.hav.dao.DAOFactory;
import ua.hav.domain.Book;
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
public class SaveBook implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Book book = (Book) Service.get(request, Book.class);
        Validator<Book> validator = new Validator<>();
        Map<String, String> errors = validator.validate(book, Book.class);
        if (errors.isEmpty()) {
            DAOFactory.getBookDAO().save(book);
            request.setAttribute("users", DAOFactory.getUserDAO().findAll());
            request.setAttribute("books", DAOFactory.getBookDAO().findAll());
            return "out";
        } else {
            request.setAttribute("book", book);
            request.setAttribute("errors", errors);
            request.setAttribute("act", "Save book");
            request.setAttribute("msg", "correct your entry");
            return "bookform";
        }
    }
}
