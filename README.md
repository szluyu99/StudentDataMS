转载自我的 [CSDN博客](https://blog.csdn.net/weixin_43734095/article/details/105085183)

# 前言
是这样的....那个夜黑风高的晚上，我高中同学给我发了这么一张图....
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200325000800769.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
秒懂，遂着手，腾出 3 天的晚上时间，完成此项目.....

我对学生管理系统没啥兴趣...完全按照他们的需求来做，旨在做出个任务品 让他们交作业.....下面就介绍一下大体框架和数据库表的设计。

# 项目GitHub网址

**完整代码放在 GitHub**，[https://github.com/szluyu99/StudentDataMS](https://github.com/szluyu99/StudentDataMS)

# 需求分析
系统的主要功能包括：信息的操作功能、查询功能、统计功能、分析功能和打印功能。 

信息操作功能 ： 
* 学生基本信息的添加、修改和删除。
 学生基本信息包括：学号（学号的前 6 位为班级号）、姓名、性别、出生日期和所在的专业。 
* 课程信息的添加、修改和删除。
课程信息包括:课程号、所属专业、课程名称、课 程类型（专修、选修、方向、通修、公修）、开课学期、学时数和学分。 
* 学生成绩信息的添加、修改和删除。学生成绩信息包括：学好、课程号、成绩和学分。 

查询功能：可以通过学号查询学生基本信息，通过学号和学期号查询学生的成绩，通过课程号查询该课程的信息。 

统计功能：统计学生某个学期或所有学期课程的总学分。 

分析功能：对某一个班级的某一门课程的成绩分布进行分析，并以直方图的形式显示出来。 

将学生信息存储在数据库中。数据库可选择 MySQL、SQL Server 和 SQLite。 


# 工具准备
* JavaFx 环境 [Eclipse 搭建](https://blog.csdn.net/weixin_43734095/article/details/103432741)，IDEA安装 JavaFx 插件即可
* MySQL5.7(**不要用8.0**) 及 Navicat for MySQL
* Idea( 或 Eclipse)
* JavaFX Scene Builder 2.0


# 项目代码结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200325010819199.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
由于是帮别的专业同学做的作业，不使用框架，使用原生的JDBC操作数据库。

以下包名省略前缀 com.yu：
* controller：存放**控制器类**， 对JavaFx的界面文件添加动作等。
* dao：数据访问对象，存放**数据库接口文件**
	* dao.impl：存放**数据库接口的实现**
* domain：存放数据库中表对应的**数据库对象**
* stage：主要存放**界面文件**(fxml)
* util：存放开发中写的**工具类**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200325010838773.png)
lib 文件夹中存放开发用到的 **jar包**。
resources 资源文件夹中存放数据库的属性文件 db.properties。

# 数据库表设计
根据题目要求，设计三个表，首先在 mysql 中创建一个数据库：stums
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020032500523548.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)

**创建 student 表：**
```sql
/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : stums

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-03-25 00:26:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `stuID` INT NOT NULL,
  `stuClass` varchar(255) DEFAULT NULL,
  `stuName` varchar(255) DEFAULT NULL,
  `stuSex` varchar(255) DEFAULT NULL,
  `stuBirth` varchar(255) DEFAULT NULL,
  `stuMajor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`stuID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('17210201', '172102', '任盈盈', '女', '无', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210202', '172102', '阿青', '女', '1999.8.8', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210203', '172102', '阿朱', '女', '1999.8.15', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210204', '172102', '阿紫', '女', '1998.5.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210205', '172102', '小龙女', '女', '2000.1.1', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210206', '172102', '黄蓉', '女', '2000.8.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210207', '172102', '岳灵珊', '女', '2222.2.2', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210208', '172102', '公孙绿萼', '女', '1998.5.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210209', '172102', '东方不败', '无性', '1998.5.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210210', '172102', '乔峰', '男', '1998.5.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210211', '172102', '段誉', '男', '2000.8.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210212', '172102', '虚竹', '男', '2000.8.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210213', '172102', '田伯光', '男', '1998.5.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210214', '172102', '胡斐', '男', '1997.6.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210215', '172102', '郭靖', '男', '1998.5.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210216', '172102', '张无忌', '男', '1998.5.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210217', '172102', '陈家洛', '男', '1998.8.4', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210218', '172102', '杨过', '男', '2000.8.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210219', '172102', '令狐冲', '男', '2000.8.5', '计算机科学与技术');
INSERT INTO `student` VALUES ('17210220', '172102', '韦小宝', '男', '2000.8.5', '计算机科学与技术');
```

**创建 course 表：**
```sql
/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : stums

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-03-25 00:22:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cID` INT NOT NULL,
  `cMajor` varchar(255) DEFAULT NULL,
  `cName` varchar(255) DEFAULT NULL,
  `cType` varchar(255) DEFAULT NULL,
  `cStartTerm` varchar(255) DEFAULT NULL,
  `cPeriod` varchar(255) DEFAULT NULL,
  `cCredit` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '计算机科学与技术', '数据结构', '必修', '大二（上）', '64', '5');
