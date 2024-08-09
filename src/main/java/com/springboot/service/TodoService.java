package com.service;

import com.entity.Todos;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
  public Todos createTodo(Todos todos) {
    Todos createdTodo = todos;

    return createdTodo;
  }
}
