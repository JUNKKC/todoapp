package com.springboot.controller;

import com.springboot.dto.TodoPatchDto;
import com.springboot.dto.TodoPostDto;
import com.springboot.entity.Todos;
import com.springboot.mapper.TodoMapper;
import com.springboot.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping
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
  @PatchMapping("/{id}")
  public ResponseEntity updateTodo(@PathVariable ("id") @Positive long id, @Valid @RequestBody
  TodoPatchDto todoPatchDto) {
    todoPatchDto.setId(id);
    Todos todos = todoService.updateTodo(todoMapper.todosPatchDtoToTodos(todoPatchDto));

    return new ResponseEntity<> (todoMapper.todosToTodoResponseDto(todos), HttpStatus.OK);
  }
  @GetMapping("/{id}")
  public ResponseEntity findById(@PathVariable("id") @Positive long id) {
    Todos todos = todoService.findTodo(id);

    return new ResponseEntity(todoMapper.todosToTodoResponseDto(todos), HttpStatus.OK);
  }
  //페이징 네이션 구현 잘못 구현함
//  @GetMapping
//  public ResponseEntity getTodos(@Positive @RequestParam int page, @Positive @RequestParam int size) {
//    Page<Todos> pageTodos = todoService.findTodos(page-1, size);
//    List<Todos> todos = pageTodos.getContent();
//    return new ResponseEntity(todoMapper.todosToTodoResponseDtos(todos), HttpStatus.OK);
//  }

  @GetMapping
  public ResponseEntity findAll() {


     return new ResponseEntity(todoMapper.todosToTodoResponseDtos(todoService.findAlltodos()), HttpStatus.OK);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity deleteTodo(@PathVariable ("id") @Positive long id) {

    todoService.deleteTodo(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  @DeleteMapping
  public ResponseEntity deleteTodos(){
    todoService.deleteAllTodos();

    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

}
