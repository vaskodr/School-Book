import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import {BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import StudentDashboard from './Dashboard/StudentDashboard';
import AdminDashboard from './Dashboard/AdminDashboard';
import {AuthProvider} from './Auth/AuthContext';
import ClassDetails from "./Class/ClassDetails";
import SchoolClasses from "./School/SchoolClasses";
import TeacherDashboard from "./Dashboard/TeacherDashboard";
import App from './App';
import PrivateRoute from './Auth/PrivateRoute';
import Layout from './UI/Layout'
import CreateSchool from "./School/CreateSchool";
import CreateClass from "./Class/CreateClass";
import UpdateClass from "./Class/UpdateClass";
import SchoolTeachers from "./School/SchoolTeachers";
import UpdateSchool from "./School/UpdateSchool";
import StudentList from "./Student/StudentList";
import RegisterStudent from "./Student/RegisterStudent";
import RegisterTeacher from "./Teacher/RegisterTeacher";
import UpdateTeacher from "./Teacher/UpdateTeacher";
import StudentDetails from "./Student/StudentDetails";
import TeacherDetails from "./Teacher/TeacherDetails";
import ParentDashboard from './Dashboard/ParentDashboard';
import CreateClassProgram from "./Program/CreateClassProgram";
import UpdateClassProgram from "./Program/UpdateClassProgram";
import ClassProgram from "./Program/ClassProgram";
import AddDirector from "./Director/AddDirector";
import UpdateStudent from "./Student/UpdateStudent";
import SubjectManagement from "./Subject/SubjectManagement";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <AuthProvider>
        <Router>
            <Layout>
                <Routes>
                    <Route path="/" element={<App/>}/>
                    <Route
                        path="/student/dashboard/:schoolId"
                        element={
                            <PrivateRoute roles={['ROLE_STUDENT']}>
                                <StudentDashboard/>
                            </PrivateRoute>
                        }
                    />
                    <Route
                        path="/admin/dashboard/subjects"
                        element={
                            <PrivateRoute roles={['ROLE_ADMIN']}>
                                <SubjectManagement/>
                            </PrivateRoute>
                        }
                    />
                    <Route
                        path="/admin/dashboard"
                        element={
                            <PrivateRoute roles={['ROLE_ADMIN']}>
                                <AdminDashboard/>
                            </PrivateRoute>
                        }>
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <ClassDetails/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/classes/:classId"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <ClassDetails/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/classes/create"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <CreateClass/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId/students"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <StudentList/>
                                </ PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId/student/add"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <RegisterStudent/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId/student/:studentId/details"
                            element={
                                <PrivateRoute>
                                    <StudentDetails/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId/student/:studentId/update"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <UpdateStudent/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId/update"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <UpdateClass/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId/programs"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <ClassProgram/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/director/add"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <AddDirector/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId/program/create"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <CreateClassProgram/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId/program/:programId/update"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <UpdateClassProgram/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/teachers"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <SchoolTeachers/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/teacher/add"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <RegisterTeacher/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/teacher/:teacherId/update"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <UpdateTeacher/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/teacher/:teacherId/details"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <TeacherDetails/>
                                </PrivateRoute>
                            }
                        />

                        <Route
                            path="/admin/dashboard/school/:schoolId/classes"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <SchoolClasses/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/create"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <CreateSchool/>
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/update"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <UpdateSchool/>
                                </PrivateRoute>
                            }
                        />
                    </Route>
                    <Route
                        path="/teacher/dashboard/:schoolId"
                        element={
                            <PrivateRoute roles={['ROLE_TEACHER']}>
                                <TeacherDashboard/>
                            </PrivateRoute>
                        }
                    />
                    <Route
                        path="/parent/dashboard"
                        element={
                            <PrivateRoute roles={['ROLE_PARENT']}>
                                <ParentDashboard/>
                            </PrivateRoute>
                        }
                    >
                        <Route
                            path="/parent/dashboard/student/:studentId/dashboard"
                            element={
                                <PrivateRoute roles={['ROLE_PARENT']}>
                                    <StudentDashboard/>
                                </PrivateRoute>
                            }
                        />
                    </Route>
                    <Route path="*" element={<Navigate to="/"/>}/>
                </Routes>
            </Layout>
        </Router>
    </AuthProvider>
);