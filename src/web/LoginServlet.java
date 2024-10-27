package web;

import domain.User;
import persistence.userDao;
import service.userService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

//处理登录操作
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private String username;
    private String password;
    private User user;
    private userService  userService;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        username=request.getParameter("username");
        password=request.getParameter("password");
        user=new User(username,password);
        int result=-2;
        try {
            result=userService.login(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result==-2){
            System.out.println("error In login in servlet");
        }
        //账户存在，密码错误
        else if (result==-1) {

        }
        //账户不存在
        else if(result==0){

        }
        //信息正确
        else {
            response.sendRedirect("success.jsp");
        }
    }

}
//处理注册操作
@WebServlet("/signUp")
class SignUpServlet extends  HttpServlet{
    private String username=null;
    private String password=null;
    private User user;
    private userService userService;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        username=request.getParameter("username");
        password=request.getParameter("password");
        user=new User(username,password);
        int result=-2;
        try {
            result=userService.signUp(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(result==-2) {
            System.out.println("error In signUp in servlet");
        }
        //该用户名已经存在
        else if (result==-1) {

        }
        //数据库插入失败
        else if (result==0) {
            System.out.println("error In signUp in servlet (insert error)");
        }
        //信息成功插入，此时应返回登录界面并提示注册成功
        else {
            //此处还应提示用户注册成功
            response.sendRedirect("login.jsp");
        }
    }
}
