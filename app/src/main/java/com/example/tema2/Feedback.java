package com.example.tema2;

public class Feedback {
    private String feedback;

    public Feedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedback='" + feedback + '\'' +
                '}';
    }
}
