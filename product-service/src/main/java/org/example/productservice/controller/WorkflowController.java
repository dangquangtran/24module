package org.example.productservice.controller;

import org.example.productservice.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping("/start-workflow")
    public String startWorkflow(@RequestParam String input) {
        return workflowService.startWorkflow(input);
    }
}
