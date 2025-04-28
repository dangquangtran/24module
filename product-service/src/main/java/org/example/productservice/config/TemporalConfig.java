package org.example.productservice.config;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

    @Bean
    public WorkflowClient workflowClient() {
        // Cấu hình các tùy chọn cho Temporal service
        WorkflowServiceStubsOptions options = WorkflowServiceStubsOptions.newBuilder()
                .setTarget("localhost:7233") // Địa chỉ Temporal service
                .build();

        // Tạo đối tượng WorkflowServiceStubs với các tùy chọn
        WorkflowServiceStubs serviceStubs = WorkflowServiceStubs.newInstance(options);

        // Tạo và trả về WorkflowClient
        return WorkflowClient.newInstance(serviceStubs);
    }
}
