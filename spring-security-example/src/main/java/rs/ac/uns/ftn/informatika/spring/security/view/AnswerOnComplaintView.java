package rs.ac.uns.ftn.informatika.spring.security.view;

public class AnswerOnComplaintView {
    private  String answer;
    private  String complaintId;
    private String complaintTip;

    public AnswerOnComplaintView(String answer, String complaintId, String complaintTip) {
        this.answer = answer;
        this.complaintId = complaintId;
        this.complaintTip = complaintTip;
    }

    public AnswerOnComplaintView() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplaintTip() {
        return complaintTip;
    }

    public void setComplaintTip(String complaintTip) {
        this.complaintTip = complaintTip;
    }
}
