package dao;

import domain.Course;
import java.util.List;

public interface ICourseDao {
    /**
     * 保存一门课程
     */
    public void save(Course course);
    /**
     * 删除一门课程
     */
    public void delete(int cId);
    /**
     * 修改一门课程
     */
    public void update(int cId, Course course);
    /**
     * 查询某门课
     */
    public Course get(int cId);
    /**
     * 查询所有课
     */
    public List<Course> getAll();

}
