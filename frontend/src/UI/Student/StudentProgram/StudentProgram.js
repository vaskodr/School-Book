import React, { Component } from 'react';

class StudentProgram extends Component {
  constructor(props) {
    super(props);
    this.state = {
      program: null,
    };
  }

  fetchProgramData = async () => {
    const { schoolId, classId, authData, clearContent, setContent } = this.props;
    console.log('Fetching program data for classId:', classId); // Debugging log

    try {
      console.log('School id: ', schoolId);
      console.log('Class id: ', classId);
      const response = await fetch(`http://localhost:8080/api/school/${schoolId}/classes/${classId}/program`, {
        headers: {
          Authorization: `Bearer ${authData.accessToken}`,
        },
      });
      if (response.ok) {
        const data = await response.json();
        console.log('Fetched program data:', data); // Debugging log
        this.setState({ program: data });
        clearContent(); // Clear the content before rendering the new data
        setContent(this.renderProgramTable(data)); // Render the table in the cleared div
      } else {
        console.error('Failed to fetch program data');
      }
    } catch (error) {
      console.error('Error fetching program data:', error);
    }
  };

  renderProgramTable = (program) => {
    const daysOfWeek = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"];

    if (!program || !program.classSessions) return null;

    const sessionsByDay = daysOfWeek.reduce((acc, day) => {
      acc[day] = program.classSessions.filter(session => session.day === day);
      return acc;
    }, {});

    return (
      <table>
        <thead>
          <tr>
            {daysOfWeek.map(day => (
              <th key={day}>{day}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          <tr>
            {daysOfWeek.map(day => (
              <td key={day}>
                {sessionsByDay[day].map(session => (
                  <div key={session.id}>
                    <p>{session.startTime} - {session.endTime}</p>
                    <p>Subject ID: {session.subjectId}</p>
                    <p>Teacher ID: {session.teacherId}</p>
                  </div>
                ))}
              </td>
            ))}
          </tr>
        </tbody>
      </table>
    );
  };

  render() {
    return (
      <button onClick={this.fetchProgramData}>
        Load Student Program
      </button>
    );
  }
}

export default StudentProgram;
