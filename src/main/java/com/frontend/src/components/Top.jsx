import "./Top.css";

const Top = () => {
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
        </div>
    );
};

export default Top;
