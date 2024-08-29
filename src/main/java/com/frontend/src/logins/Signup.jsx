import React, { useState } from 'react';
import './Signup.css';

function Signup({ onSignup, onSwitchToLogin }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    const containsKorean = (text) => /[\u3131-\uD79D]/g.test(text);
    const containsWhitespace = (text) => /\s/g.test(text);
    const containsInvalidCharacters = (text) => /[^a-zA-Z0-9@.]/g.test(text);

    const handleSubmit = (e) => {
        e.preventDefault();

        let errorMessage = '';

        if (email === '' || password === '' || name === '') {
            errorMessage = '빈 칸을 모두 채워주세요.';
        } else if (!validateEmail(email)) {
            errorMessage = '유효한 이메일 주소를 입력해주세요.';
        } else if (containsKorean(email)) {
            errorMessage = '이메일 주소에는 한글을 사용할 수 없습니다.';
        } else if (containsWhitespace(email)) {
            errorMessage = '이메일 주소에는 공백을 사용할 수 없습니다.';
        } else if (containsInvalidCharacters(email)) {
            errorMessage = '이메일에는 @,. 를 제외한 특수기호를 사용할 수 없습니다.';
        } else if (name.length > 8) {
            errorMessage = '닉네임은 8자 이하로 작성해주세요.';
        } else if (password !== confirmPassword) {
            errorMessage = '비밀번호가 일치하지 않습니다.';
        }

        if (errorMessage) {
            alert(errorMessage);
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
                            onChange={(e) => setName(e.target.value)}
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
                <button className="togologin" onClick={onSwitchToLogin}>뒤로가기</button>
            </div>
        </div>
    );
}

export default Signup;