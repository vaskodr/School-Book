import React, { useEffect, useState, useContext } from 'react';
import { AuthContext } from '../Auth/AuthContext';
import { useNavigate } from 'react-router-dom';
import { Button, Container, Form, Table } from 'react-bootstrap';

const SubjectsManagement = () => {
    const [subjects, setSubjects] = useState([]);
    const [newSubjectName, setNewSubjectName] = useState('');
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSubjects = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/subject/all`, {
                    headers: {
                        Authorization: `Bearer ${authData.accessToken}`,
                    },
                });
                if (response.ok) {
                    const data = await response.json();
                    setSubjects(data);
                } else {
                    console.error('Failed to fetch subjects');
                }
            } catch (error) {
                console.error('Error fetching subjects:', error);
            }
        };

        fetchSubjects();
    }, [authData]);

    const handleAddSubject = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/api/subject/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify({ name: newSubjectName }),
            });
            if (response.ok) {
                const newSubject = await response.json();
                setSubjects([...subjects, newSubject]);
                setNewSubjectName('');
            } else {
                console.error('Failed to add subject');
            }
        } catch (error) {
            console.error('Error adding subject:', error);
        }
    };

    const handleEditSubject = (subjectId) => {
        const newName = prompt('Enter the new subject name:');
        if (newName) {
            const updateSubject = async () => {
                try {
                    const response = await fetch(`http://localhost:8080/api/subject/${subjectId}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: `Bearer ${authData.accessToken}`,
                        },
                        body: JSON.stringify({ name: newName }),
                    });
                    if (response.ok) {
                        setSubjects(subjects.map(subject => subject.id === subjectId ? { ...subject, name: newName } : subject));
                    } else {
                        console.error('Failed to update subject');
                    }
                } catch (error) {
                    console.error('Error updating subject:', error);
                }
            };
            updateSubject();
        }
    };

    const handleDeleteSubject = async (subjectId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/subject/${subjectId}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                setSubjects(subjects.filter(subject => subject.id !== subjectId));
            } else {
                console.error('Failed to delete subject');
            }
        } catch (error) {
            console.error('Error deleting subject:', error);
        }
    };

    return (
        <Container className="mt-4">
            <h2>Manage Subjects</h2>
            <Form onSubmit={handleAddSubject} className="mb-4">
                <Form.Group controlId="newSubjectName" className="mb-3">
                    <Form.Label>New Subject Name</Form.Label>
                    <Form.Control
                        type="text"
                        value={newSubjectName}
                        onChange={(e) => setNewSubjectName(e.target.value)}
                        required
                    />
                </Form.Group>
                <Button variant="primary" type="submit">Add Subject</Button>
            </Form>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Subject Name</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {subjects.map((subject, index) => (
                    <tr key={subject.id}>
                        <td>{index + 1}</td>
                        <td>{subject.name}</td>
                        <td>
                            <Button variant="warning" className="me-2" onClick={() => handleEditSubject(subject.id)}>Edit</Button>
                            <Button variant="danger" onClick={() => handleDeleteSubject(subject.id)}>Delete</Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <Button variant="secondary" className="mt-3" onClick={() => navigate(-1)}>Back to Dashboard</Button>
        </Container>
    );
};

export default SubjectsManagement;