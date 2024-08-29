import React, { useState } from 'react';
import './UserProfile.css';

const UserProfile = ({  userName, onProfileUpdate, onLogout, onGoBack }) => {
    const [name, setName] = useState(userName);
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const containsWhitespace = (text) => /\s/g.test(text);

        let errorMessage = '';

        if (!oldPassword || !newPassword || !confirmPassword || !name) {
            errorMessage = '모든 필드를 채워주세요.';
        }else if (containsWhitespace(name)) {
            errorMessage = '이름에는 공백을 사용할 수 없습니다.';
        } else if (name.length > 8) {
            errorMessage = '이름은 8자 이내로 입력하세요.';
        } else if (newPassword.length > 20) {
            errorMessage = '비밀번호는 20자 이하로 작성해주세요.';
        } else if (newPassword !== confirmPassword) {
            errorMessage = '새 비밀번호가 일치하지 않습니다.';
        } else if (containsWhitespace(newPassword)) {
            errorMessage = '비밀번호에는 공백을 사용할 수 없습니다.';
        }

        if (errorMessage) {
            alert (errorMessage);
            return;
        }

        try {
            await onProfileUpdate(name, oldPassword, newPassword);
            setSuccess('회원 정보가 성공적으로 수정되었습니다. 다시 로그인하세요.');
            setError('');
            onLogout();
        } catch (error) {
            if (error.response?.status === 401) {
                setError('기존 비밀번호가 일치하지 않습니다.');
            } else {
                setError('회원 정보를 수정하는 중 오류가 발생했습니다. ' + (error.response?.data?.message || ''));
            }
            setSuccess('');
        }
    };

    return (
        <div>
            <div className="signup-container">
                <form onSubmit={handleSubmit}>
                    <h2>내 정보 수정</h2>
                    <div>
                        <label>이름:</label>
                        <input
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            placeholder="새 이름을 입력하세요"
                        />
                    </div>
                    <div>
                        <label>기존 비밀번호:</label>
                        <input
                            type="password"
                            value={oldPassword}
                            onChange={(e) => setOldPassword(e.target.value)}
                            placeholder="기존 비밀번호를 입력하세요"
                        />
                    </div>
                    <div>
                        <label>새 비밀번호:</label>
                        <input
                            type="password"
                            value={newPassword}
                            onChange={(e) => setNewPassword(e.target.value)}
                            placeholder="새 비밀번호를 입력하세요"
                        />
                    </div>
                    <div>
                        <label>새 비밀번호 확인:</label>
                        <input
                            type="password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            placeholder="새 비밀번호를 다시 입력하세요"
                        />
                    </div>
                    <button type="submit">정보 수정</button>
                    {error && <div style={{color: 'red'}}>{error}</div>}
                    {success && <div style={{color: 'green'}}>{success}</div>}
                </form>
            </div>
            <div className="login">
                <button className="toback" onClick={onGoBack}>뒤로가기</button>
            </div>
        </div>
    );
};

export default UserProfile;