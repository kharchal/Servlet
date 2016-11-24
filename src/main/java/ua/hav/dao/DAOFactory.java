package ua.hav.dao;

import org.apache.log4j.Logger;
import ua.hav.domain.Book;
import ua.hav.domain.Genre;
import ua.hav.domain.Phone;
import ua.hav.domain.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {
	private static final Logger LOGGER = Logger.getLogger(DAOFactory.class);

	private static Dao bookDao;
	private static UserDao userDao;
	private static Dao genreDao;
	private static Dao phoneDao;

	public static Dao getDao(String type) {
		System.out.println("Dao getDao type= " + type);

		switch (type) {
			case "Book" : return getBookDAO();
			case "User" : return getUserDAO();
			case "Genre" : return getGenreDAO();
			case "Phone" : return getPhoneDAO();
			default:
		}
		return null;
	}

	public static Dao<Phone> getPhoneDAO() {
		if (phoneDao == null) {
			phoneDao = new Dao<Phone>(Phone.class);
		}
		LOGGER.info("Phone Dao is ready to use");
		return phoneDao;
	}

	public static UserDao getUserDAO() {
		if (userDao == null) {
			userDao = new UserDao(User.class);
		}
	  	LOGGER.info(" userDAO is ready to use.");
	  	return userDao;
	}

	public static Dao<Book> getBookDAO() {
		if (bookDao == null) {
			bookDao = new Dao<Book>(Book.class);
		}
		return bookDao;
	}

	public static Dao<Genre> getGenreDAO() {
		if (genreDao == null) {
			genreDao = new Dao<Genre>(Genre.class);
		}
		return genreDao;
	}
//
//	public static IBookDAO getBookDAO() {
//		LOGGER.info(" bookDAO is ready to use.");
//		return new BookDAO();
//	}
//
//	public static IOrderDAO getOrderDAO() {
//		return new OrderDAO();
//	}
//
//	public static IEmailDAO getEmailDAO() {
//		return new EmailDAO();
//	}
//
//	public static IPhoneDAO getPhoneDAO() {
//		return new PhoneDAO();
//	}
private static DataSource dataSource;

	static {
		InitialContext ic;
		try {
			ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/dblibrary");
			LOGGER.info("Data source initiated successfuly.");
		} catch (NamingException e) {
			e.printStackTrace();
			LOGGER.fatal("Data source initiation has failed.");
			System.exit(0);
		}
	}

	public static Connection getConnection() {
		Connection c = null;
		try {
			c = dataSource.getConnection();
		} catch (SQLException e) {
			LOGGER.warn("Connection is not obtained!");
			e.printStackTrace();
		}
		if (c == null) {
			LOGGER.warn("Connection is not settled!");
			System.exit(0);
		}
		return c;
	}

	public static String toString1() {
		return "DAOFactory" +
				" bookDao " + bookDao +
				" userDao " + userDao +
				" genreDao " +genreDao ;
	}
}
