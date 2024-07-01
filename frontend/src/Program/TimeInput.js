import React, { useState, useEffect } from 'react';
import { Form } from 'react-bootstrap';

const TimeInput = ({ value, onChange, isInvalid, controlId, label, error }) => {
    const [timeValue, setTimeValue] = useState(value);

    useEffect(() => {
        setTimeValue(value);
    }, [value]);

    const handleTimeChange = (event) => {
        const newValue = event.target.value;
        setTimeValue(newValue);
        onChange(newValue);
    };

    return (
        <Form.Group controlId={controlId}>
            <Form.Label>{label}</Form.Label>
            <Form.Control
                type="time"
                value={timeValue}
                onChange={handleTimeChange}
                isInvalid={isInvalid}
                step={1} // Ensure seconds are displayed
            />
            <Form.Control.Feedback type="invalid">{error}</Form.Control.Feedback>
        </Form.Group>
    );
};

export default TimeInput;