import React, { useState } from 'react';
import './NavigationBar.css';
import LoginModal from '../LoginModal/LoginModal';
import { useAuth } from '../../Auth/AuthContext';
import { useNavigate } from 'react-router-dom';

const NavigationBar = () => {
  const [isLoginModalOpen, setLoginModalOpen] = useState(false);
  const { authData, logout } = useAuth();
  const navigate = useNavigate();

  const handleLoginClick = () => {
    setLoginModalOpen(true);
  };

  const handleCloseModal = () => {
    setLoginModalOpen(false);
  };

  const handleLogoutClick = () => {
    logout();
    navigate('/');
  };

  return (
    <div className="navbar">
      {!authData ? (
        <button className="navbar-button" onClick={handleLoginClick}>Вход</button>
      ) : (
        <>
          {authData.roles.includes('ROLE_ADMIN') && (
            <button className="navbar-button" onClick={() => navigate('/admin/dashboard')}>Admin Home</button>
          )}
          {authData.roles.includes('ROLE_STUDENT') && (
            <button className="navbar-button" onClick={() => navigate(`/student/dashboard/${authData.userDetailsDTO.schoolId}`)}>Student Home</button>
          )}
          {authData.roles.includes('ROLE_TEACHER') && (
            <button className="navbar-button" onClick={() => navigate(`/teacher/dashboard/${authData.userDetailsDTO.schoolId}`)}>Teacher Home</button>
          )}
          <button className="navbar-button" onClick={handleLogoutClick}>Logout</button>
        </>
      )}
      {isLoginModalOpen && <LoginModal onClose={handleCloseModal} />}
    </div>
  );
};

export default NavigationBar;
