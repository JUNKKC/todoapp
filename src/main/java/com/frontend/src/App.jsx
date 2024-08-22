import axios from "axios";
import "./App.css";
import Header from "./components/Header";
import Editor from "./components/Editor";
import List from "./components/List";
import { useState, useRef, useEffect } from "react";

function App() {
  const [todos, setTodos] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredTodos, setFilteredTodos] = useState([]);
  const idRef = useRef(0);

  useEffect(() => {
    // 초기 할 일 목록 가져오기
    axios
        .get("http://localhost:8080/")
        .then((response) => {
          setTodos(response.data);
          setFilteredTodos(response.data);
          idRef.current =
              response.data.length > 0
                  ? Math.max(...response.data.map((todo) => todo.id)) + 1
                  : 1;
        })
        .catch((error) => {
          console.error("할 일 목록을 가져오는 중 오류가 발생했습니다!", error);
        });
  }, []);

  const onCreate = (title, modifiedAt) => {
    const newTodo = {
      title: title,
      todoOrder: todos.length,
      completed: false,
      modifiedAt: modifiedAt,
    };

    axios
        .post("http://localhost:8080/", newTodo)
        .then((response) => {
          setTodos([response.data, ...todos]);
          setFilteredTodos([response.data, ...todos]);
        })
        .catch((error) => {
          console.error("할 일 생성 중 오류가 발생했습니다!", error);
        });
  };

  const onUpdate = (targetId) => {
    const updatedTodo = todos.find((todo) => todo.id === targetId);
    if (updatedTodo) {
      const updatedData = {
        ...updatedTodo,
        completed: !updatedTodo.completed,
      };

      axios
          .patch(`http://localhost:8080/${targetId}`, updatedData)
          .then((response) => {
            const updatedTodos = todos.map((todo) =>
                todo.id === targetId ? response.data : todo
            );
            setTodos(updatedTodos);
            setFilteredTodos(updatedTodos);
          })
          .catch((error) => {
            console.error("할 일 업데이트 중 오류가 발생했습니다!", error);
          });
    }
  };

  const onDelete = (targetId) => {
    axios
        .delete(`http://localhost:8080/${targetId}`)
        .then(() => {
          const remainingTodos = todos.filter((todo) => todo.id !== targetId);
          setTodos(remainingTodos);
          setFilteredTodos(remainingTodos);
        })
        .catch((error) => {
          console.error("할 일 삭제 중 오류가 발생했습니다!", error);
        });
  };

  const onAllDelete = () => {
    if (todos.length !== 0) {
      axios
          .delete(`http://localhost:8080/`)
          .then(() => {
            setTodos([]);
            setFilteredTodos([]);
          })
          .catch((error) => {
            console.error("할 일 전체 삭제 중 오류가 발생했습니다!", error);
          });
    }
  };

  // 검색 기능 추가
  const handleSearch = async () => {
    try {
      const response = await axios.get("http://localhost:8080/search", {
        params: { title: searchTerm }
      });
      setFilteredTodos(response.data);
    } catch (error) {
      console.error("할 일 검색 중 오류가 발생했습니다!", error);
    }
  };

  return (
      <div className="App">
        <Header />
        <Editor onCreate={onCreate} />
        <List
            todos={filteredTodos}
            onUpdate={onUpdate}
            onDelete={onDelete}
            onAllDelete={onAllDelete}
            searchTerm={searchTerm}
            setSearchTerm={setSearchTerm}
            handleSearch={handleSearch}
        />
      </div>
  );
}

export default App;