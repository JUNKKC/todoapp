import React from 'react';
import './Header.css';

const Header = ({ onLogout, name, onEditProfile }) => {
    return (
        <div className="Header">
            <div className="logout-button">
                <h2>{name} 의 오늘 할 일</h2>
                <button onClick={onEditProfile}>내 정보</button> {/* 내 정보 버튼 추가 */}
                <button onClick={onLogout}>로그아웃</button>
            </div>
        </div>
    );
};

export default Header;