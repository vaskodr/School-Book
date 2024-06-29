import React, { useEffect, useState } from 'react';

const SchoolList = () => {
    const [schools, setSchools] = useState([]);
    const [selectedSchool, setSelectedSchool] = useState(null);
    const [classes, setClasses] = useState([]);

    useEffect(() => {
        const fetchSchools = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/school');
                if (response.ok) {
                    const data = await response.json();
                    setSchools(data);
                } else {
                    console.error('Failed to fetch schools');
                }
            } catch (error) {
                console.error('Error fetching schools:', error);
            }
        };

        fetchSchools();
    }, []);

    const fetchClasses = async (schoolId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes`);
            if (response.ok) {
                const data = await response.json();
                setClasses(data);
            } else {
                console.error('Failed to fetch classes');
            }
        } catch (error) {
            console.error('Error fetching classes:', error);
        }
    };

    const handleSchoolClick = (schoolId) => {
        setSelectedSchool(schoolId);
        fetchClasses(schoolId);
    };

    return (
        <div>
            <h1>Schools</h1>
            <div>
                {schools.map((school) => (
                    <button key={school.id} onClick={() => handleSchoolClick(school.id)}>
                        {school.name}
                    </button>
                ))}
            </div>
            {selectedSchool && (
                <div>
                    <h2>Classes for School {selectedSchool}</h2>
                    {classes.length > 0 ? (
                        <ul>
                            {classes.map((classItem) => (
                                <li key={classItem.id}>
                                    {classItem.name} - Level: {classItem.level}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No classes available for this school.</p>
                    )}
                </div>
            )}
        </div>
    );
};

export default SchoolList;