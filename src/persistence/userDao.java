package persistence;

import domain.User;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class userDao {
    private DatabaseManager dbmanager = new DatabaseManager();

    //将新注册的用户信息插入数据库的一系列操作
    //返回值：
    //-1： 该用户名已存在
    // 0： 用户信息插入数据库失败
    // 1： 用户信息成功插入
    public int  insertUser(User user) {
        boolean ifUsernameExit = false;
        try {
            ifUsernameExit = dbmanager.ifNameExit(user.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (ifUsernameExit) {
            //提醒该用户名已存在
            return -1;
        }
        else {
            //将密码加盐并哈希处理
            HashOperate hop = new HashOperate();
            //随机盐值
            String salt = hop.saltGener();
            String hashedPassword;
            try {
                hashedPassword = hop.SHA256Gener(user.getPassword(), salt);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            boolean ifInsertSuccess;
            try {
                ifInsertSuccess = dbmanager.insertInfor(user.getUsername(), hashedPassword, salt);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (ifInsertSuccess) {
                System.out.println("insert success");
                return 1;
            } else {
                System.out.println("insert fail");
                return 0;
            }
        }
    }

    //验证用户的登录信息
    //返回值：
    //-1： 账户存在，但输入的密码错误
    //0 :  账户不存在
    //1 ： 信息正确
    public int  verifyUser(User user) throws SQLException {

        if(dbmanager.ifNameExit(user.getUsername()))
        {
            boolean ifPasswordTrue=false;
            ifPasswordTrue= dbmanager.verityPassword(user.getUsername(), user.getPassword());
            if(ifPasswordTrue)
                return 1;
            else
                return -1;
        }
        //用户名不存在
        else {
            return 0;
        }

    }
}