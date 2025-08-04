package org.example.productservice.temporal;

import io.temporal.client.WorkflowClient;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemporalWorkerStarter {

    @Autowired
    private WorkflowClient workflowClient; // Inject WorkflowClient đã được cấu hình

    @PostConstruct
    public void startWorker() {
        // 1. Sử dụng WorkflowClient đã được cấu hình thay vì tạo mới
        // WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        // WorkflowClient client = WorkflowClient.newInstance(service);

        // 2. Tạo Worker Factory với WorkflowClient đã được inject
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);

        // 3. Tạo Worker và đăng ký task queue
        Worker worker = factory.newWorker("MyTaskQueue");

        // 4. Đăng ký Workflow Implementation và Activity Implementation
        worker.registerWorkflowImplementationTypes(MyWorkflowImpl.class);
        worker.registerActivitiesImplementations(new MyActivitiesImpl());

        // 5. Start Worker
        factory.start();
    }
}
