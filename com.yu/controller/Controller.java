package controller;

import dao.impl.CourseDaoImpl;
import dao.impl.OtherDaoImpl;
import dao.impl.ScoreDaoImpl;
import dao.impl.StudentDaoImpl;
import domain.Course;
import domain.Score;
import domain.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Tab tab_stu;
    @FXML
    Tab tab_course;
    @FXML
    Tab tab_score;
    @FXML
    TableView studentTableView;
    @FXML
    TableView courseTableView;
    @FXML
    TableView scoreTableView;
    @FXML
    TableColumn stuIDCol;
    @FXML
    TableColumn stuClassCol;
    @FXML
    TableColumn stuNameCol;
    @FXML
    TableColumn stuSexCol;
    @FXML
    TableColumn stuBirCol;
    @FXML
    TableColumn stuMajorCol;

    @FXML
    TableColumn cIdCol;
    @FXML
    TableColumn cMajorCol;
    @FXML
    TableColumn cNameCol;
    @FXML
    TableColumn cTypeCol;
    @FXML
    TableColumn cStartTremCol;
    @FXML
    TableColumn cPeriodCol;
    @FXML
    TableColumn cCreditCol;

    @FXML
    TableColumn score_sIdCol;
    @FXML
    TableColumn score_cIdCol;
    @FXML
    TableColumn scoreCol;
    @FXML
    TableColumn score_creditCol;

    /**
     * 添加学生
     */
    public void addStudent() {
        Dialog<StudentResults> dialog = new Dialog<>();
        dialog.setTitle("添加学生");
        dialog.setHeaderText("请在下面输入要添加的学生信息：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField sClass = new TextField();
        TextField sName = new TextField();
        TextField sSex = new TextField();
        TextField sBirth = new TextField();
        TextField sMajor = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("班级:"), 0, 1);
        grid.add(sClass, 1, 1);
        grid.add(new Label("姓名:"), 0, 2);
        grid.add(sName, 1, 2);
        grid.add(new Label("性别:"), 0, 3);
        grid.add(sSex, 1, 3);
        grid.add(new Label("出生日期:"), 0, 4);
        grid.add(sBirth, 1, 4);
        grid.add(new Label("所在专业:"), 0, 5);
        grid.add(sMajor, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new StudentResults(sID.getText(),sClass.getText(),
                        sName.getText(),sSex.getText(),
                        sBirth.getText(),sMajor.getText());
            }
            return null;
        });

        Optional<StudentResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((StudentResults results) -> {
            Student student = new StudentDaoImpl().get(Integer.parseInt(results.sID));
            if(student != null){
                alert("失败提示","学号为【" + results.sID + "】的学生数据已经存在，无法添加！",null, Alert.AlertType.ERROR);
            }else{
                // 保存信息到数据库
                new StudentDaoImpl().save(new Student(results.sID, results.sClass, results.sName,
                        results.sSex, results.sBirth, results.sMajor));

                alert("成功提示","成功保存学号为【" + results.sID + "】的学生数据！",null, Alert.AlertType.INFORMATION);
                refreshStuTable(); // 刷新界面
            }
        });
    }
    /**
     * 修改学生
     */
    public void changeStu(){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("修改学生信息");
        d.setHeaderText("请输入要修改信息的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }

            Student student = new StudentDaoImpl().get(Integer.parseInt(result.get()));
            if(null != student){
                Dialog<StudentResults> dialog = new Dialog<>();
                dialog.setTitle("学生数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField sID = new TextField(student.getStuID());
                TextField sClass = new TextField(student.getStuClass());
                TextField sName = new TextField(student.getStuName());
                TextField sSex = new TextField(student.getStuSex());
                TextField sBirth = new TextField(student.getStuBirth());
                TextField sMajor = new TextField(student.getStuMajor());

                grid.add(new Label("学号:"), 0, 0);
                grid.add(sID, 1, 0);
                grid.add(new Label("班级:"), 0, 1);
                grid.add(sClass, 1, 1);
                grid.add(new Label("姓名:"), 0, 2);
                grid.add(sName, 1, 2);
                grid.add(new Label("性别:"), 0, 3);
                grid.add(sSex, 1, 3);
                grid.add(new Label("出生日期:"), 0, 4);
                grid.add(sBirth, 1, 4);
                grid.add(new Label("所在专业:"), 0, 5);
                grid.add(sMajor, 1, 5);

                dialog.getDialogPane().setContent(grid);
                Optional<StudentResults> results = dialog.showAndWait();

                if(results.isPresent()){
                    Student stu = new Student(sID.getText(),sClass.getText(),
                            sName.getText(),sSex.getText(),
                            sBirth.getText(),sMajor.getText());
                    new StudentDaoImpl().update(Integer.parseInt(result.get()), stu);
                    alert("成功提示","成功修改学号为【" + student.getStuID() + "】的学生数据！",null, Alert.AlertType.INFORMATION);
                    refreshStuTable(); // 刷新界面
                }
            }else{
                alert("错误提示","没有该学生的记录，无法修改！",null, Alert.AlertType.ERROR);
            }
        }}

    /**
     * 删除学生
     */
    public void deleteStu(){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("删除学生");
        d.setHeaderText("请输入要删除的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Student student = new StudentDaoImpl().get(Integer.parseInt(result.get()));
            if(null != student){
                System.out.println("删除学号为" + student.getStuID() +"的数据");
                new StudentDaoImpl().delete(Integer.parseInt(student.getStuID()));
                alert("成功提示","成功删除学号为【" + student.getStuID() + "】的学生数据！",null, Alert.AlertType.INFORMATION);
                refreshStuTable();//刷新界面
            }else {
                alert("错误提示","没有该学生的记录，无法删除！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * 查询学生
     */
    public void queryStu(){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("查询学生");
        d.setHeaderText("请输入要查询的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Student student = new StudentDaoImpl().get(Integer.parseInt(result.get()));
            if(null != student){
                Dialog<StudentResults> dialog = new Dialog<>();
                dialog.setTitle("学生数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);


                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField sID = new TextField(student.getStuID());
                sID.setEditable(false);
                TextField sClass = new TextField(student.getStuClass());
                sClass.setEditable(false);
                TextField sName = new TextField(student.getStuName());
                sName.setEditable(false);
                TextField sSex = new TextField(student.getStuSex());
                sSex.setEditable(false);
                TextField sBirth = new TextField(student.getStuBirth());
                sBirth.setEditable(false);
                TextField sMajor = new TextField(student.getStuMajor());
                sMajor.setEditable(false);

                grid.add(new Label("学号:"), 0, 0);
                grid.add(sID, 1, 0);
                grid.add(new Label("班级:"), 0, 1);
                grid.add(sClass, 1, 1);
                grid.add(new Label("姓名:"), 0, 2);
                grid.add(sName, 1, 2);
                grid.add(new Label("性别:"), 0, 3);
                grid.add(sSex, 1, 3);
                grid.add(new Label("出生日期:"), 0, 4);
                grid.add(sBirth, 1, 4);
                grid.add(new Label("所在专业:"), 0, 5);
                grid.add(sMajor, 1, 5);

                dialog.getDialogPane().setContent(grid);
                dialog.showAndWait();
            }else {
                alert("错误提示","没有该学生的记录，无法查询！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * 添加课程
     */
    public void addCourse(){
        Dialog<CourseResults> dialog = new Dialog<>();
        dialog.setTitle("添加课程");
        dialog.setHeaderText("请在下面输入要添加的课程信息：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField cID = new TextField();
        TextField cMajor = new TextField();
        TextField cName = new TextField();
        TextField cType = new TextField();
        TextField cStartTerm = new TextField();
        TextField cPeriod = new TextField();
        TextField cCredit = new TextField();

        grid.add(new Label("课程号:"), 0, 0);
        grid.add(cID, 1, 0);
        grid.add(new Label("所属专业:"), 0, 1);
        grid.add(cMajor, 1, 1);
        grid.add(new Label("课程名称:"), 0, 2);
        grid.add(cName, 1, 2);
        grid.add(new Label("课程类型:"), 0, 3);
        grid.add(cType, 1, 3);
        grid.add(new Label("开课学期:"), 0, 4);
        grid.add(cStartTerm, 1, 4);
        grid.add(new Label("学时数:"), 0, 5);
        grid.add(cPeriod, 1, 5);
        grid.add(new Label("学分:"), 0, 6);
        grid.add(cCredit, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new CourseResults(cID.getText(),cMajor.getText(),
                        cName.getText(),cType.getText(),
                        cStartTerm.getText(),cPeriod.getText(), cCredit.getText());
            }
            return null;
        });
        Optional<CourseResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((CourseResults results) -> {
            Course course = new CourseDaoImpl().get(Integer.parseInt(results.cID));
            if(course != null){
                alert("错误提示","课程号为【" + results.cID + "】的数据已经存在，无法添加！",null, Alert.AlertType.INFORMATION);
            }else{
                // 保存信息到数据库
                new CourseDaoImpl().save(new Course(results.cID, results.cMajor, results.cName,
                        results.cType, results.cStartTerm, results.cPeriod, results.cCredit));

                alert("成功提示","成功保存课程号为【" + results.cID + "】的课程数据！",null, Alert.AlertType.ERROR);
                refreshCourseTable(); // 刷新界面
            }
        });
    }
    /**
     * 修改课程
     */
    public void changeCourse(){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("修改课程信息");
        d.setHeaderText("请输入要修改信息的课程号：");
        d.setContentText("课程号:");
        Optional<String> result = d.showAndWait();

        if(result.isPresent()){
            if(checkIdIllegal(result.get())){
                return;
            }
            Course course = new CourseDaoImpl().get(Integer.parseInt(result.get()));
            if(null != course){
                Dialog<CourseResults> dialog = new Dialog<>();
                dialog.setTitle("课程数据");
                dialog.setHeaderText(null);

                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getButtonTypes().addAll(ButtonType.OK);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20,60,10,10));

                TextField cID = new TextField(course.getcID());
                TextField cMajor = new TextField(course.getcMajor());
                TextField cName = new TextField(course.getcName());
                TextField cType = new TextField(course.getcType());
                TextField cStartTerm = new TextField(course.getcStartTerm());
                TextField cPeriod = new TextField(course.getcPeriod());
                TextField cCredit = new TextField(course.getcCredit());

                grid.add(new Label("课程号:"), 0, 0);
                grid.add(cID, 1, 0);
                grid.add(new Label("所属专业:"), 0, 1);
                grid.add(cMajor, 1, 1);
                grid.add(new Label("课程名称:"), 0, 2);
                grid.add(cName, 1, 2);
                grid.add(new Label("课程类型:"), 0, 3);
                grid.add(cType, 1, 3);
                grid.add(new Label("开课学期:"), 0, 4);
                grid.add(cStartTerm, 1, 4);
                grid.add(new Label("学时数:"), 0, 5);
                grid.add(cPeriod, 1, 5);
                grid.add(new Label("学分:"), 0, 6);
                grid.add(cCredit, 1, 6);

                dialog.getDialogPane().setContent(grid);
                Optional<CourseResults> results = dialog.showAndWait();

                if(results.isPresent()){
                            course = new Course(cID.getText(),cMajor.getText(), cName.getText(),cType.getText(),
                            cStartTerm.getText(),cPeriod.getText(), cCredit.getText());

                    new CourseDaoImpl().update(Integer.parseInt(result.get()), course);
                    alert("成功提示","成功修改课程号为【" + course.getcID() + "】的课程数据！",null, Alert.AlertType.INFORMATION);
                    refreshCourseTable(); // 刷新界面
                }
            }else{
                alert("错误提示","没有该课程的记录，无法修改！",null, Alert.AlertType.ERROR);
            }
        }
    }
    /**
     * 删除课程
     */
    public void deleteCourse(){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("删除课程");
        d.setHeaderText("请输入要删除的课程号：");
        d.setContentText("课程号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){

            if(checkIdIllegal(result.get())){
                return;
            }
            Course course = new CourseDaoImpl().get(Integer.parseInt(result.get()));
            if(null != course){
                System.out.println("删除课程号为" + course.getcID() +"的数据");
                new CourseDaoImpl().delete(Integer.parseInt(course.getcID()));
                alert("成功提示","成功删除课程号为【" + course.getcID() + "】的课程数据！",null, Alert.AlertType.INFORMATION);
                refreshCourseTable();//刷新界面
            }else {
                alert("错误提示","没有该课程的记录，无法删除！",null, Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * 添加分数
     */
    public void addScore(){
        Dialog<ScoreResults> dialog = new Dialog<>();
        dialog.setTitle("添加成绩");
        dialog.setHeaderText("请输入对应学号、课程号和成绩：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField cID = new TextField();
        TextField _score = new TextField();
        // TextField credit = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("课程号:"), 0, 1);
        grid.add(cID, 1, 1);
        grid.add(new Label("成绩:"), 0, 2);
        grid.add(_score, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new ScoreResults(sID.getText(),cID.getText(), _score.getText());
            }
            return null;
        });

        Optional<ScoreResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ScoreResults results) -> {
            Score score = new ScoreDaoImpl().get(Integer.parseInt(results.stuID), Integer.parseInt(results.cID));
            Course course = new CourseDaoImpl().get(Integer.parseInt(results.cID));
            if(course == null){ // 课程不存在
                alert("失败提示", "该门课不存在，无法添加！", null, Alert.AlertType.ERROR);
                return;
            }
            if(score != null){ // 分数已经存在
                alert("失败提示","该学生的这门课已有成绩，无法添加！",null, Alert.AlertType.ERROR);
            }else{
                // 保存信息到数据库
                new ScoreDaoImpl().save(Integer.parseInt(results.stuID), Integer.parseInt(results.cID),
                        new Score(results.stuID, results.cID, results.score,
                                String.format("%.3s",Double.parseDouble(results.score) / 100 * Integer.parseInt(course.getcCredit()) + "")));
                                // 50 / 100 *

                alert("成功提示","成功保存此门成绩！",null, Alert.AlertType.INFORMATION);
                refreshScoreTable(); // 刷新分数页面
            }
        });
    }
    /**
     * 删除分数
     */
    public void deleteScore(){
        Dialog<ScoreResults> dialog = new Dialog<>();
        dialog.setTitle("删除成绩");
        dialog.setHeaderText("请输入要对应的学号，课程号：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField cID = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("课程号:"), 0, 1);
        grid.add(cID, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new ScoreResults(sID.getText(),cID.getText());
            }
            return null;
        });

        Optional<ScoreResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ScoreResults results) -> {
            Score score = new ScoreDaoImpl().get(Integer.parseInt(results.stuID), Integer.parseInt(results.cID));
            if(score == null){ // 分数已经存在
                alert("失败提示","没有这门课，无法删除！",null, Alert.AlertType.ERROR);
            }else{
                // 数据库删除信息
                new ScoreDaoImpl().delete(Integer.parseInt(results.stuID), Integer.parseInt(results.cID));
                alert("成功提示","成功删除这门成绩！",null, Alert.AlertType.INFORMATION);
                refreshScoreTable(); // 刷新分数页面
            }
        });
    }
    /**
     * 修改分数
     */
    public void changeScore(){
        Dialog<ScoreResults> dialog = new Dialog<>();
        dialog.setTitle("修改成绩");
        dialog.setHeaderText("请输入对应学号、课程号和成绩：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sID = new TextField();
        TextField cID = new TextField();
        TextField _score = new TextField();

        grid.add(new Label("学号:"), 0, 0);
        grid.add(sID, 1, 0);
        grid.add(new Label("课程号:"), 0, 1);
        grid.add(cID, 1, 1);
        grid.add(new Label("成绩:"), 0, 2);
        grid.add(_score, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new ScoreResults(sID.getText(),cID.getText(), _score.getText());
            }
            return null;
        });

        Optional<ScoreResults> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ScoreResults results) -> {
            Score score = new ScoreDaoImpl().get(Integer.parseInt(results.stuID), Integer.parseInt(results.cID));
            Course course = new CourseDaoImpl().get(Integer.parseInt(results.cID));
            if(score == null){ // 分数不存在
                alert("失败提示","成绩不存在，无法修改！",null, Alert.AlertType.ERROR);
            }else{
                // score != null;
                score.setCredit(String.format("%.3s",Double.parseDouble(results.score) / 100 * Integer.parseInt(course.getcCredit()) + ""));
                // 修改成绩
                new ScoreDaoImpl().update(Integer.parseInt(results.stuID), score);

                alert("成功提示","成功修改成绩！",null, Alert.AlertType.INFORMATION);
                refreshScoreTable(); // 刷新分数页面
            }
        });
    }

    /**
     * 统计功能
     */
    public void statistic(){
        TextInputDialog d = new TextInputDialog();
        d.setTitle("统计学分");
        d.setHeaderText("请输入要统计学分的学生学号：");
        d.setContentText("学号:");
        Optional<String> result = d.showAndWait();

        if (result.isPresent()){
            if(result.get().trim().equals("")) return;
            if(checkIdIllegal(result.get())){
                return;
            }
            int sId = Integer.parseInt(result.get());
            Student student = new StudentDaoImpl().get(sId);
            if(student != null){
                alert("学分统计",result.get() + student.getStuName() + "的总学分为:" + String.format("%.4s",new OtherDaoImpl().statistic(sId)),
                        null, Alert.AlertType.INFORMATION);
            }else{
                alert("错误提示","没有该学生的记录，无法统计！",null, Alert.AlertType.ERROR);
            }
        }
    }
    /**
     * 分析功能
     */
    public void analysis(){


        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("成绩分析");
        dialog.setHeaderText("请输入要分析的班级、课程：");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,60,10,10));

        TextField sClass = new TextField();
        TextField cID = new TextField();

        grid.add(new Label("班级:"), 0, 0);
        grid.add(sClass, 1, 0);
        grid.add(new Label("课程号:"), 0, 1);
        grid.add(cID, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Pair<>(sClass.getText(), cID.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Pair<String, String> results) -> {
            int _class = Integer.parseInt(results.getKey());
            int _cId = Integer.parseInt(results.getValue());
            Course course = new CourseDaoImpl().get(_cId);
            if(course == null){
                alert("失败提示","没有这门课，无法分析！",null, Alert.AlertType.ERROR);
            }else{
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                int score_0_60 = new OtherDaoImpl().analysis(_class, _cId, 0, 60);
                int score_60_75 = new OtherDaoImpl().analysis(_class, _cId, 60, 75);
                int score_75_90 = new OtherDaoImpl().analysis(_class, _cId, 75, 90);
                int score_90_100 = new OtherDaoImpl().analysis(_class, _cId, 90, 100);
                int score_100 = new OtherDaoImpl().analysis(_class, _cId, 100, 101);
                String cName = course.getcName();
                dataset.addValue(score_0_60, cName,"0~60");
                dataset.addValue(score_60_75, cName,"60~75");
                dataset.addValue(score_75_90, cName,"75~90");
                dataset.addValue(score_90_100, cName,"90~100");
                dataset.addValue(score_100, cName,"100");

                setChart("成绩直方图", "成绩分布", "人数", dataset);

            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refreshStuTable(); // 默认显示学生表
        refreshCourseTable(); // 显示课程表
        refreshScoreTable();  // 显示学分表

        // 设置标签与表对应
        setTabVisible(tab_stu, studentTableView);
        setTabVisible(tab_course, courseTableView);
        setTabVisible(tab_score, scoreTableView);

    }

    /**
     * 分析功能显示图表
     */
    private void setChart(String title, String xName, String yName, CategoryDataset dataset){
        JFreeChart chart = ChartFactory.createBarChart(title, xName, yName, dataset);
        // 从这里开始
        CategoryPlot plot = chart.getCategoryPlot();// 获取图表区域对象
        CategoryAxis domainAxis = plot.getDomainAxis(); // 水平底部列表
        domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14)); // 水平底部标题
        domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); // 垂直标题
        ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));// 设置标题字体

        ChartPanel chartPanel = new ChartPanel(chart, true); // 这里也可以用chartFrame,可以直接生成一个独立的Frame
        JFrame frame=new JFrame("成绩分布图");
        frame.add(chartPanel);           //添加柱形图
        frame.setBounds(50, 50, 900, 600);
        frame.setVisible(true);
    }
    private CategoryDataset getChartDataSet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int score_0_60 = new OtherDaoImpl().analysis(172102, 00003, 0, 60);
        int score_60_75 = new OtherDaoImpl().analysis(172102, 00003, 60, 75);
        int score_75_90 = new OtherDaoImpl().analysis(172102, 00003, 75, 90);
        int score_90_100 = new OtherDaoImpl().analysis(172102, 00003, 90, 100);
        int score_100 = new OtherDaoImpl().analysis(172102, 00003, 100, 101);

        dataset.addValue(score_75_90, "数据结构","数据结构");

        /*dataset.addValue(10, "北京", "苹果");*/
        return dataset;
    }

    /**
     * 刷新学生表
     */
    private void refreshStuTable(){ // 刷新显示学生表
        stuIDCol.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        stuClassCol.setCellValueFactory(new PropertyValueFactory<>("stuClass"));
        stuNameCol.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        stuSexCol.setCellValueFactory(new PropertyValueFactory<>("stuSex"));
        stuBirCol.setCellValueFactory(new PropertyValueFactory<>("stuBirth"));
        stuMajorCol.setCellValueFactory(new PropertyValueFactory<>("stuMajor"));

        List<Student> students = new StudentDaoImpl().getAll();
        ObservableList<Student> data = FXCollections.observableArrayList();
        for (Student student : students) {
            data.add(student);
            // System.out.println(students);
        }
        studentTableView.setItems(data);
    }
    /**
     * 刷新课程表
     */
    private void refreshCourseTable(){ // 刷新显示课程表
        cIdCol.setCellValueFactory(new PropertyValueFactory<>("cID"));
        cMajorCol.setCellValueFactory(new PropertyValueFactory<>("cMajor"));
        cNameCol.setCellValueFactory(new PropertyValueFactory<>("cName"));
        cTypeCol.setCellValueFactory(new PropertyValueFactory<>("cType"));
        cStartTremCol.setCellValueFactory(new PropertyValueFactory<>("cStartTerm"));
        cPeriodCol.setCellValueFactory(new PropertyValueFactory<>("cPeriod"));
        cCreditCol.setCellValueFactory(new PropertyValueFactory<>("cCredit"));

        List<Course> courses = new CourseDaoImpl().getAll();
        ObservableList<Course> data = FXCollections.observableArrayList();
        for (Course course : courses) {
            data.add(course);
        }
        courseTableView.setItems(data);
    }
    /**
     * 刷新成绩表
     */
    private void refreshScoreTable(){
        score_sIdCol.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        score_cIdCol.setCellValueFactory(new PropertyValueFactory<>("cID"));
        score_creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        List<Score> scores = new ScoreDaoImpl().getAll();
        ObservableList<Score> data = FXCollections.observableArrayList();
        for (Score score : scores) {
            data.add(score);
            System.out.println(score);
        }
        scoreTableView.setItems(data);
    }

    /**
     * 检测ID合法
     */
    private boolean checkIdIllegal(String sID){
        if(sID.length() >= 10){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("输入的数据不合法！");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * 弹框
     */
    private void alert(String title, String content, String header, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static class StudentResults {
        String sID;
        String sClass;
        String sName;
        String sSex;
        String sBirth;
        String sMajor;
        public StudentResults(String sID, String sClass, String sName, String sSex, String sBirth, String sMajor) {
            this.sID = sID;
            this.sClass = sClass;
            this.sName = sName;
            this.sSex = sSex;
            this.sBirth = sBirth;
            this.sMajor = sMajor;
        }
    }
    private static class CourseResults{
        private String cID;
        private String cMajor;
        private String cName;
        private String cType;
        private String cStartTerm;
        private String cPeriod;
        private String cCredit;

        public CourseResults(String cID, String cMajor, String cName, String cType, String cStartTerm, String cPeriod, String cCredit) {
            this.cID = cID;
            this.cMajor = cMajor;
            this.cName = cName;
            this.cType = cType;
            this.cStartTerm = cStartTerm;
            this.cPeriod = cPeriod;
            this.cCredit = cCredit;
        }
    }
    private static class ScoreResults{
        private String stuID;
        private String cID;
        private String score;

        public ScoreResults(String stuID, String cID) {
            this.stuID = stuID;
            this.cID = cID;
        }

        public ScoreResults(String stuID, String cID, String score) {
            this.stuID = stuID;
            this.cID = cID;
            this.score = score;
        }
    }

    private interface Task {
        void execute();
    }
    private void setTabVisible(Tab tab, TableView tableView){
        setTabAction(tab, new Task() {
            @Override
            public void execute() {
                if(tableView.equals(studentTableView)){
                    courseTableView.setVisible(false);
                    studentTableView.setVisible(true);
                    scoreTableView.setVisible(false);
                }
                else if(tableView.equals(courseTableView)){
                    studentTableView.setVisible(false);
                    courseTableView.setVisible(true);
                    scoreTableView.setVisible(false);
                }
                else if(tableView.equals(scoreTableView)){
                    studentTableView.setVisible(false);
                    courseTableView.setVisible(false);
                    scoreTableView.setVisible(true);
                }
            }
        });
    }
    private void setTabAction(Tab tab, Task task) {
        tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                task.execute();
            }
        });
    }
}
