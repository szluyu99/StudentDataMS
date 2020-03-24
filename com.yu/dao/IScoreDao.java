package dao;

import domain.Score;
import java.util.List;

public interface IScoreDao {
    /**
     * 增加学生成绩
     */
    public void save(int sId, int cId,Score score);
    /**
     * 删除学生成绩
     */
    public void delete(int sId, int cId);
    /**
     * 更新学生成绩
     */
    public void update(int sId, Score score);
    /**
     * 根据学号获取学生成绩
     */
    public Score get(int sId, int cId);
    /**
     * 获取全部学生成绩
     */
    public List<Score> getAll();

}
