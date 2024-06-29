import React, { createContext, useState, useContext } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [authData, setAuthData] = useState(null);
    //authData
    const login = (data) => {
        console.log('Setting auth data:', data);
        setAuthData(data);
    };

    const logout = () => {
        setAuthData(null);
        localStorage.removeItem('authToken');
    };

    return (
        <AuthContext.Provider value={{ authData, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);