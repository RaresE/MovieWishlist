package com.example.tema2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedbackParser {
    private static final String FEEDBACK = "feedback";

    public static List<Feedback> parsareJson(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            return parsareFeedbacks(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Feedback> parsareFeedbacks(JSONArray jsonArray) throws JSONException {
        List<Feedback> feedbacks = new ArrayList<>();
        for (int i=0; i< jsonArray.length(); i++){
            feedbacks.add(parsareFeedback(jsonArray.getJSONObject(i)));
        }

        return feedbacks;
    }
    private static Feedback parsareFeedback(JSONObject jsonObject) throws JSONException {
        String feedback = jsonObject.getString(FEEDBACK);

        return new Feedback(feedback);
    }
}
