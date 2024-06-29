import React from 'react';
import { useAuth } from './AuthContext';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children, roles }) => {
  const { authData } = useAuth();

  if (!authData) {
    return <Navigate to="/login" />;
  }

  if (roles && !roles.some(role => authData.roles.includes(role))) {
    return <Navigate to="/" />;
  }

  return children;
};

export default PrivateRoute;
