package com.springboot.todos.mapper;


import com.springboot.todos.dto.TodoPatchDto;
import com.springboot.todos.dto.TodoPostDto;
import com.springboot.todos.dto.TodoResponseDto;
import com.springboot.todos.entity.Todos;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
  Todos todosPostDtoToTodos(TodoPostDto todoPostDto);
  Todos todosPatchDtoToTodos(TodoPatchDto todoPatchDto);
  TodoResponseDto todosToTodoResponseDto(Todos todos);
  List<TodoResponseDto> todosToTodoResponseDtos(List<Todos> todos);

}
