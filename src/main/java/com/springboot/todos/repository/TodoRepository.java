package com.springboot.todos.repository;

import com.springboot.todos.entity.Todos;
import com.springboot.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todos, Long> {
  Optional<Todos> findByTitle(String title);
  List<Todos> findByTitleContainingIgnoreCase(String title);
  List<Todos> findByMember(Member member);
  List<Todos> findByMemberAndTitleContainingIgnoreCase(Member member, String title);
}
