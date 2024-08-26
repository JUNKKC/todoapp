package com.springboot.todos.service;

import com.springboot.todos.entity.Todos;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.todos.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
  private final TodoRepository todoRepository;

  public TodoService(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Transactional
  public Todos createTodo(Todos todos) {
    // 'todoOrder'를 현재 저장된 총 개수에 +1하여 설정
    int todoOrder = (int) todoRepository.count() + 1;
    todos.setTodoOrder(todoOrder);

    // 'modifiedAt'을 현재 시간으로 설정
    todos.setModifiedAt(LocalDateTime.now());

    // DB에 저장
    return todoRepository.save(todos);
  }

  @Transactional
  public Todos updateTodo(Todos todos) {
    // DB에서 기존 엔티티를 찾음
    Todos findTodo = findVerifiedTodo(todos.getId());

    // 필드 업데이트
    Optional.ofNullable(todos.getTitle())
        .ifPresent(findTodo::setTitle);
    Optional.ofNullable(todos.getTodoOrder())
        .ifPresent(findTodo::setTodoOrder);

    findTodo.setCompleted(todos.isCompleted());

    // 수정 시간 갱신
    findTodo.setModifiedAt(LocalDateTime.now());

    // 업데이트된 엔티티를 저장
    return todoRepository.save(findTodo);
  }

  public Todos findTodo(long id) {
    return findVerifiedTodo(id);
  }

  public List<Todos> findAlltodos() {
    return todoRepository.findAll();
  }

  @Transactional
  public void deleteTodo(long id) {
    Todos findTodo = findVerifiedTodo(id);
    todoRepository.delete(findTodo);
  }

  @Transactional
  public void deleteAllTodos() {
    todoRepository.deleteAll();
  }
  // 특정 'Todo'를 검증하고 반환
  public Todos findVerifiedTodo(long id) {
    Optional<Todos> optionalTodos = todoRepository.findById(id);
    return optionalTodos.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
  }
  // 'title'에 해당하는 'Todo' 목록을 반환
  public List<Todos> searchTodosByTitle(String title) {
    return todoRepository.findByTitleContainingIgnoreCase(title);
  }
}