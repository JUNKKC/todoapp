import "./List.css";
import TodoItem from "./TodoItem";
import { useState } from "react";

const List = ({ todos, onUpdate, onDelete,onAllDelete ,onAllCompletedDelete}) => {
  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState("ALL"); // 필터 상태 추가

  const onChangeSearch = (e) => {
    setSearch(e.target.value);
  };

  const handleFilterChange = (newFilter) => {
    setFilter(newFilter); // 필터 변경
  };
  const getFilteredData = () => {
    let filteredTodos = todos;

    if (search !== "") {
      filteredTodos = filteredTodos.filter((todo) =>
          todo.title.toLowerCase().includes(search.toLowerCase())
      );
    }

    if (filter === "COMPLETED") {
      filteredTodos = filteredTodos.filter((todo) => todo.completed);
    } else if (filter === "PENDING") {
      filteredTodos = filteredTodos.filter((todo) => !todo.completed);
    }

    return filteredTodos;
  };


  const filteredTodos = getFilteredData();

  return (
      <div className="List">

        <div className="test">
            {/*<button onClick={onAllCompletedDelete}>완료된 할 일 삭제</button>*/}
          <h4 className="todoList">Todo List 🌱</h4>
          <button onClick={onAllDelete}>전체 삭제</button>
        </div>
        <div className="status">
          <button onClick={() => handleFilterChange("ALL")}>전체조회</button>
          <button onClick={() => handleFilterChange("PENDING")}>할 일</button>
          <button onClick={() => handleFilterChange("COMPLETED")}>완료</button>
        </div>
        <input
            value={search}
            onChange={onChangeSearch}
            placeholder="검색어를 입력하세요"
        />
        <div className="todos_wrapper">
          {filteredTodos.map((todo) => {
            return (
                <TodoItem
                    key={todo.id}
                    {...todo}
                    onUpdate={onUpdate}
                    onDelete={onDelete}
                />
            );
          })}
        </div>
      </div>
  );
};

export default List;