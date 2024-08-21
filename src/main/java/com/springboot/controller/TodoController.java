package com.springboot.controller;

import com.springboot.dto.TodoPatchDto;
import com.springboot.dto.TodoPostDto;
import com.springboot.entity.Todos;
import com.springboot.mapper.TodoMapper;
import com.springboot.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping("/")
@Validated
@CrossOrigin(origins = "http://localhost:3000")  // 프론트엔드 URL에 맞게 설정
//  private final static String MEMBER_DEFAULT_URL = "/"; URI 이용
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
//    URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, todos.getId());URI 이용

    Todos response = todoService.createTodo(todos);
    log.info("포스트 완료");
    return new ResponseEntity<>(todoMapper.todosToTodoResponseDto(response), HttpStatus.CREATED);
//    return ResponseEntity.created(location).build(); // URI 이용 응답값 비어서옴 나중에 해결
  }

  @PatchMapping("/{id}")
  public ResponseEntity updateTodo(@PathVariable("id") @Positive long id, @Valid @RequestBody TodoPatchDto todoPatchDto) {
    todoPatchDto.setId(id);
    log.info("수정 완료");
    Todos todos = todoService.updateTodo(todoMapper.todosPatchDtoToTodos(todoPatchDto));
    return new ResponseEntity<>(todoMapper.todosToTodoResponseDto(todos), HttpStatus.OK);

    //아래 코드로 작성할 경우 Date 로 묶여서 반환됨
//    return new ResponseEntity<> (
//        new SingleResponseDto<>(todoMapper.todosToTodoResponseDto(todos)), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity findById(@PathVariable("id") @Positive long id) {
    Todos todos = todoService.findTodo(id);
    log.info("조회 완료");
    return new ResponseEntity<>(todoMapper.todosToTodoResponseDto(todos), HttpStatus.OK);
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
    log.info("전체 조회 완료");
    return new ResponseEntity<>(todoMapper.todosToTodoResponseDtos(todoService.findAlltodos()), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteTodo(@PathVariable("id") @Positive long id) {
    todoService.deleteTodo(id);
    log.info("삭제 완료");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping
  public ResponseEntity deleteTodos() {
    todoService.deleteAllTodos();
    log.info("전체 삭제 완료");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}