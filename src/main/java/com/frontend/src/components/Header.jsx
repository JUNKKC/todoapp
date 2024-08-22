import "./Header.css";
const Header = () => {
  return (
    <div className="Header">
      <h3>오늘은 해야할일 📆</h3>
      <h1>{new Date().toLocaleDateString("ko-KR",{year: "numeric",
          month: "long",
          day: "numeric",})}</h1>
    </div>
  );
};
export default Header;
