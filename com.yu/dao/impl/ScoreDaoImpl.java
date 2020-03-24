package dao.impl;

import dao.IScoreDao;
import domain.Score;
import util.JdbcUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScoreDaoImpl implements IScoreDao {
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
    public void save(int sId, int cId, Score score) {
        String sql = "insert into score(stuID, cID, score, credit) values (?,?,?,?)";
        executeUpdate(sql, score.getStuID(), score.getcID(), score.getScore(), score.getCredit());
    }

    @Override
    public void delete(int sId, int cId) {
        String sql = "delete from score where stuID = ? and cID = ?";
        this.executeUpdate(sql, sId, cId);
    }

    @Override
    public void update(int sId, Score score) {
        String sql = "update score set score=?, credit=? where stuId =? and cID=?";
        this.executeUpdate(sql, score.getScore(), score.getCredit(), sId, score.getcID());
    }

    @Override
    public Score get(int sId, int cId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            String sql = "select * from score where stuID = ? and cID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sId);
            ps.setInt(2, cId);
            // 4.执行语句
            rs = ps.executeQuery();
            if (rs.next()) {
                Score score = new Score(rs.getString("stuID"), rs.getString("cID"),
                        rs.getString("score"), rs.getString("credit"));
                return score;
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
    public List<Score> getAll() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            st = conn.createStatement();
            String sql = "select * from score ";
            System.out.println(sql);
            // 4.执行语句
            rs = st.executeQuery(sql);
            // 创建一个集合
            List<Score> list = new ArrayList<>();
            while (rs.next()) {
                Score score = new Score(
                        rs.getString("stuID"),
                        rs.getString("cID"),
                        rs.getString("score"),
                        rs.getString("credit"));

                list.add(score);
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
