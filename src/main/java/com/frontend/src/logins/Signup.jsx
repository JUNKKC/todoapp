import React, { useState } from 'react';
import './Signup.css';

function Signup({ onSignup, onSwitchToLogin }) { // onSwitchToLogin 콜백 추가
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!validateEmail(email)) {
            alert('유효한 이메일 주소를 입력해주세요.');
            return;
        }
        if (name.length > 8) {
            alert('닉네임은 8자 이하로 작성해주세요');
            return;
        }
        if (password !== confirmPassword) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }

        if (email === '' || password === '' || name === '') {
            alert('빈 칸을 모두 채워주세요.');
            return;
        }

        onSignup({ email, password, name });
    };

    return (
        <div>
        <div className="signup-container">
            <h2>회원가입</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>이메일: </label>
                    <input
                        type="text"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div>
                    <label>닉네임: </label>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}/>
                </div>
                <div>
                    <label>비밀번호: </label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <div>
                    <label>비밀번호 확인: </label>
                    <input
                        type="password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                </div>
                <button type="submit">회원가입</button>

            </form>
        </div>
            <div className="login">
            <button className="togologin" onClick={onSwitchToLogin}>로그인 하러가기</button>

        </div>
        </div>
    );
}

export default Signup;
