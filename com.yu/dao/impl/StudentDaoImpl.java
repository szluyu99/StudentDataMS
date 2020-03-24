package dao.impl;

import dao.IStudentDao;
import util.JdbcUtil;
import domain.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements IStudentDao {
    /**
     * 1.设计一个方法 2.要求传入两个参数 一个sql语句 一个参数 第一个参数sql语句模板 第二个参数为可变参数，设置语句参数值 3.返回值
     * 返回值为int，受影响的行数。 图示 DAO重构示意图
     * 调用示意图
     * 可变参数的本质是一个数组
     */
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
    public void save(Student stu) {
        String sql = "insert into student(stuID,stuClass,stuName,stuSex,stuBirth,stuMajor) values (?,?,?,?,?,?)";
        this.executeUpdate(sql, stu.getStuID(), stu.getStuName(), stu.getStuClass(), stu.getStuSex(), stu.getStuBirth(),stu.getStuMajor());
    }

    @Override
    public void delete(int sId) {
        String sql = "delete from student where stuID = ?";
        this.executeUpdate(sql, sId);
    }

    @Override
    public void update(int sId, Student stu) {
        String sql = "update student set stuClass=?, stuName=?, stuSex=?,stuBirth=?,stuMajor=? where stuId =? ";
        this.executeUpdate(sql, stu.getStuClass(), stu.getStuName(), stu.getStuSex(),
                stu.getStuBirth(), stu.getStuMajor(), sId);
    }

    @Override
        public Student get(int sId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            String sql = "select * from student where stuID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sId);
            // 4.执行语句
            rs = ps.executeQuery();
            if (rs.next()) {
                Student stu = new Student(rs.getString("stuID"),rs.getString("stuClass"),
                        rs.getString("stuName"),rs.getString("stuSex"),rs.getString("stuBirth"),
                        rs.getString("stuMajor"));
                return stu;
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
    public List<Student> getAll() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            st = conn.createStatement();
            String sql = "select * from student ";
            System.out.println(sql);
            // 4.执行语句
            rs = st.executeQuery(sql);
            // 创建一个集合
            List<Student> list = new ArrayList<Student>();
            while (rs.next()) {
                Student stu = new Student(
                        rs.getString("stuID"),
                        rs.getString("stuClass"),
                        rs.getString("stuName"),
                        rs.getString("stuSex"),
                        rs.getString("stuBirth"),
                        rs.getString("stuMajor"));
                list.add(stu);
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
