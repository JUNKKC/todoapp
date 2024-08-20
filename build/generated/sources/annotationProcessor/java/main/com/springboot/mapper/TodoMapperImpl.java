package com.springboot.mapper;

import com.springboot.dto.TodoPatchDto;
import com.springboot.dto.TodoPostDto;
import com.springboot.dto.TodoResponseDto;
import com.springboot.entity.Todos;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-20T09:41:59+0900",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 17.0.12 (Azul Systems, Inc.)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Override
    public Todos todosPostDtoToTodos(TodoPostDto todoPostDto) {
        if ( todoPostDto == null ) {
            return null;
        }

        Todos todos = new Todos();

        todos.setId( todoPostDto.getId() );
        todos.setTitle( todoPostDto.getTitle() );
        todos.setTodoOrder( todoPostDto.getTodoOrder() );
        todos.setCompleted( todoPostDto.isCompleted() );
        todos.setModifiedAt( todoPostDto.getModifiedAt() );

        return todos;
    }

    @Override
    public Todos todosPatchDtoToTodos(TodoPatchDto todoPatchDto) {
        if ( todoPatchDto == null ) {
            return null;
        }

        Todos todos = new Todos();

        todos.setId( todoPatchDto.getId() );
        todos.setTitle( todoPatchDto.getTitle() );
        todos.setTodoOrder( todoPatchDto.getTodoOrder() );
        todos.setCompleted( todoPatchDto.isCompleted() );
        todos.setModifiedAt( todoPatchDto.getModifiedAt() );

        return todos;
    }

    @Override
    public TodoResponseDto todosToTodoResponseDto(Todos todos) {
        if ( todos == null ) {
            return null;
        }

        TodoResponseDto.TodoResponseDtoBuilder todoResponseDto = TodoResponseDto.builder();

        todoResponseDto.id( todos.getId() );
        todoResponseDto.title( todos.getTitle() );
        todoResponseDto.todoOrder( todos.getTodoOrder() );
        todoResponseDto.completed( todos.isCompleted() );
        todoResponseDto.modifiedAt( todos.getModifiedAt() );

        return todoResponseDto.build();
    }

    @Override
    public List<TodoResponseDto> todosToTodoResponseDtos(List<Todos> todos) {
        if ( todos == null ) {
            return null;
        }

        List<TodoResponseDto> list = new ArrayList<TodoResponseDto>( todos.size() );
        for ( Todos todos1 : todos ) {
            list.add( todosToTodoResponseDto( todos1 ) );
        }

        return list;
    }
}
