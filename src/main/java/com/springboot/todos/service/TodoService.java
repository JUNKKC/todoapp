package com.springboot.todos.service;

import com.springboot.todos.entity.Todos;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.todos.repository.TodoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
  private final TodoRepository todoRepository;
  private final MemberRepository memberRepository;  // MemberRepository를 추가하여 사용자를 조회

  public TodoService(TodoRepository todoRepository, MemberRepository memberRepository) {
    this.todoRepository = todoRepository;
    this.memberRepository = memberRepository;
  }

  @Transactional
  public Todos createTodo(Todos todos) {
    // 현재 인증된 사용자 정보 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String username = authentication.getName();

    // 사용자 정보로 Member 엔티티 조회
    Member member = memberRepository.findByEmail(username)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

    // 'Todos' 엔티티에 사용자 정보 설정
    todos.setMember(member);

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