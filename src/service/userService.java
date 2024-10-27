package service;

import domain.User;
import persistence.userDao;

import java.sql.SQLException;

public class userService {
     private userDao dao;

     public userService() {dao = new userDao();}

     public int  login(User user) throws SQLException {
         return dao.verifyUser(user);
     }

     public int signUp(User user) throws SQLException {
         return dao.verifyUser(user);
     }
}
