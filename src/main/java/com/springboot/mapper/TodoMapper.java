package com.springboot.mapper;


import com.springboot.dto.TodoPatchDto;
import com.springboot.dto.TodoPostDto;
import com.springboot.dto.TodoResponseDto;
import com.springboot.entity.Todos;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
  Todos todosPostDtoToTodos(TodoPostDto todoPostDto);
  Todos todosPatchDtoToTodos(TodoPatchDto todoPatchDto);
  TodoResponseDto todosToTodoResponseDto(Todos todos);
  List<TodoResponseDto> todosToTodoResponseDtos(List<Todos> todos);

}
