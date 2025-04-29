package org.example.productservice.temporal;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TemporalWorkerStarter {

    @PostConstruct
    public void startWorker() {
        // 1. Kết nối tới Temporal Server (thường localhost:7233)
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClient client = WorkflowClient.newInstance(service);

        // 2. Tạo Worker Factory
        WorkerFactory factory = WorkerFactory.newInstance(client);

        // 3. Tạo Worker và đăng ký task queue
        Worker worker = factory.newWorker("MyTaskQueue");

        // 4. Đăng ký Workflow Implementation và Activity Implementation
        worker.registerWorkflowImplementationTypes(MyWorkflowImpl.class);
        worker.registerActivitiesImplementations(new MyActivitiesImpl());

        // 5. Start Worker
        factory.start();
    }
}
