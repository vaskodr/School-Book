import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../Auth/AuthContext';
import { Form, Button, Container, Row, Col, Card } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const CreateSchool = () => {
    const [name, setName] = useState('');
    const [address, setAddress] = useState('');
    const { authData } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/school/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${authData.accessToken}`,
                },
                body: JSON.stringify({ name, address }),
            });
            if (response.ok) {
                navigate('/admin/dashboard');
            } else {
                console.error('Failed to create school');
            }
        } catch (error) {
            console.error('Error creating school:', error);
        }
    };

    return (
        <Container className="mt-5">
            <Row className="justify-content-center">
                <Col md={8}>
                    <Card>
                        <Card.Header as="h2" className="text-center">Create School</Card.Header>
                        <Card.Body>
                            <Form onSubmit={handleSubmit}>
                                <Form.Group controlId="formSchoolName" className="mb-3">
                                    <Form.Label>Name</Form.Label>
                                    <Form.Control
                                        type="text"
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                        placeholder="Enter school name"
                                        required
                                    />
                                </Form.Group>
                                <Form.Group controlId="formSchoolAddress" className="mb-3">
                                    <Form.Label>Address</Form.Label>
                                    <Form.Control
                                        type="text"
                                        value={address}
                                        onChange={(e) => setAddress(e.target.value)}
                                        placeholder="Enter school address"
                                        required
                                    />
                                </Form.Group>
                                <Button variant="primary" type="submit" className="me-2">Create School</Button>
                                <Button variant="secondary" onClick={() => navigate('/admin/dashboard')}>Back</Button>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default CreateSchool;