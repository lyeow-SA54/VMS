import React, { Component } from 'react';
import { Button, ButtonGroup,  Container} from 'reactstrap';

class Student extends Component {
    constructor(props) {
        super(props);
        this.state = {
            students : [],
            // roomNameFilter : '',
            studentFilter : '',
            // dateFilter : '',
            isLoaded: false
        };

        this.onChange = {
            // roomNameFilter: this.handleChange.bind(this, 'roomNameFilter'),
            studentFilter: this.handleChange.bind(this, 'studentFilter'),
            // dateFilter : this.handleChange.bind(this, 'dateFilter')
        }
    };

    async componentDidMount() {
        fetch('/admin/students')
            .then(response => response.json())
            .then(data => this.setState({students: data, isLoaded: true}));
    }
    
    // async cancelStudent(id, status) {
    //     if (status!=="REJECTED"&&status!=="CANCELLED")
    //     {
    //         if(window.confirm("Please confirm cancellation."))
    //         {await fetch(`/admin/students/${id}`, {
    //             method: 'POST',
    //             headers: {
    //                 'Accept': 'application/json',
    //                 'Content-Type': 'application/json'
    //             }
    //         })
    //         .then(
    //             window.location.reload(false)
    //         )};
    //     }

    //     else
    //     {
    //         window.alert("Student status invalid for cancellation");
    //     }
    // };
    

    handleChange(name, event) {
        this.setState({ [name]: event.target.value });
    };
    
    render() {
        const {students, isLoaded} = this.state;

        if (!isLoaded) {
            return <p>Loading...</p>;
        }

        // const filteredList = students.filtered(student => {
        //     return student.room.roomName === this.state.roomNameFilter
        // });
        const StudentList = students.filter(student => 
        //     student.room.roomName.toLowerCase().includes(this.state.roomNameFilter.toLowerCase())
        // && (
            student.user.firstName.toLowerCase().includes(this.state.studentFilter.toLowerCase())
         || student.user.lastName.toLowerCase().includes(this.state.studentFilter.toLowerCase())
         || student.id.includes(this.state.studentFilter.toLowerCase())
        )
        // && student.date.includes(this.state.dateFilter))
        .map(searchedStudents => {
            return (
                <tr>
                    <td>{searchedStudents.user.firstName} {searchedStudents.user.lastName} / {searchedStudents.id}</td>
                    <td>{searchedStudents.user.username}</td>
                    <td>{searchedStudents.user.email}</td>
                    <td>{searchedStudents.score}</td>
                    {/* <td>{searchedStudents.date} / {searchedStudents.time}</td>
                    <td>{searchedStudents.duration} minutes</td> */}
                    {/* <td>
                    <ButtonGroup>
                        <Button size="sm" color='danger' onClick={() => this.cancelStudent(searchedStudents.id, searchedStudents.status)} style={ { display: (searchedStudents.status!=="REJECTED")&&(searchedStudents.status!=="CANCELLED") ? 'block' : 'none' } }>Cancel Student<span className="fa fa-times"></span></Button>
                    </ButtonGroup></td> */}
                </tr>
            );
        });

        return(
            <Container className='mt-5'>
                <div className="float-end">
                {/* <label for="room">Room name:&nbsp;&nbsp;</label>
                <input type="text" onChange={this.onChange.roomNameFilter} id="room"/> */}
                <label for="studentname">Name search:&nbsp;&nbsp;</label>
                <input type="text" onChange={this.onChange.studentFilter} id="studentname"/>
                {/* <label for="date">Date:&nbsp;&nbsp;</label>
                <input type="date" onChange={this.onChange.dateFilter} id="date" min="2022-01-01" max="2023-12-31"></input> */}
                </div>
                <div>
                    <h2>Student List</h2>
                </div>
                <table className='table table-hover text-center mt-3'>
                    <thead className='table-light'>
                        <tr>
                        <th>Student Name / ID</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Score</th>
                            {/* <th>Room Name</th> */}
                            {/* <th>Student Date & Time</th> */}
                            {/* <th>Duration</th> */}
                        </tr>
                    </thead>
                <tbody>
                {StudentList}
                </tbody>
            </table>
            </Container>
        );
    }
}
export default Student;