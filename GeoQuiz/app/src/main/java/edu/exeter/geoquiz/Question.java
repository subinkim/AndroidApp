package edu.exeter.geoquiz;

public class Question {

    private boolean answer;
    private int resId;

    public Question(int rId, boolean ans){
        this.answer = ans;
        this.resId = rId;
    }

    public boolean getAnswer(){
        return answer;
    }

    public int getResId() {
        return resId;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