INSERT INTO `course` VALUES ('2', '计算机科学与技术', '离散数学', '必修', '大二（上）', '48', '4');
INSERT INTO `course` VALUES ('3', '计算机科学与技术', '高等数学', '必修', '大一（下）', '64', '5');
INSERT INTO `course` VALUES ('4', '计算机科学与技术', '网页制作', '必修', '大一（下）', '48', '3');
INSERT INTO `course` VALUES ('5', '计算机科学与技术', '机器学习', '专修', '大三（上）', '48', '3.5');
INSERT INTO `course` VALUES ('6', '计算机科学与技术', '深度学习', '专修', '大三（上）', '48', '3.5');
INSERT INTO `course` VALUES ('7', '计算机科学与技术', '线性代数', '必修', '大一（上）', '64', '4');
INSERT INTO `course` VALUES ('8', '计算机科学与技术', '网络安全', '选修', '大二（下）', '48', '3');
INSERT INTO `course` VALUES ('9', '计算机科学与技术', 'Java', '选修', '大二（下）', '48', '3');
```

**创建 score 表：**
```sql
/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : stums

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-03-25 00:26:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `stuID` INT DEFAULT NULL,
  `cID` INT DEFAULT NULL,
  `score` varchar(255) DEFAULT NULL,
  `credit` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES ('17210201', '1', '60', '3.0');
INSERT INTO `score` VALUES ('17210201', '2', '70 ', '5');
INSERT INTO `score` VALUES ('17210201', '3', '80', '4');
INSERT INTO `score` VALUES ('17210201', '4', '60', '3.0');
INSERT INTO `score` VALUES ('17210202', '1', '94', '4.6');
INSERT INTO `score` VALUES ('17210202', '2', '85', '2.5');
INSERT INTO `score` VALUES ('17210202', '1', '40', '1.2');
INSERT INTO `score` VALUES ('17210202', '1', '50', '1.5');
INSERT INTO `score` VALUES ('17210203', '1', '80', '4.0');
INSERT INTO `score` VALUES ('17210203', '1', '100', '4');
INSERT INTO `score` VALUES ('17210203', '1', '60', '3.0');
INSERT INTO `score` VALUES ('17210203', '1', '80', '2.4');
INSERT INTO `score` VALUES ('17210204', '1', '90', '4.5');
INSERT INTO `score` VALUES ('17210205', '1', '100', '5');
INSERT INTO `score` VALUES ('17210206', '1', '0', '0');
INSERT INTO `score` VALUES ('17210207', '1', '90', '4.5');
INSERT INTO `score` VALUES ('17210208', '1', '84', '4.2');
INSERT INTO `score` VALUES ('17210209', '1', '90', '4.5');
INSERT INTO `score` VALUES ('17210210', '1', '90', '4.5');
INSERT INTO `score` VALUES ('17210211', '1', '100', '5');
INSERT INTO `score` VALUES ('17210212', '1', '96', '4.8');
INSERT INTO `score` VALUES ('17210213', '1', '90', '4.5');
INSERT INTO `score` VALUES ('17210214', '1', '100', '5');
INSERT INTO `score` VALUES ('17210215', '1', '90', '4.5');
INSERT INTO `score` VALUES ('17210216', '1', '80', '4.0');
INSERT INTO `score` VALUES ('17210217', '1', '70', '3.5');
INSERT INTO `score` VALUES ('17210218', '1', '65', '3.25');
INSERT INTO `score` VALUES ('17210219', '1', '50', '2.5');
INSERT INTO `score` VALUES ('17210220', '1', '90', '4.5');
```


# 项目演示
左边的学生、课程、成绩标签，分别控制三张表的显示。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200325010936454.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200325010944769.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200325010952146.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,sie_16,color_FFFFFF,t_70)
所有增删改查均可操作，这里就展示一下查询学生信息
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200325011042114.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
统计学分
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020032501115663.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
呃，简陋的统计成绩直方图。(他们老师要求用 **jfreechart**)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200325011231115.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
