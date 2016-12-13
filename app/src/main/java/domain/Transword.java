package domain;

import java.util.List;

/**
 * Created by Alice on 2016/10/4.
 */
public class Transword {
//    public List<Word> wordList;
//
//
//    public List<Word> getWordList() {
//        return wordList;
//    }
//
//    public void setWordList(List<Word> wordList) {
//        this.wordList = wordList;
//    }
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

}
