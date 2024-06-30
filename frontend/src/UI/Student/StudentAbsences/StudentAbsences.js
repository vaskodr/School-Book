import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

class StudentAbsences extends Component {
  constructor(props) {
    super(props);
    this.state = {
      absences: null,
      showAbsences: false,
    };
  }

  fetchAbsencesData = async () => {
    const { studentId, authData } = this.props;
    console.log('Fetching absences data for studentId:', studentId); // Debugging log

    try {
      const response = await fetch(`http://localhost:8080/api/absences/student/${studentId}`, {
        headers: {
          Authorization: `Bearer ${authData.accessToken}`,
        },
      });
      if (response.ok) {
        const data = await response.json();
        console.log('Fetched absences data:', data); // Debugging log
        this.setState({ absences: data, showAbsences: true });
      } else {
        console.error('Failed to fetch absences data');
      }
    } catch (error) {
      console.error('Error fetching absences data:', error);
    }
  };

  toggleAbsencesVisibility = () => {
    this.setState(prevState => ({ showAbsences: !prevState.showAbsences }));
  };

  renderAbsencesTable = (absences) => {
    if (!absences) return null;

    return (
        <table className="table table-striped table-bordered mt-3">
          <thead className="thead-dark">
          <tr>
            <th>Description</th>
            <th>Date</th>
            <th>Class Session</th>
          </tr>
          </thead>
          <tbody>
          {absences.map(absence => (
              <tr key={absence.id}>
                <td>{absence.description}</td>
                <td>{new Date(absence.date).toLocaleDateString()}</td>
                <td>{absence.classSessionId}</td>
              </tr>
          ))}
          </tbody>
        </table>
    );
  };

  render() {
    const { absences, showAbsences } = this.state;

    return (
        <div className="container mt-5">
          {!showAbsences ? (
              <button className="btn btn-primary mb-3" onClick={this.fetchAbsencesData}>
                Load Student Absences
              </button>
          ) : (
              <>
                {this.renderAbsencesTable(absences)}
                <button className="btn btn-secondary mt-3" onClick={this.toggleAbsencesVisibility}>
                  Hide Absences
                </button>
              </>
          )}
        </div>
    );
  }
}

export default StudentAbsences;