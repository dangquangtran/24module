package org.example.productservice.temporal;

public class MyActivitiesImpl implements MyActivities {

    @Override
    public String performActivity(String input) {
        // Thực hiện logic của activity
        return "Activity performed with input: " + input;
    }
}