package rs.ac.uns.ftn.informatika.spring.security.view;

public class ComplaintView {
    private long id;
    private String content;
    private String complainedOnName;
    private String userName;
    private boolean isAnswered;


    private String type;
    public ComplaintView() {
    }

    public ComplaintView(long id, String content, String complainedOnName, String userName, boolean isAnswered, String type) {
        this.id = id;
        this.content = content;
        this.complainedOnName = complainedOnName;
        this.userName = userName;
        this.isAnswered = isAnswered;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComplainedOnName() {
        return complainedOnName;
    }

    public void setComplainedOnName(String complainedOnName) {
        this.complainedOnName = complainedOnName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
