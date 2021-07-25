package Model;

/** This is the class for the Month ans Type Report model. */
public class ReportByMonth {

    private String month;
    private String type;
    private int count;

    public ReportByMonth(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }



}
