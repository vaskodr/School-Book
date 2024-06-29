import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import StudentDashboard from './Dashboard/StudentDashboard';
import AdminDashboard from './Dashboard/AdminDashboard';
import { AuthProvider } from './Auth/AuthContext';
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

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <AuthProvider>
        <Router>
            <Layout>
                <Routes>
                    <Route path="/" element={<App />} />
                    <Route
                        path="/student/dashboard/:schoolId"
                        element={
                            <PrivateRoute roles={['ROLE_STUDENT']}>
                                <StudentDashboard />
                            </PrivateRoute>
                        }
                    />
                    <Route
                        path="/admin/dashboard"
                        element={
                            <PrivateRoute roles={['ROLE_ADMIN']}>
                                <AdminDashboard />
                            </PrivateRoute>
                        }>
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <ClassDetails />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <ClassDetails />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/create"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <CreateClass />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/class/:classId/update"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <UpdateClass />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/teachers"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <SchoolTeachers />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/classes"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <SchoolClasses />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/create"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <CreateSchool />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/admin/dashboard/school/:schoolId/update"
                            element={
                                <PrivateRoute roles={['ROLE_ADMIN']}>
                                    <UpdateSchool />
                                </PrivateRoute>
                            }
                        />
                    </Route>
                    <Route
                        path="/teacher/dashboard/:schoolId"
                        element={
                            <PrivateRoute roles={['ROLE_TEACHER']}>
                                <TeacherDashboard />
                            </PrivateRoute>
                        }
                    />
                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
            </Layout>
        </Router>
    </AuthProvider>
);