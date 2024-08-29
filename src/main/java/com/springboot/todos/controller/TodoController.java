package com.springboot.todos.controller;

import com.springboot.member.entity.Member;
import com.springboot.todos.dto.TodoPatchDto;
import com.springboot.todos.dto.TodoPostDto;
import com.springboot.todos.dto.TodoResponseDto;
import com.springboot.todos.entity.Todos;
import com.springboot.todos.mapper.TodoMapper;
import com.springboot.todos.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/todos")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

  private final TodoService todoService;
  private final TodoMapper todoMapper;

  public TodoController(TodoService todoService, TodoMapper todoMapper) {
    this.todoService = todoService;
    this.todoMapper = todoMapper;
  }

  @PostMapping("/")
  public ResponseEntity createTodo(@Valid @RequestBody TodoPostDto todoPostDto, Authentication authentication) {
    Todos todos = todoMapper.todosPostDtoToTodos(todoPostDto);
    Todos response = todoService.createTodo(todos, authentication);
    log.info("포스트 완료");
    return new ResponseEntity<>(todoMapper.todosToTodoResponseDto(response), HttpStatus.CREATED);
  }

  @PatchMapping("/{id}")
  public ResponseEntity updateTodo(@PathVariable("id") @Positive long id, @Valid @RequestBody TodoPatchDto todoPatchDto) {
    todoPatchDto.setId(id);
    log.info("수정 완료");
    Todos todos = todoService.updateTodo(todoMapper.todosPatchDtoToTodos(todoPatchDto));
    return new ResponseEntity<>(todoMapper.todosToTodoResponseDto(todos), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity findById(@PathVariable("id") @Positive long id) {
    Todos todos = todoService.findTodo(id);
    log.info("조회 완료");
    return new ResponseEntity<>(todoMapper.todosToTodoResponseDto(todos), HttpStatus.OK);
  }

  @GetMapping("/")
  public ResponseEntity findAll(Authentication authentication) {
    List<Todos> todos;
    Member member = todoService.findMemberByEmail(authentication.getName());

//    if (member.getRoles().contains("ADMIN")) {
//      todos = todoService.findAlltodos(); // Admin 계정이면 모든 투두 가져오기
//    } else {
      todos = todoService.findTodosByUser(authentication); // 일반 사용자이면 자신의 투두만 가져오기
//    }

    log.info("전체 조회 완료");
    return new ResponseEntity<>(todoMapper.todosToTodoResponseDtos(todos), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteTodo(@PathVariable("id") @Positive long id) {
    todoService.deleteTodo(id);
    log.info("삭제 완료");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/")
  public ResponseEntity deleteTodos(Authentication authentication) {
    todoService.deleteAllTodos(authentication);
    log.info("사용자 할 일 전체 삭제 완료");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/search")
  public ResponseEntity<List<TodoResponseDto>> searchTodos(Authentication authentication, @RequestParam String title) {
    List<Todos> todos;
    Member member = todoService.findMemberByEmail(authentication.getName());

//    if (member.getRoles().contains("ADMIN")) {
//      todos = todoService.searchTodosByTitle(title); // Admin 계정이면 모든 투두 검색
//    } else {
      todos = todoService.searchTodosByTitleAndUser(authentication, title); // 일반 사용자이면 자신의 투두만 검색
//    }

    log.info("검색 완료");
    return new ResponseEntity<>(todoMapper.todosToTodoResponseDtos(todos), HttpStatus.OK);
  }
}
