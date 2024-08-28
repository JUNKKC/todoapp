import React, { useState, useRef, useEffect } from 'react';
import Header from './components/Header';
import Editor from './components/Editor';
import List from './components/List';
import Signup from '././logins/Signup';
import Login from '././logins/Login';
import axios from 'axios';
import './App.css';

// 토큰을 안전하게 가져오기 위해 즉시 실행 함수(IIFE)를 사용
const token = localStorage.getItem('token') || '';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : '',
    },
});

axiosInstance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

function App() {
    const [todos, setTodos] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [filteredTodos, setFilteredTodos] = useState([]);
    const [isSignup, setIsSignup] = useState(false);
    const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('token')); // 초기 로그인 상태 설정
    const [userName, setUserName] = useState('');
    const idRef = useRef(0);

    useEffect(() => {
        if (isLoggedIn) {
            fetchUserName();
        }
    }, [isLoggedIn]);

    const fetchUserName = async () => {
        try {
            const response = await axiosInstance.get('/members/me');
            setUserName(response.data.name);
        } catch (error) {
            console.error('사용자 정보를 가져오는 중 오류가 발생했습니다!', error);
            alert('사용자 정보를 가져오는 중 오류가 발생했습니다.');
        }
    };

    useEffect(() => {
        if (isLoggedIn) {
            const fetchTodos = async () => {
                try {
                    const response = await axiosInstance.get('/todos/');
                    setTodos(response.data);
                    setFilteredTodos(response.data);
                    idRef.current =
                        response.data.length > 0
                            ? Math.max(...response.data.map((todo) => todo.id)) + 1
                            : 1;
                } catch (error) {
                    console.error('할 일 목록을 가져오는 중 오류가 발생했습니다!', error);
                    alert('할 일 목록을 가져오는 중 오류가 발생했습니다.');
                }
            };

            fetchTodos();
        }
    }, [isLoggedIn]);

    const onCreate = async (title, modifiedAt) => {
        const newTodo = {
            title,
            todoOrder: todos.length,
            completed: false,
            modifiedAt,
        };

        try {
            const response = await axiosInstance.post('/todos/', newTodo);
            setTodos([response.data, ...todos]);
            setFilteredTodos([response.data, ...todos]);
        } catch (error) {
            console.error('할 일 생성 중 오류가 발생했습니다!', error);
            alert('할 일 생성 중 오류가 발생했습니다: ' + (error.response?.data?.message || error.message));
        }
    };

    const onUpdate = async (targetId) => {
        const updatedTodo = todos.find((todo) => todo.id === targetId);
        if (updatedTodo) {
            const updatedData = {
                ...updatedTodo,
                completed: !updatedTodo.completed,
            };

            try {
                const response = await axiosInstance.patch(`/todos/${targetId}`, updatedData);
                const updatedTodos = todos.map((todo) =>
                    todo.id === targetId ? response.data : todo
                );
                setTodos(updatedTodos);
                setFilteredTodos(updatedTodos);
            } catch (error) {
                console.error('할 일 업데이트 중 오류가 발생했습니다!', error);
                alert('할 일 업데이트 중 오류가 발생했습니다: ' + (error.response?.data?.message || error.message));
            }
        }
    };

    const onDelete = async (targetId) => {
        try {
            await axiosInstance.delete(`/todos/${targetId}`);
            const remainingTodos = todos.filter((todo) => todo.id !== targetId);
            setTodos(remainingTodos);
            setFilteredTodos(remainingTodos);
        } catch (error) {
            console.error('할 일 삭제 중 오류가 발생했습니다!', error);
            alert('할 일 삭제 중 오류가 발생했습니다: ' + (error.response?.data?.message || error.message));
        }
    };

    const onAllDelete = async () => {
        if (todos.length !== 0) {
            try {
                await axiosInstance.delete('/todos/');
                setTodos([]);
                setFilteredTodos([]);
            } catch (error) {
                console.error('할 일 전체 삭제 중 오류가 발생했습니다!', error);
                alert('할 일 전체 삭제 중 오류가 발생했습니다: ' + (error.response?.data?.message || error.message));
            }
        }
    };

    const handleSearch = async () => {
        try {
            const response = await axiosInstance.get('/todos/search', {
                params: { title: searchTerm },
            });
            setFilteredTodos(response.data);
        } catch (error) {
            console.error('할 일 검색 중 오류가 발생했습니다!', error);
            alert('할 일 검색 중 오류가 발생했습니다: ' + (error.response?.data?.message || error.message));
        }
    };

    const handleSignup = async (userInfo) => {
        try {
            const response = await axios.post('http://localhost:8080/members/', userInfo);
            if (response.status === 201) {
                alert('회원가입 성공! 이제 로그인하세요.');
                setIsSignup(false);
            }
        } catch (error) {
            console.error('회원가입 중 오류가 발생했습니다!', error);
            alert('회원가입 실패: ' + (error.response?.data?.message || error.message));
        }
    };

    const handleLogin = async (credentials) => {
        try {
            const response = await axiosInstance.post('/auth/login', credentials);

            if (response.status === 200) {
                const existingToken = localStorage.getItem('token');
                const newToken = response.data.accessToken;

                if (existingToken !== newToken) {
                    localStorage.setItem('token', newToken);
                    console.log('토큰이 변경되었습니다.');
                } else {
                    console.log('토큰이 동일합니다.');
                }

                setIsLoggedIn(true);
            } else {
                alert('로그인 실패: 아이디 또는 비밀번호를 확인하세요.');
            }
        } catch (error) {
            console.error('로그인 중 오류가 발생했습니다!', error);
            alert( (error.response?.data?.message || '서버 오류가 발생했습니다.'));
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        setIsLoggedIn(false);
        setUserName('');
    };

    return (
        <div className="App">
            {isLoggedIn ? (
                <>
                    <Header name={userName} onLogout={handleLogout} />
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
                </>
            ) : isSignup ? (
                <Signup onSignup={handleSignup} />
            ) : (
                <div>
                    <Login onLogin={handleLogin} />
                    <div className="login">
                        <button className="new" onClick={() => setIsSignup(true)}>회원가입</button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default App;