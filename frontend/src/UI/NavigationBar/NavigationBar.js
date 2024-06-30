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
            <button className="navbar-button" onClick={() => navigate('/admin/dashboard')}>Admin Dashboard</button>
          )}
          {authData.roles.includes('ROLE_DIRECTOR') && (
            <button className="navbar-button" onClick={() => navigate(`/director/dashboard/${authData.userDetailsDTO.schoolId}`)}>Director Dashboard</button>
          )}
          {authData.roles.includes('ROLE_TEACHER') && (
            <button className="navbar-button" onClick={() => navigate(`/teacher/dashboard/${authData.userDetailsDTO.schoolId}`)}>Teacher Dashboard</button>
          )}
          {authData.roles.includes('ROLE_PARENT') && (
            <button className="navbar-button" onClick={() => navigate(`/parent/dashboard`)}>Parent Dashboard</button>
          )}
          {authData.roles.includes('ROLE_STUDENT') && (
            <button className="navbar-button" onClick={() => navigate(`/student/dashboard/${authData.userDetailsDTO.schoolId}`)}>Student Dashboard</button>
          )}
          <button className="navbar-button" onClick={handleLogoutClick}>Logout</button>
        </>
      )}
      {isLoginModalOpen && <LoginModal onClose={handleCloseModal} />}
    </div>
  );
};

export default NavigationBar;
