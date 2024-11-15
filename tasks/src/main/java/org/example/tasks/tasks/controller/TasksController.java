package org.example.tasks.tasks.controller;

import lombok.AllArgsConstructor;
import org.example.tasks.tasks.request.CancelRequest;
import org.example.tasks.tasks.data.Task;
import org.example.tasks.tasks.request.TaskRequest;
import org.example.tasks.tasks.request.AssignRequest;
import org.example.tasks.tasks.service.TasksService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@EnableRabbit
@RestController
@RequestMapping("/employee")
public class TasksController {

    private final TasksService tasksService;

    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasks(
        @RequestParam("limit") final int limit,
        @RequestParam("offset") final int offset,
        @RequestParam(value = "status", required = false) final String status
    ) throws Exception {
        return tasksService.getTasks(offset, limit);
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void postTask(@RequestBody TaskRequest taskRequest) {
        createTask(taskRequest);
    }

    @RabbitListener(queues = "myQueue")
    public void createTask(TaskRequest taskRequest) {
        tasksService.createTask(taskRequest);
    }

    @PatchMapping("/tasks/{taskId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(
        @PathVariable("taskId") final int taskId,
        @RequestBody final CancelRequest reasonRequest
    ) throws Exception {
        tasksService.cancelTask(taskId);
    }

    @PatchMapping("/tasks/{taskId}/assign")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void prepareTask(
        @PathVariable("taskId") final int taskId,
        @RequestBody final AssignRequest assignRequest
    ) throws Exception {
        tasksService.assignTask(taskId, assignRequest.executorId());
    }

    @PatchMapping("/tasks/{taskId}/complete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void completeTask(@PathVariable("taskId") final int taskId) throws Exception {
        tasksService.completeTask(taskId);
    }
}
