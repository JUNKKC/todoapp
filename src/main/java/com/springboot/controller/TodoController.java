package com.controller;

import com.dto.TodoPostDto;
import com.entity.Todos;
import com.mapper.TodoMapper;
import com.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@Validated
public class TodoController {
  private final TodoService todoService;
  private final TodoMapper todoMapper;

  public TodoController(TodoService todoService, TodoMapper todoMapper) {
    this.todoService = todoService;
    this.todoMapper = todoMapper;
  }

  @PostMapping
  public ResponseEntity createTodo(@Valid @RequestBody TodoPostDto todoPostDto) {

    Todos todos = todoMapper.todosPostDtoToTodos(todoPostDto);

    Todos response = todoService.createTodo(todos);

    return new ResponseEntity<>(todoMapper.todosToTodoResponseDto(response), HttpStatus.CREATED);
  }
}
