import axios from "axios";
import "./App.css";
import Header from "./components/Header";
import Editor from "./components/Editor";
import List from "./components/List";
import { useState, useRef, useEffect } from "react";

function App() {
  const [todos, setTodos] = useState([]);
  const idRef = useRef(0);

  // 컴포넌트가 처음 렌더링될 때 백엔드에서 할 일 목록을 가져옴
  useEffect(() => {
    axios
      .get("http://localhost:8080/") // Spring Boot 서버의 URL에 맞게 조정하세요
      .then((response) => {
        setTodos(response.data);
        idRef.current =
          response.data.length > 0
            ? Math.max(...response.data.map((todo) => todo.id)) + 1
            : 1;
      })
      .catch((error) => {
        console.error("할 일 목록을 가져오는 중 오류가 발생했습니다!", error);
      });
  }, []);

  // 새 할 일 생성 함수
  const onCreate = (title ,modifiedAt) => {
    const newTodo = {
      title: title,
      todoOrder: todos.length, // 순서를 할 일 목록의 길이로 설정
      completed: false,
      modifiedAt: modifiedAt,
    };

    axios
      .post("http://localhost:8080/", newTodo)
      .then((response) => {
        setTodos([response.data, ...todos]);
      })
      .catch((error) => {
        console.error("할 일 생성 중 오류가 발생했습니다!", error);
      });
  };

  // 할 일 업데이트 함수
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
          setTodos(
            todos.map((todo) => (todo.id === targetId ? response.data : todo))
          );
        })
        .catch((error) => {
          console.error("할 일 업데이트 중 오류가 발생했습니다!", error);
        });
    }
  };

  // 할 일 삭제 함수
  const onDelete = (targetId) => {
    axios
      .delete(`http://localhost:8080/${targetId}`)
      .then(() => {
        setTodos(todos.filter((todo) => todo.id !== targetId));
      })
      .catch((error) => {
        console.error("할 일 삭제 중 오류가 발생했습니다!", error);
      });
  };
  // 할 일 전체삭제 함수
  const onAllDelete = () => {
    axios
        .delete(`http://localhost:8080/`)
        .then(() => {setTodos([]);})
        .catch((error) => {
          console.error("할 일 삭제 중 오류가 발생했습니다!", error);
        });
  };
  {/* 완료된 할 일 전체삭제 함수
  const onAllCompletedDelete = () => {
    if (filter === "COMPLETED")
    axios
        .delete(`http://localhost:8080/`)
        .then(() => {setTodos([]);})
        .catch((error) => {
          console.error("할 일 삭제 중 오류가 발생했습니다!", error);
        });
  };*/}

  return (
    <div className="App">
      <Header />
      <Editor onCreate={onCreate} />
      <List todos={todos} onUpdate={onUpdate} onDelete={onDelete} onAllDelete={onAllDelete} />
    </div>
  );
}

export default App;
