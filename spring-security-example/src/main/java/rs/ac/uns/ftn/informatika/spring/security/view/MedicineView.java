package rs.ac.uns.ftn.informatika.spring.security.view;

import java.util.ArrayList;
import java.util.List;
public class MedicineView {
    private String code;
    private String name;
    private String type;
    private String shape;
    private String content;
    private String producer;
    private boolean withPrescription;
    private ArrayList<String> substituteMedicineCodes= new ArrayList<String>();
    private String notes;
    private int adviseddailydose;

    private int buyingpoints;
    private String contradiction;
    public MedicineView(String code, String name, String type, String shape, String content, String producer, boolean withPrescription, ArrayList<String> substituteMedicineCodes, String notes, int adviseddailydose, int buyingpoints, String contradiction) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.shape = shape;
        this.content = content;
        this.producer = producer;
        this.withPrescription = withPrescription;
        this.substituteMedicineCodes = substituteMedicineCodes;
        this.notes = notes;
        this.adviseddailydose = adviseddailydose;
        this.buyingpoints = buyingpoints;
        this.contradiction = contradiction;
    }


    public MedicineView(){super();}
    public MedicineView(String code, String name, String type, String shape, String content, String producer, boolean withPrescription, ArrayList<String> substituteMedicineCodes, String notes, int adviseddailydose, String contradiction) {
        super();
        this.code = code;
        this.name = name;
        this.type = type;
        this.shape = shape;
        this.content = content;
        this.producer = producer;
        this.withPrescription = withPrescription;
        this.substituteMedicineCodes = substituteMedicineCodes;
        this.notes = notes;
        this.adviseddailydose = adviseddailydose;
        this.contradiction = contradiction;
    }

    public int getBuyingpoints() {
        return buyingpoints;
    }

    public void setBuyingpoints(int buyingpoints) {
        this.buyingpoints = buyingpoints;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public boolean isWithPrescription() {
        return withPrescription;
    }

    public void setWithPrescription(boolean withPrescription) {
        this.withPrescription = withPrescription;
    }

    public ArrayList<String> getSubstituteMedicineCodes() {
        return substituteMedicineCodes;
    }

    public void setSubstituteMedicineCodes(ArrayList<String> substituteMedicineCodes) {
        this.substituteMedicineCodes = substituteMedicineCodes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAdviseddailydose() {
        return adviseddailydose;
    }

    public void setAdviseddailydose(int adviseddailydose) {
        this.adviseddailydose = adviseddailydose;
    }

    public String getContradiction() {
        return contradiction;
    }

    public void setContradiction(String contradiction) {
        this.contradiction = contradiction;
    }

    @Override
    public String toString() {
        return "MedicineView{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", shape='" + shape + '\'' +
                ", content='" + content + '\'' +
                ", producer='" + producer + '\'' +
                ", withPrescription=" + withPrescription +
                ", substituteMedicineCodes=" + substituteMedicineCodes +
                ", notes='" + notes + '\'' +
                ", adviseddailydose=" + adviseddailydose +
                ", contradiction='" + contradiction + '\'' +
                '}';
    }
}
