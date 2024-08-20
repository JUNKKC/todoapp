import "./TodoItem.css";

const TodoItem = ({ id, completed, title, modifiedAt, onUpdate, onDelete }) => {
  const onChangeCheckbox = () => {

    onUpdate(id);
  };
  const onClickDeleteButton = () => {
    onDelete(id);
  };
  return (
      <div className="TodoItem">
          <input
              onChange={onChangeCheckbox}
              readOnly
              checked={completed}
              type="checkbox"
          />
          <div className={`content ${completed ? 'completed' : ''}`}>{title}</div>
          <div className="date">{new Date(modifiedAt).toLocaleDateString()}</div>
          <button onClick={onClickDeleteButton}>삭제</button>
      </div>
  );
};
export default TodoItem;
