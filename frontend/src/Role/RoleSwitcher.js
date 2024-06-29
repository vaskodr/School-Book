import React from 'react';
import { useNavigate } from 'react-router-dom';

const RoleSwitcher = ({ roles, schoolId }) => {
    const navigate = useNavigate();

    const handleRoleSelect = (role) => {
        localStorage.setItem('selectedRole', role);
        if (role === 'ROLE_ADMIN') {
            navigate(`/admin/dashboard/`);
        } else if (role === 'ROLE_STUDENT') {
            navigate(`/student/dashboard/${schoolId}`);
        } else if (role === 'ROLE_TEACHER') {
            navigate(`/teacher/dashboard/${schoolId}`);
        }
    };

    return (
        <div>
            <h2>Switch Role</h2>
            {roles.map((role) => (
                <button key={role} onClick={() => handleRoleSelect(role)}>
                    {role}
                </button>
            ))}
        </div>
    );
};

export default RoleSwitcher;