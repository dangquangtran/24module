package org.example.productservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.productservice.service.WorkflowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowService workflowService;

    @Operation(summary = "Start a workflow", description = "Triggers a workflow process with the given input.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workflow started successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/start-workflow")
    public String startWorkflow(
            @Parameter(description = "Input data for the workflow")
            @RequestParam String input) {
        return workflowService.startWorkflow(input);
    }
}
