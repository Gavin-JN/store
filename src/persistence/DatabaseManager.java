package persistence;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private ComboPooledDataSource comboPooledDataSource ;

    public DatabaseManager() {}
    //默认处理的数据库设置为存储用户登录信息的数据库
//    public DatabaseManager() {this.comboPooledDataSource = new ComboPooledDataSource("");}

//    //设置要选择进行操作的数据库
//    public DatabaseManager(String dbname) {this.comboPooledDataSource = new ComboPooledDataSource(dbname);};

    public ComboPooledDataSource getComboPooledDataSource() {return comboPooledDataSource;}

    public void setComboPooledDataSource(ComboPooledDataSource comboPooledDataSource) {this.comboPooledDataSource = comboPooledDataSource;}

    //向数据库中推入信息
    public boolean insertInfor(String name,String password,String salt) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = comboPooledDataSource.getConnection();
            String sql = "INSERT INTO user (username, password, salt) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, salt);

            int result = ps.executeUpdate();
            return result > 0; // 如果结果大于0, 则表示插入成功

        } catch (SQLException e) {
            e.printStackTrace(); // 输出异常信息
            return false; // 在发生异常情况下返回false
        } finally {   //即使catch到了异常并return了，finally语句也会执行
            // 进行资源清理
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    //验证（输入的为用户名和未处理过（未加盐and未hash的密码）
    public boolean verityPassword(String name,String password) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = comboPooledDataSource.getConnection();
            String sql = "SELECT password ,salt FROM user WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            String salt = rs.getString("salt");
            //获得盐之后哈希密码
            HashOperate hp = new HashOperate();
            String hashedPassword= hp.SHA256Gener(password,salt);
            //将处理后的密码与数据库中获得的密码比较
            String pass=rs.getString("pass");
            return pass.equals(hashedPassword);
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    //检验用户名是否存在
    public boolean ifNameExit(String name) throws SQLException {
        String sql = "SELECT name FROM user WHERE username = ?";
        try (Connection conn = comboPooledDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e)
        {
            throw new RuntimeException("Database error while checking username existence.", e);
        }
    }

    //删除用户信息
    public boolean deleteUser(String name) throws SQLException {
        String sql = "DELETE FROM user WHERE username = ?";
        try (Connection conn = comboPooledDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, name);
            return ps.executeUpdate() > 0;

        }
        catch (SQLException e)
        {
            throw new RuntimeException("Database error while deleting user existence.", e);
        }
    }
}
