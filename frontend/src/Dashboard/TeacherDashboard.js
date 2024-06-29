import React, { useEffect, useState } from 'react';

function TeacherDashboard() {
    const [school, setSchool] = useState(null);

    useEffect(() => {
        async function fetchSchool() {
            const schoolId = new URLSearchParams(window.location.search).get('schoolId');
            const response = await fetch(`/api/school/${schoolId}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });
            const data = await response.json();
            setSchool(data);
        }
        fetchSchool();
    }, []);

    if (!school) return <div>Loading...</div>;

    return (
        <div>
            <h1>Welcome to {school.name}</h1>
            {/* Render teacher's class sessions or schedule here */}
        </div>
    );
}

export default TeacherDashboard;