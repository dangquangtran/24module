package org.example.productservice.service;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.AllArgsConstructor;
import org.example.productservice.temporal.MyWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkflowService {

    private WorkflowClient workflowClient;

    public String startWorkflow(String input) {
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("MyTaskQueue")
                .build();

        MyWorkflow workflow = workflowClient.newWorkflowStub(MyWorkflow.class, options);
        return workflow.executeWorkflow(input);
    }
}
