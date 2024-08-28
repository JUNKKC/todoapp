import "./Header.css";

const Header = ({ onLogout, name }) => {
    return (
        <div className="Header">

            <div className="logout-button">
                {/* name prop을 사용하여 사용자 이름을 표시 */}
                <h2>{name} 의 오늘 할 일</h2>
                <button onClick={onLogout}>로그아웃</button>
            </div>
        </div>
    );
};

export default Header;
