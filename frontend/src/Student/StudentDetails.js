import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';

const StudentDetails = () => {
    const { schoolId, classId, studentId } = useParams();
    const [studentDetails, setStudentDetails] = useState(null);
    const [selectedParent, setSelectedParent] = useState(null);
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchStudentDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/student/${studentId}/details`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setStudentDetails(data);
                } else {
                    console.error('Failed to fetch student details');
                }
            } catch (error) {
                console.error('Error fetching student details:', error);
            }
        };

        fetchStudentDetails();
    }, [schoolId, classId, studentId, authData]);

    const handleDeleteParent = async (parentId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/parents/${parentId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                setStudentDetails((prevDetails) => ({
                    ...prevDetails,
                    parents: prevDetails.parents.filter((parent) => parent.id !== parentId),
                }));
            } else {
                console.error('Failed to delete parent');
            }
        } catch (error) {
            console.error('Error deleting parent:', error);
        }
    };

    const handleUnassignParent = async (parentId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/parent/${parentId}/student/${studentId}`, {
                method: 'POST',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                setStudentDetails((prevDetails) => ({
                    ...prevDetails,
                    parents: prevDetails.parents.filter((parent) => parent.id !== parentId),
                }));
            } else {
                console.error('Failed to unassign parent');
            }
        } catch (error) {
            console.error('Error unassigning parent:', error);
        }
    };

    const handleSaveParent = async (parent) => {
        const method = parent.id ? 'PUT' : 'POST';
        const url = parent.id ? `http://localhost:8080/api/parents/${parent.id}` : `http://localhost:8080/api/parents`;
        try {
            const response = await fetch(url, {
                method,
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify(parent),
            });
            if (response.ok) {
                const data = await response.json();
                setStudentDetails((prevDetails) => {
                    const parents = parent.id
                        ? prevDetails.parents.map((p) => (p.id === parent.id ? data : p))
                        : [...prevDetails.parents, data];
                    return { ...prevDetails, parents };
                });
                setSelectedParent(null);
            } else {
                console.error('Failed to save parent');
            }
        } catch (error) {
            console.error('Error saving parent:', error);
        }
    };

    const handleEditParent = (parent) => {
        setSelectedParent(parent);
    };

    const handleCreateParent = () => {
        setSelectedParent({ firstName: '', lastName: '', phone: '', email: '', username: '' });
    };

    if (!studentDetails) {
        return <p>Loading student details...</p>;
    }

    return (
        <div className="container mt-4">
            <h2>Student Details</h2>
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{studentDetails.firstName} {studentDetails.lastName}</h5>
                    <p className="card-text"><strong>Date of Birth:</strong> {studentDetails.dateOfBirth}</p>
                    <p className="card-text"><strong>Gender:</strong> {studentDetails.gender}</p>
                    <p className="card-text"><strong>Phone:</strong> {studentDetails.phone}</p>
                    <p className="card-text"><strong>Email:</strong> {studentDetails.email}</p>
                    <p className="card-text"><strong>Username:</strong> {studentDetails.username}</p>
                    <p className="card-text"><strong>School:</strong> {studentDetails.schoolName}</p>
                    <p className="card-text"><strong>Class:</strong> {studentDetails.classDTO.name} ({studentDetails.classDTO.level})</p>
                    <h5>Parents</h5>
                    <ul className="list-group">
                        {studentDetails.parents && studentDetails.parents.length > 0 ? (
                            studentDetails.parents.map((parent) => (
                                <li key={parent.id} className="list-group-item">
                                    <p><strong>Name:</strong> {parent.firstName} {parent.lastName}</p>
                                    <p><strong>Phone:</strong> {parent.phone}</p>
                                    <p><strong>Email:</strong> {parent.email}</p>
                                    <p><strong>Username:</strong> {parent.username}</p>
                                    <button className="btn btn-primary me-2" onClick={() => handleEditParent(parent)}>Edit</button>
                                    <button className="btn btn-danger me-2" onClick={() => handleDeleteParent(parent.id)}>Delete</button>
                                    <button className="btn btn-warning" onClick={() => handleUnassignParent(parent.id)}>Unassign</button>
                                </li>
                            ))
                        ) : (
                            <li className="list-group-item">No parents available</li>
                        )}
                    </ul>
                    <button className="btn btn-success mt-3" onClick={handleCreateParent}>Add Parent</button>
                    <button className="btn btn-secondary mt-3" onClick={() => navigate(-1)}>Back</button>
                </div>
            </div>
            {selectedParent && (
                <ParentForm
                    parent={selectedParent}
                    onSave={handleSaveParent}
                    onCancel={() => setSelectedParent(null)}
                />
            )}
        </div>
    );
};

const ParentForm = ({ parent, onSave, onCancel }) => {
    const [formData, setFormData] = useState(parent);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave(formData);
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <h4>{parent.id ? 'Edit Parent' : 'Add Parent'}</h4>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>First Name</label>
                        <input type="text" name="firstName" value={formData.firstName} onChange={handleChange} className="form-control" />
                    </div>
                    <div className="form-group">
                        <label>Last Name</label>
                        <input type="text" name="lastName" value={formData.lastName} onChange={handleChange} className="form-control" />
                    </div>
                    <div className="form-group">
                        <label>Phone</label>
                        <input type="text" name="phone" value={formData.phone} onChange={handleChange} className="form-control" />
                    </div>
                    <div className="form-group">
                        <label>Email</label>
                        <input type="email" name="email" value={formData.email} onChange={handleChange} className="form-control" />
                    </div>
                    <div className="form-group">
                        <label>Username</label>
                        <input type="text" name="username" value={formData.username} onChange={handleChange} className="form-control" />
                    </div>
                    <button type="submit" className="btn btn-primary">Save</button>
                    <button type="button" className="btn btn-secondary" onClick={onCancel}>Cancel</button>
                </form>
            </div>
        </div>
    );
};

export default StudentDetails;