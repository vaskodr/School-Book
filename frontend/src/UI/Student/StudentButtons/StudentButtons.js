import React, { Component } from 'react';
import StudentProgram from '../StudentProgram/StudentProgram';
import StudentGrades from '../StudentGrades/StudentGrades';
import StudentAbsences from '../StudentAbsences/StudentAbsences';

class StudentButtons extends Component {
  constructor(props) {
    super(props);
    this.state = {
      content: null,
    };
  }

  clearContent = () => {
    this.setState({ content: null });
  };

  setContent = (content) => {
    this.setState({ content });
  };

  render() {
    const { content } = this.state;
    const { schoolId, classId, studentId, authData } = this.props;

    return (
      <div>
        <StudentProgram
          schoolId={schoolId}
          classId={classId}
          authData={authData}
          clearContent={this.clearContent}
          setContent={this.setContent}
        />
        <StudentGrades
          studentId={studentId}
          authData={authData}
          clearContent={this.clearContent}
          setContent={this.setContent}
        />
        <StudentAbsences
          studentId={studentId}
          authData={authData}
          clearContent={this.clearContent}
          setContent={this.setContent}
        />
        <div id="clearable-container">
          {content}
        </div>
      </div>
    );
  }
}

export default StudentButtons;
