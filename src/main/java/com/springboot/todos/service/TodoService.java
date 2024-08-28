package com.springboot.todos.service;

import com.springboot.todos.entity.Todos;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.todos.repository.TodoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
  private final TodoRepository todoRepository;
  private final MemberRepository memberRepository;

  public TodoService(TodoRepository todoRepository, MemberRepository memberRepository) {
    this.todoRepository = todoRepository;
    this.memberRepository = memberRepository;
  }
// @Transactional 어노테이션을 사용하여 트랜잭션을 적용합니다.
  @Transactional
  // 할일 저장
  public Todos createTodo(Todos todos, Authentication authentication) {
    String username = authentication.getName();
    Member member = memberRepository.findByEmail(username)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

    todos.setMember(member);

    todos.setModifiedAt(LocalDateTime.now());

    return todoRepository.save(todos);
  }

  @Transactional
  public Todos updateTodo(Todos todos) {
    Todos findTodo = findVerifiedTodo(todos.getId());

    Optional.ofNullable(todos.getTitle()).ifPresent(findTodo::setTitle);
    Optional.ofNullable(todos.getTodoOrder()).ifPresent(findTodo::setTodoOrder);
    findTodo.setCompleted(todos.isCompleted());
    findTodo.setModifiedAt(LocalDateTime.now());

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
  public void deleteAllTodos(Authentication authentication) {
    // 현재 인증된 사용자 정보 가져오기
    String username = authentication.getName();

    // 사용자 정보로 Member 엔티티 조회
    Member member = memberRepository.findByEmail(username)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

    // 해당 사용자의 모든 할 일 삭제
    List<Todos> userTodos = todoRepository.findByMember(member);
    todoRepository.deleteAll(userTodos);
  }

  public Todos findVerifiedTodo(long id) {
    Optional<Todos> optionalTodos = todoRepository.findById(id);
    return optionalTodos.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
  }

  public List<Todos> searchTodosByTitle(String title) {
    return todoRepository.findByTitleContainingIgnoreCase(title);
  }

  public List<Todos> findTodosByUser(Authentication authentication) {
    String username = authentication.getName();
    Member member = memberRepository.findByEmail(username)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    return todoRepository.findByMember(member);
  }

  public List<Todos> searchTodosByTitleAndUser(Authentication authentication, String title) {
    String username = authentication.getName();
    Member member = memberRepository.findByEmail(username)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    return todoRepository.findByMemberAndTitleContainingIgnoreCase(member, title);
  }

  public Member findMemberByEmail(String email) {
    return memberRepository.findByEmail(email)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }
}
