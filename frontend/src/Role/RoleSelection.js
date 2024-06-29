// RoleSelection.js
import { useNavigate } from "react-router-dom";

const RoleSelection = () => {
    const navigate = useNavigate();
    const roles = JSON.parse(localStorage.getItem("roles"));
    const userDetails = JSON.parse(localStorage.getItem("userDetails"));

    const handleRoleSelection = (role) => {
        if (role === "ROLE_STUDENT") {
            navigate(`/student/dashboard/${userDetails.schoolId}`);
        } else if (role === "ROLE_TEACHER") {
            navigate(`/teacher/dashboard/${userDetails.schoolId}`);
        } else if (role === "ROLE_ADMIN") {
            navigate(`/admin/dashboard`);
        }
    };

    return (
        <div>
            <h1>Select Your Role</h1>
            <ul>
                {roles.map((role) => (
                    <li key={role}>
                        <button onClick={() => handleRoleSelection(role)}>
                            {role.replace("ROLE_", "")}
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default RoleSelection;