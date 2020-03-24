package dao;

import domain.Student;
import java.util.List;

public interface IStudentDao {
    /**
     * 保存一个学生
     */
    public void save(Student stu);
    /**
     * 删除学生
     */
    public void delete(int sId);
    /**
     * 更新一个学生信息
     */
    public void update(int id,Student stu);
    /**
     * 获取指定学生
     */
    public Student get(int sId);
    /**
     * 获取所有的学生
     */
    public List<Student> getAll();
}
