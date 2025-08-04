package org.example.productservice.config;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

    @Value("${temporal.server.url:localhost:7233}")
    private String temporalServerUrl;

    @Bean
    public WorkflowClient workflowClient() {
        // Cấu hình các tùy chọn cho Temporal service
        WorkflowServiceStubsOptions options = WorkflowServiceStubsOptions.newBuilder()
                .setTarget(temporalServerUrl) // Đọc từ properties thay vì hardcode
                .build();

        // Tạo đối tượng WorkflowServiceStubs với các tùy chọn
        WorkflowServiceStubs serviceStubs = WorkflowServiceStubs.newInstance(options);

        // Tạo và trả về WorkflowClient
        return WorkflowClient.newInstance(serviceStubs);
    }
}
