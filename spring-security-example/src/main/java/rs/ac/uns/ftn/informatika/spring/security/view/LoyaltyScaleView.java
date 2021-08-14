package rs.ac.uns.ftn.informatika.spring.security.view;

public class LoyaltyScaleView {
    private String regularNeededPoints;
    private String regularDiscount;
    private String silverNeededPoints;
    private String silverDiscount;
    private String goldNeededPoints;
    private String goldDiscount;

    public LoyaltyScaleView(){

    }
    public LoyaltyScaleView(String regularNeededPoints, String regularDiscount, String silverNeededPoints, String silverDiscount, String goldNeededPoints, String goldDiscount) {
        this.regularNeededPoints = regularNeededPoints;
        this.regularDiscount = regularDiscount;
        this.silverNeededPoints = silverNeededPoints;
        this.silverDiscount = silverDiscount;
        this.goldNeededPoints = goldNeededPoints;
        this.goldDiscount = goldDiscount;
    }

    public String getRegularNeededPoints() {
        return regularNeededPoints;
    }

    public void setRegularNeededPoints(String regularNeededPoints) {
        this.regularNeededPoints = regularNeededPoints;
    }

    public String getRegularDiscount() {
        return regularDiscount;
    }

    public void setRegularDiscount(String regularDiscount) {
        this.regularDiscount = regularDiscount;
    }

    public String getSilverNeededPoints() {
        return silverNeededPoints;
    }

    public void setSilverNeededPoints(String silverNeededPoints) {
        this.silverNeededPoints = silverNeededPoints;
    }

    public String getSilverDiscount() {
        return silverDiscount;
    }

    public void setSilverDiscount(String silverDiscount) {
        this.silverDiscount = silverDiscount;
    }

    public String getGoldNeededPoints() {
        return goldNeededPoints;
    }

    public void setGoldNeededPoints(String goldNeededPoints) {
        this.goldNeededPoints = goldNeededPoints;
    }

    public String getGoldDiscount() {
        return goldDiscount;
    }

    public void setGoldDiscount(String goldDiscount) {
        this.goldDiscount = goldDiscount;
    }
}
