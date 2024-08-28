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

    // 한글을 감지하는 함수
    const containsKorean = (text) => /[\u3131-\uD79D]/g.test(text);

    // 공백을 감지하는 함수
    const containsWhitespace = (text) => /\s/g.test(text);

    // 이메일에 허용된 문자만 남기는 함수 (@, ., 영어 알파벳, 숫자만 허용)
    const filterInvalidCharacters = (text) => text.replace(/[^a-zA-Z0-9@.]/g, '');

    const handleEmailChange = (e) => {
        const inputValue = e.target.value;
        const filteredValue = filterInvalidCharacters(inputValue);

        // 3항 연산자로 조건에 따른 경고 메시지 처리
        containsKorean(inputValue)
            ? alert('이메일 주소에는 한글을 사용할 수 없습니다.')
            : containsWhitespace(inputValue)
                ? alert('이메일 주소에는 공백을 사용할 수 없습니다.')
                : inputValue !== filteredValue
                    ? alert('이메일 주소에는 특수기호를 사용할 수 없습니다.')
                    : setEmail(filteredValue);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (email === '' || password === '' || name === '') {
            alert('빈 칸을 모두 채워주세요.');
            return;
        }

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
                            onChange={handleEmailChange}
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
                <button className="togologin" onClick={onSwitchToLogin}>로그인 하러가기</button>
            </div>
        </div>
    );
}

export default Signup;