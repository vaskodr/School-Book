// DashboardComponent.js (for any role)
import { useNavigate } from "react-router-dom";

const DashboardComponent = () => {
    const navigate = useNavigate();

    const handleSwitchRole = () => {
        navigate("/select-role");
    };

    return (
        <div>
            <h1>Dashboard</h1>
            <button onClick={handleSwitchRole}>Switch Role</button>
            {/* Dashboard content */}
        </div>
    );
};

export default DashboardComponent;