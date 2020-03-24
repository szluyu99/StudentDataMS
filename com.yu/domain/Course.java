package domain;

import javafx.beans.property.SimpleStringProperty;

public class Course {
    /** ¿Î³ÌºÅ£¨Ö÷¼ü£© **/
    private final SimpleStringProperty cID;
    private final SimpleStringProperty cMajor;
    private final SimpleStringProperty cName;
    private final SimpleStringProperty cType;
    private final SimpleStringProperty cStartTerm;
    private final SimpleStringProperty cPeriod;
    private final SimpleStringProperty cCredit;

    public Course(String cID, String cMajor, String cName, String cType, String cStartTerm, String cPeriod, String cCredit) {
        this.cID = new SimpleStringProperty(cID);
        this.cMajor = new SimpleStringProperty(cMajor);
        this.cName = new SimpleStringProperty(cName);
        this.cType = new SimpleStringProperty(cType);
        this.cStartTerm = new SimpleStringProperty(cStartTerm);
        this.cPeriod = new SimpleStringProperty(cPeriod);
        this.cCredit = new SimpleStringProperty(cCredit);
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

    public String getcMajor() {
        return cMajor.get();
    }

    public SimpleStringProperty cMajorProperty() {
        return cMajor;
    }

    public void setcMajor(String cMajor) {
        this.cMajor.set(cMajor);
    }

    public String getcName() {
        return cName.get();
    }

    public SimpleStringProperty cNameProperty() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName.set(cName);
    }

    public String getcType() {
        return cType.get();
    }

    public SimpleStringProperty cTypeProperty() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType.set(cType);
    }

    public String getcStartTerm() {
        return cStartTerm.get();
    }

    public SimpleStringProperty cStartTermProperty() {
        return cStartTerm;
    }

    public void setcStartTerm(String cStartTerm) {
        this.cStartTerm.set(cStartTerm);
    }

    public String getcPeriod() {
        return cPeriod.get();
    }

    public SimpleStringProperty cPeriodProperty() {
        return cPeriod;
    }

    public void setcPeriod(String cPeriod) {
        this.cPeriod.set(cPeriod);
    }

    public String getcCredit() {
        return cCredit.get();
    }

    public SimpleStringProperty cCreditProperty() {
        return cCredit;
    }

    public void setcCredit(String cCredit) {
        this.cCredit.set(cCredit);
    }
}
