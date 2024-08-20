package com.springboot.service;

import com.springboot.entity.Todos;
import com.springboot.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
  private final TodoRepository todoRepository;

  public TodoService(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  public Todos createTodo(Todos todos) {

    int todoOrder = (int) todoRepository.count();

    todos.setTodoOrder(todoOrder+1);

    return todoRepository.save(todos);
  }


  public Todos updateTodo(Todos todos) {
    Todos findTodo = findVerifiedTodo(todos.getId());

    Optional.ofNullable(todos.getTitle())
        .ifPresent(title -> findTodo.setTitle(title));
    Optional.ofNullable(todos.getTodoOrder())
        .ifPresent(order -> findTodo.setTodoOrder(order));
    boolean completed = todos.isCompleted();
    findTodo.setCompleted(completed);

    return todoRepository.save(findTodo);
  }
  public Todos findTodo(long id) {
    return findVerifiedTodo(id);
  }
  public List<Todos> findAlltodos(){
    return todoRepository.findAll();
  }
  //페이징 네이션 서비스
//  public Page<Todos> findTodos(int page, int size) {
//    return todoRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
//  }


  public void deleteTodo(long id) {
    Todos findTodo = findVerifiedTodo(id);

    todoRepository.delete(findTodo);

  }
  public void deleteAllTodos() {
    todoRepository.deleteAll();
  }

  public Todos findVerifiedTodo(long id) {
    Optional<Todos> optionalTodos = todoRepository.findById(id);
    Todos findTodos =
        optionalTodos.orElseThrow(() -> new RuntimeException("Todo not found"));
    return findTodos;
  }

}
