import fetch from 'unfetch';
import * as serviceConnection from './serviceConnection';

const checkStatus = response => {
    if (response.ok) {
        return response;
    } else {
        let error = new Error(response.statusText);
        error.response = response;
        response.json().then(e => {
            error.error = e;
        });
        return Promise.reject(error);
    }
}

export const getAllStudents = () => 
    fetch(serviceConnection.getBaseUrl() + '/api/students').then(checkStatus);

export const addNewStudent = student =>
    fetch(serviceConnection.getBaseUrl() + '/api/students', {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(student)
    })
    .then(checkStatus);

export const updateStudent = (studentId, student) => 
    fetch(serviceConnection.getBaseUrl() + `/api/students/${studentId}`, {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'PUT',
        body: JSON.stringify(student)
    })
    .then(checkStatus);

export const deleteStudent = studentId =>
    fetch(serviceConnection.getBaseUrl() + `/api/students/${studentId}`, {
        method: 'DELETE'
    })
    .then(checkStatus);

