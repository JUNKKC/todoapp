import React, { useState } from 'react';


function Signup({ onSignup }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }
        else if(email === '' || password === '' || name === '') {
            alert('빈 칸을 모두 채워주세요.');
            return;
        }
        onSignup({ email, password ,name});
    };

    return (
        <div>
            <h2>회원가입</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>아이디: </label>
                    <input
                        type="text"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div>
                    <label>이름: </label>
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
    );
}

export default Signup;