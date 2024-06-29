import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import {BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import LoginForm from './Auth/LoginForm';
import StudentDashboard from './Dashboard/StudentDashboard';
import AdminDashboard from './Dashboard/AdminDashboard';
import { AuthProvider } from './Auth/AuthContext';
import ClassDetails from "./Class/ClassDetails";
import SchoolClasses from "./School/SchoolClasses";
import TeacherDashboard from "./Dashboard/TeacherDashboard";
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
        <AuthProvider>
             <Router>
                 <Routes>
                     <Route path="/" element={<App/>}/>
                     <Route path="/login" element={<LoginForm />} />
                     <Route path="/student/dashboard/:schoolId" element={<StudentDashboard />} />
                     <Route path="/admin/dashboard" element={<AdminDashboard />} />
                     <Route path="/teacher/dashboard/:schoolId" element={<TeacherDashboard />} />
                     <Route path="/admin/dashboard/school/:schoolId/classes" element={<SchoolClasses />} />
                     <Route path="/admin/dashboard/school/:schoolId/class/:classId" element={<ClassDetails />} />
                     <Route path="*" element={<Navigate to="/" />} />
                 </Routes>
             </Router>
         </AuthProvider>
);