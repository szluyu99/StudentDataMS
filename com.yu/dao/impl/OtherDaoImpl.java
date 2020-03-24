package dao.impl;

import dao.IotherDao;
import domain.Course;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OtherDaoImpl implements IotherDao {

    public int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            ps = conn.prepareStatement(sql);
            // 遍历参数
            for (int i = 0; i < params.length; i++) {
                // ps.setString(1, stu.getName());
                // ps.setInt(2, stu.getAge());
                ps.setObject(i + 1, params[i]);

            }
            // 4.执行语句
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.释放资源
            JdbcUtil.close(conn, ps, null);
        }
        return 0;
    }


    @Override
    public double statistic(int sId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            String sql = "select SUM(credit) FROM score WHERE stuID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sId);
            // 4.执行语句
            rs = ps.executeQuery();
            if (rs.next()) {
                double res = rs.getDouble("SUM(credit)");
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.释放资源
            JdbcUtil.close(conn, ps, rs);
        }
        return 0;
    }

    @Override
    public int analysis(int stuClass, int cId, int minScore, int maxScore) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            String sql = "SELECT COUNT(*) num FROM score\n" +
                    "WHERE stuID IN\n" +
                    "(SELECT stuID FROM student\n" +
                    "WHERE stuClass = ?)\n" +
                    "AND score >= ?\n" +
                    "AND score < ?  \n" +
                    "And cID=?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, stuClass);
            ps.setInt(2, minScore);
            ps.setInt(3, maxScore);
            ps.setInt(4, cId);
            // 4.执行语句
            rs = ps.executeQuery();
            if (rs.next()) {
                int res = rs.getInt("num");
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.释放资源
            JdbcUtil.close(conn, ps, rs);
        }
        return 0;
    }
}
