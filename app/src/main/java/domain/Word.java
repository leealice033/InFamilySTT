package domain;

/**
 * Created by Alice on 2016/10/4.
 */
public class Word {
    private String word;
    private float wsc;
    private String group_id;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public float getWsc() {
        return wsc;
    }

    public void setWsc(float wsc) {
        this.wsc = wsc;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", wsc=" + wsc +
                ", group_id='" + group_id + '\'' +
                '}';
    }
}
