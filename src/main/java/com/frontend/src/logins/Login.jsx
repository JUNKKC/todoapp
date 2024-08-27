import React, { useState } from 'react';
import './Login.css';
function Login({ onLogin }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        if(username === ``){
            alert("아이디를 입력해주세요")
            return
        }
        onLogin({ username, password });
    };

    return (
        <div className="login-container">
            <h2>로그인</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>아이디: </label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>비밀번호: </label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">로그인</button>
            </form>
        </div>
    );
}

export default Login;