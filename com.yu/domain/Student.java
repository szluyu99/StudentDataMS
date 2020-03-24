package domain;

import javafx.beans.property.SimpleStringProperty;

public class Student {

    /** 学号ID（主键） **/
    private final SimpleStringProperty stuID;
    /** 班级 **/
    private final SimpleStringProperty stuClass;
    /** 姓名 **/
    private final SimpleStringProperty stuName;
    /** 性别**/
    private final SimpleStringProperty stuSex;
    /** 出生日期**/
    private final SimpleStringProperty stuBirth;
    /** 所在专业**/
    private final SimpleStringProperty stuMajor;

    public Student(String stuID,
                   String studClass,
                   String stuName,
                   String stuSex,
                   String stuBirth,
                   String stuMajor) {
        this.stuID = new SimpleStringProperty(stuID);
        this.stuClass = new SimpleStringProperty(studClass);
        this.stuName = new SimpleStringProperty(stuName);
        this.stuSex = new SimpleStringProperty(stuSex);
        this.stuBirth = new SimpleStringProperty(stuBirth);
        this.stuMajor = new SimpleStringProperty(stuMajor);
    }


    public String getStuID() {
        return stuID.get();
    }

    public SimpleStringProperty stuIDProperty() {
        return stuID;
    }

    public void setStuID(String stuID) {
        this.stuID.set(stuID);
    }

    public String getStuClass() {
        return stuClass.get();
    }

    public SimpleStringProperty stuClassProperty() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass.set(stuClass);
    }

    public String getStuName() {
        return stuName.get();
    }

    public SimpleStringProperty stuNameProperty() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName.set(stuName);
    }

    public String getStuSex() {
        return stuSex.get();
    }

    public SimpleStringProperty stuSexProperty() {
        return stuSex;
    }

    public void setStuSex(String stuSex) {
        this.stuSex.set(stuSex);
    }

    public String getStuBirth() {
        return stuBirth.get();
    }

    public SimpleStringProperty stuBirthProperty() {
        return stuBirth;
    }

    public void setStuBirth(String stuBirth) {
        this.stuBirth.set(stuBirth);
    }

    public String getStuMajor() {
        return stuMajor.get();
    }

    public SimpleStringProperty stuMajorProperty() {
        return stuMajor;
    }

    public void setStuMajor(String stuMajor) {
        this.stuMajor.set(stuMajor);
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuID=" + stuID +
                ", stuClass=" + stuClass +
                ", stuName=" + stuName +
                ", stuSex=" + stuSex +
                ", stuBirth=" + stuBirth +
                ", stuMajor=" + stuMajor +
                '}';
    }
}
