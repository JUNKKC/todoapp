import React, { useState, useRef, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import Header from './components/Header';
import Editor from './components/Editor';
import List from './components/List';
import Signup from './logins/Signup';
import Login from './logins/Login';
import UserProfile from './logins/UserProfile';
import axios from 'axios';
import './App.css';
import Top from "./components/Top.jsx";

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
    const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('token'));
    const [userName, setUserName] = useState('');
    const [userId, setUserId] = useState(null);
    const [isEditingProfile, setIsEditingProfile] = useState(false);
    const idRef = useRef(0);
    const navigate = useNavigate();

    useEffect(() => {
        if (isLoggedIn) {
            fetchUserName();
        }
    }, [isLoggedIn]);

    const fetchUserName = async () => {
        try {
            const response = await axiosInstance.get('/members/me');
            setUserName(response.data.name);
            setUserId(response.data.memberId);
        } catch (error) {
            console.error('사용자 정보를 가져오는 중 오류가 발생했습니다!', error);
            alert('사용자 정보를 가져오는 중 오류가 발생했습니다.');
        }
    };

    useEffect(() => {
        if (isLoggedIn && !isEditingProfile) {
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
    }, [isLoggedIn, isEditingProfile]);

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
                navigate('/login');
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
                localStorage.setItem('token', response.data.accessToken);
                setIsLoggedIn(true);
                setIsEditingProfile(false);
                navigate('/');
            } else {
                alert('로그인 실패: 아이디 또는 비밀번호를 확인하세요.');
            }
        } catch (error) {
            console.error('로그인 중 오류가 발생했습니다!', error);
            alert(error.response?.data?.message || '서버 오류가 발생했습니다.');
        }
    };

    const handleProfileUpdate = async (name, oldPassword, newPassword) => {
        try {
            const response = await axiosInstance.patch(`/members/${userId}`, {
                name,
                oldPassword,
                newPassword,
            });

            alert('회원 정보가 성공적으로 수정되었습니다. 다시 로그인하세요.');
            handleLogout();
        } catch (error) {
            throw error;
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        setIsLoggedIn(false);
        setUserName('');
        navigate('/login');
    };

    const handleSwitchToLogin = () => {
        navigate('/login');
    };

    return (
        <div className="App">
            <Top />
            <Routes>
                <Route path="/" element={isLoggedIn ? (
                    <>
                        <Header name={userName} onLogout={handleLogout} onEditProfile={() => navigate('/profile')} />
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
                ) : (
                    <Navigate to="/login" />
                )} />
                <Route path="/login" element={!isLoggedIn ? (
                    <div>
                        <Login onLogin={handleLogin} />
                        <div className="login">
                            <button className="new" onClick={() => navigate('/signup')}>회원가입</button>
                        </div>
                    </div>
                ) : (
                    <Navigate to="/" />
                )} />
                <Route path="/signup" element={!isLoggedIn ? (
                    <Signup onSignup={handleSignup} onSwitchToLogin={handleSwitchToLogin} />
                ) : (
                    <Navigate to="/" />
                )} />
                <Route path="/profile" element={isLoggedIn ? (
                    <UserProfile
                        userId={userId}
                        userName={userName}
                        onProfileUpdate={handleProfileUpdate}
                        onLogout={handleLogout}
                        onGoBack={() => navigate('/')}
                    />
                ) : (
                    <Navigate to="/login" />
                )} />
            </Routes>
        </div>
    );
}

function AppWrapper() {
    return (
        <Router>
            <App />
        </Router>
    );
}

export default AppWrapper;