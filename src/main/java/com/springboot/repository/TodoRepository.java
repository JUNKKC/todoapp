package com.springboot.repository;

import com.springboot.entity.Todos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todos, Long> {
  Optional<Todos> findByTitle(String title);

// 제목을 대소문자 구분 없이 검색
List<Todos> findByTitleContainingIgnoreCase(String title);
}
