package com.mapper;


import com.dto.TodoPatchDto;
import com.dto.TodoPostDto;
import com.dto.TodoResponseDto;
import com.entity.Todos;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
  Todos todosPostDtoToTodos(TodoPostDto todoPostDto);
  Todos todosPatchDtoToTodos(TodoPatchDto todoPatchDto);
  TodoResponseDto todosToTodoResponseDto(Todos todos);

}
