import React from 'react';
import './LoginModal.css';

const LoginModal = ({ onClose }) => {
  return (
      <div className="modal-overlay">
        <div className="modal-content">
          <span className="close-button" onClick={onClose}>×</span>
          <h2>Вход</h2>
          <form>
            <label>
              Потребителско име:
              <input type="text" name="username" />
            </label>
            <label>
              Парола:
              <input type="password" name="password" />
            </label>
            <button type="submit">Влез</button>
          </form>
        </div>
      </div>
  );
}

export default LoginModal;