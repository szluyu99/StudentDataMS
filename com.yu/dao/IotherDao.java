package dao;

public interface IotherDao {
    // 统计功能：统计学生某个学期或所有学期课程的总学分。
    public double statistic(int sId);

    // 分析功能：对某一个班级的某一门课程的成绩分布进行分析，并以直方图的形式显示出来
    public int analysis(int stuClass, int cId, int minScore, int maxScore);
}
