import "./Header.css";

const Header = ({ onLogout }) => {
    return (
        <div className="Header">
            <div className="top">
                <h1>📆</h1>
                <h4 className="date">{new Date().toLocaleDateString("ko-KR", {
                    year: "numeric",
                    month: "long",
                    day: "numeric",
                })}</h4>
            </div>
            <div className="logout-button">
                <h2> 의 오늘 할 일</h2>
                <button  onClick={onLogout}>로그아웃</button>
            </div>

        </div>
    );
};

export default Header;