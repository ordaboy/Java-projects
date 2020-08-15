package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact implements Serializable {
    private String number;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLastEdited;

    public Contact() {
        this.number = "";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = toValidNumber(number);
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeLastEdited() {
        return timeLastEdited;
    }

    public void setTimeLastEdited(LocalDateTime timeLastEdited) {
        this.timeLastEdited = timeLastEdited;
    }

    private String toValidNumber(String number) {
        Pattern pattern1 = Pattern.compile("\\+?\\(\\w+\\)([\\s-]\\w{2,})*");
        Pattern pattern2 = Pattern.compile("\\+?\\w+[-\\s]\\(\\w{2,}\\)([-\\s]\\w{2,})*");
        Pattern pattern3 = Pattern.compile("\\+?\\w+([-\\s]\\w{2,})*");
        Matcher matcher1 = pattern1.matcher(number);
        Matcher matcher2 = pattern2.matcher(number);
        Matcher matcher3 = pattern3.matcher(number);
        if (matcher1.matches() || matcher2.matches() || matcher3.matches()) {
            return number;
        } else {
            System.out.println("Wrong number format!");
            return "";
        }
    }

    protected void setTimeCreatedAndEdited() {
        setTimeCreated(LocalDateTime.now());
        setTimeLastEdited(LocalDateTime.now());
    }

    public String getFullName() {
        return "";
    }

}
