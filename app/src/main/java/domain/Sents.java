package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alice on 2016/10/4.
 */
public class Sents {
    public String sent;
    public double ssc;
    public int complete;
    public String action;


    public List<Transword> transword;

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public double getSsc() {
        return ssc;
    }

    public void setSsc(double ssc) {
        this.ssc = ssc;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<Transword> getTransword() {
        return transword;
    }

    public void setTransword(List<Transword> transword) {
        this.transword = transword;
    }


    @Override
    public String toString() {
        return "Sents{" +
                "sent='" + sent + '\'' +
                ", ssc=" + ssc +
                ", complete=" + complete +
                ", action='" + action + '\'' +
                ", transword=" + transword +
                '}';
    }
}
