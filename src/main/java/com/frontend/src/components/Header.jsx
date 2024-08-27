import "./Header.css";
const Header = () => {
  return (
      <div className="Header">
          <div className="h"><h1>📆</h1>
          <h4 className="date">{new Date().toLocaleDateString("ko-KR", {
              year: "numeric",
              month: "long",
              day: "numeric",
          })}</h4></div>
          <h2> 의 오늘 할 일</h2>

      </div>
  );
};
export default Header;
