package domain;

import java.util.List;

/**
 * Created by Alice on 2016/10/4.
 */
public class SttResult {
    public int status;
    public int sn;
    public String end;
    public String taskName;



    public long feedbackID;
    public float execTime;
    public float initTime;
    public List<Sents> sents;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public float getExecTime() {
        return execTime;
    }
    public long getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(long feedbackID) {
        this.feedbackID = feedbackID;
    }

    public void setExecTime(float execTime) {
        this.execTime = execTime;
    }

    public float getInitTime() {
        return initTime;
    }

    public void setInitTime(float initTime) {
        this.initTime = initTime;
    }

    public List<Sents> getSents() {
        return sents;
    }

    public void setSents(List<Sents> sents) {
        this.sents = sents;
    }

    @Override
    public String toString() {
        return "SttResult{" +
                "status=" + status +
                ", sn=" + sn +
                ", end='" + end + '\'' +
                ", taskName='" + taskName + '\'' +
                ", execTime=" + execTime +
                ", initTime=" + initTime +
                ", sents=" + sents +
                '}';
    }
}