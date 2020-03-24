package domain;

import javafx.beans.property.SimpleStringProperty;

public class Score {

    private final SimpleStringProperty stuID;
    private final SimpleStringProperty cID;
    private final SimpleStringProperty score;
    private final SimpleStringProperty credit;

    public Score(String stuID, String cID, String score, String credit) {
        this.stuID = new SimpleStringProperty(stuID);
        this.cID = new SimpleStringProperty(cID);
        this.score = new SimpleStringProperty(score);
        this.credit = new SimpleStringProperty(credit);
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

    public String getcID() {
        return cID.get();
    }

    public SimpleStringProperty cIDProperty() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID.set(cID);
    }

    public String getScore() {
        return score.get();
    }

    public SimpleStringProperty scoreProperty() {
        return score;
    }

    public void setScore(String score) {
        this.score.set(score);
    }

    public String getCredit() {
        return credit.get();
    }

    public SimpleStringProperty creditProperty() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit.set(credit);
    }

    @Override
    public String toString() {
        return "Score{" +
                "stuID=" + stuID +
                ", cID=" + cID +
                ", score=" + score +
                ", credit=" + credit +
                '}';
    }
}
