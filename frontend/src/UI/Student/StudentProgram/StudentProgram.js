import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

class StudentProgram extends Component {
    constructor(props) {
        super(props);
        this.state = {
            program: null,
            showProgram: false,
        };
    }

    fetchProgramData = async () => {
        const { schoolId, studentId, authData } = this.props;
        console.log('Fetching program data for studentId:', studentId); // Debugging log

        try {
            console.log('School id: ', schoolId);
            console.log('Student id: ', studentId);
            const response = await fetch(`http://localhost:8080/api/school/${schoolId}/program/student/${studentId}/weekly-program`, {
                headers: {
                    Authorization: `Bearer ${authData.accessToken}`,
                },
            });
            if (response.ok) {
                const data = await response.json();
                console.log('Fetched program data:', data); // Debugging log
                this.setState({ program: data, showProgram: true });
            } else {
                console.error('Failed to fetch program data');
            }
        } catch (error) {
            console.error('Error fetching program data:', error);
        }
    };

    toggleProgramVisibility = () => {
        this.setState(prevState => ({ showProgram: !prevState.showProgram }));
    };

    generateTimeSlots = () => {
        const slots = [];
        let startTime = new Date();
        startTime.setHours(7, 30, 0, 0); // 7:30 AM
        const endTime = new Date();
        endTime.setHours(13, 30, 0, 0); // 1:30 PM

        while (startTime < endTime) {
            const endSlot = new Date(startTime.getTime() + 40 * 60000); // 40 minutes later
            slots.push(`${startTime.toTimeString().substring(0, 5)} - ${endSlot.toTimeString().substring(0, 5)}`);
            startTime = new Date(endSlot.getTime() + 10 * 60000); // 10 minutes break
        }

        return slots;
    };

    isTimeInRange = (session, timeSlot) => {
        const [slotStart, slotEnd] = timeSlot.split(' - ').map(time => this.parseTime(time));
        const sessionStart = this.parseTime(session.startTime);
        const sessionEnd = this.parseTime(session.endTime);
        return sessionStart >= slotStart && sessionEnd <= slotEnd;
    };

    parseTime = (timeStr) => {
        const [hours, minutes] = timeStr.split(':').map(Number);
        return new Date(1970, 0, 1, hours, minutes);
    };

    renderProgramTable = (program) => {
        console.log('Rendering program table with data:', program); // Debugging log
        const daysOfWeek = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];

        if (!program || !program.weeklyProgram) return null;

        const timeSlots = this.generateTimeSlots();

        return (
            <table className="table table-bordered">
                <thead className="thead-light">
                <tr>
                    <th>Time / Period</th>
                    {daysOfWeek.map(day => (
                        <th key={day}>{day}</th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {timeSlots.map((timeSlot, timeIndex) => (
                    <tr key={timeIndex}>
                        <td>{timeSlot}</td>
                        {daysOfWeek.map(day => (
                            <td key={day}>
                                {program.weeklyProgram[day.toUpperCase()]?.map((session, index) => (
                                    this.isTimeInRange(session, timeSlot) && (
                                        <div key={index} className="mb-2">
                                            <p className="mb-1"><strong>Subject:</strong> {session.subject}</p>
                                            <p className="mb-1"><strong>Teacher:</strong> {session.teacher}</p>
                                            <p className="mb-1"><strong>Time:</strong> {session.startTime} - {session.endTime}</p>
                                        </div>
                                    )
                                ))}
                            </td>
                        ))}
                    </tr>
                ))}
                </tbody>
            </table>
        );
    };

    render() {
        return (
            <div className="container mt-5">
                <button className="btn btn-primary mb-3" onClick={this.fetchProgramData}>
                    {this.state.showProgram ? 'Reload Student Program' : 'Load Student Program'}
                </button>
                {this.state.showProgram && (
                    <>
                        {this.renderProgramTable(this.state.program)}
                        <button className="btn btn-secondary mt-3" onClick={this.toggleProgramVisibility}>
                            Hide Program
                        </button>
                    </>
                )}
            </div>
        );
    }
}

export default StudentProgram;