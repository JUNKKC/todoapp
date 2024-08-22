import "./List.css";
import TodoItem from "./TodoItem";
import { useState } from "react";

const List = ({ todos, onUpdate, onDelete, onAllDelete, searchTerm, setSearchTerm, handleSearch }) => {
    const [filter, setFilter] = useState("ALL");

    const handleFilterChange = (newFilter) => {
        setFilter(newFilter);
    };

    const getFilteredData = () => {
        let filteredTodos = todos;

        if (filter === "COMPLETED") {
            filteredTodos = filteredTodos.filter((todo) => todo.completed);
        } else if (filter === "PENDING") {
            filteredTodos = filteredTodos.filter((todo) => !todo.completed);
        }

        return filteredTodos;
    };
    const onKeydown = (e) => {e.keyCode === 13 && handleSearch()};

    const filteredTodos = getFilteredData();

    return (
        <div className="List">
            <div className="test">
                <h3 className="todoList">Todo List 🌱</h3>
                <button onClick={onAllDelete}>전체 삭제</button>
            </div>
            <div className="status">
                <button onClick={() => handleFilterChange("ALL")}>전체 목록</button>
                <button onClick={() => handleFilterChange("PENDING")}>할 일 목록</button>
                <button onClick={() => handleFilterChange("COMPLETED")}>완료 목록</button>
            </div><div className="search">
            <input
                type="text"
                onKeyDown={onKeydown}
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                placeholder="검색어를 입력하세요"
            />
            <button onClick={handleSearch}>검색</button>
        </div>
            <div className="todos_wrapper">
                {filteredTodos.map((todo) => (
                    <TodoItem
                        key={todo.id}
                        {...todo}
                        onUpdate={onUpdate}
                        onDelete={onDelete}
                    />
                ))}
            </div>
        </div>
    );
};

export default List;