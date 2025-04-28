package org.example.productservice.temporal;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class MyWorkflowImpl implements MyWorkflow {

    private final MyActivities activities = Workflow.newActivityStub(MyActivities.class,
            ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(2)).build());

    @Override
    public String executeWorkflow(String input) {
        // Thực hiện logic của workflow và gọi activity
        return activities.performActivity(input);
    }
}
