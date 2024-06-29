import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext';

const LoginForm = () => {
    const [usernameOrEmail, setUsernameOrEmail] = useState('');
    const [password, setPassword] = useState('');
    const { login } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();

        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ usernameOrEmail, password }),
            });

            if (response.ok) {
                const data = await response.json();
                console.log('Login successful:', data); // Debugging log
                login(data); // Set the auth data in context
                if (data.roles.includes("ROLE_ADMIN")) {
                    navigate(`/admin/dashboard`);
                }
                if (data.roles.includes("ROLE_STUDENT")) {
                    navigate(`/student/dashboard/${data.userDetailsDTO.schoolId}`);
                }
                if (data.roles.includes("ROLE_TEACHER")) {
                    navigate(`/teacher/dashboard/${data.userDetailsDTO.schoolId}`)
                }

            } else {
                console.error('Login failed');
            }
        } catch (error) {
            console.error('Error during login:', error);
        }
    };

    return (
        <form onSubmit={handleLogin}>
            <input
                type="text"
                value={usernameOrEmail}
                onChange={(e) => setUsernameOrEmail(e.target.value)}
                placeholder="Username or Email"
                required
            />
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
                required
            />
            <button type="submit">Login</button>
        </form>
    );
};

export default LoginForm;