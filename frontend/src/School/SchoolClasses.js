import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import TeacherRegistrationModal from '../Teacher/TeacherRegistrationModal';

const SchoolClasses = () => {
    const { schoolId } = useParams();
    const [classes, setClasses] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchClasses = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/all`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
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

        fetchClasses();
    }, [schoolId, authData]);

    const handleClassClick = (classId) => {
        navigate(`/admin/dashboard/school/${schoolId}/classes/${classId}`);
    };

    const handleAddTeacherClick = () => {
        setIsModalOpen(true);
    };

    const handleModalClose = () => {
        setIsModalOpen(false);
    };

    const handleFormSubmit = async (formData) => {
        try {
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/teacher/add-teacher`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify(formData),
            });
            if (!response.ok) {
                console.error('Failed to register teacher');
            } else {
                // Optionally, refetch classes or teachers to update the UI
            }
        } catch (error) {
            console.error('Error registering teacher:', error);
        }
    };

    const handleViewTeachersClick = () => {
        navigate(`/admin/dashboard/school/${schoolId}/teachers`);
    };

    return (
        <div>
            <h2>Classes for School {schoolId}</h2>
            <button onClick={handleAddTeacherClick}>Add Teacher</button>
            <button onClick={handleViewTeachersClick}>View All Teachers</button>
            {classes.length > 0 ? (
                <div>
                    {classes.map((classItem) => (
                        <div key={classItem.id}>
                            <button onClick={() => handleClassClick(classItem.id)}>
                                {classItem.name}
                            </button>
                            {/* Add more buttons for CRUD operations */}
                        </div>
                    ))}
                </div>
            ) : (
                <p>Loading classes...</p>
            )}
            <TeacherRegistrationModal
                isOpen={isModalOpen}
                onRequestClose={handleModalClose}
                onSubmit={handleFormSubmit}
            />
        </div>
    );
};

export default SchoolClasses;