package dao.impl;

import dao.ICourseDao;
import domain.Course;
import util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements ICourseDao {
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
    public void save(Course course) {
        String sql = "insert into course(cID,cMajor,cName,cType,cStartTerm,cPeriod,cCredit) values (?,?,?,?,?,?,?)";
        executeUpdate(sql, course.getcID(), course.getcMajor(),course.getcName(), course.getcType(), course.getcStartTerm(), course.getcPeriod(), course.getcCredit());
    }

    @Override
    public void delete(int cId) {
        String sql = "delete from course where cID = ?";
        this.executeUpdate(sql, cId);
    }

    @Override
    public void update(int cId, Course course) {
        String sql = "update course set cMajor=?, cName=?, cType=?, cStartTerm=?,cPeriod=?, cCredit=? where cID =? ";
        this.executeUpdate(sql,course.getcMajor(), course.getcName(), course.getcType(), course.getcStartTerm(), course.getcPeriod(), course.getcCredit(), course.getcID());
        System.out.println(sql);
    }

    @Override
    public Course get(int cId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            String sql = "select * from course where cId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cId);
            // 4.执行语句
            rs = ps.executeQuery();
            if (rs.next()) {
                Course course = new Course(rs.getString("cId"),rs.getString("cMajor"),
                        rs.getString("cName"),rs.getString("cType"),rs.getString("cStartTerm"),
                        rs.getString("cPeriod"), rs.getString("cCredit"));
                return course;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.释放资源
            JdbcUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public List<Course> getAll() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            st = conn.createStatement();
            String sql = "select * from course ";
            System.out.println(sql);
            // 4.执行语句
            rs = st.executeQuery(sql);
            // 创建一个集合
            List<Course> list = new ArrayList<>();
            while (rs.next()) {
                Course course = new Course(
                        rs.getString("cID"),
                        rs.getString("cMajor"),
                        rs.getString("cName"),
                        rs.getString("cType"),
                        rs.getString("cStartTerm"),
                        rs.getString("cPeriod"),
                        rs.getString("cCredit"));
                list.add(course);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn, st, rs);
        }
        return null;
    }
}
