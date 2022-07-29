import React, { Component } from 'react';
import { Button, ButtonGroup, Table , Container} from 'reactstrap';
import { Link } from 'react-router-dom';

class Booking extends Component {
    constructor(props) {
        super(props);
        this.state = {
            bookings : [],
            roomNameFilter : '',
            studentFilter : '',
            dateFilter : '',
            isLoaded: false
        };

        this.onChange = {
            roomNameFilter: this.handleChange.bind(this, 'roomNameFilter'),
            studentFilter: this.handleChange.bind(this, 'studentFilter'),
            dateFilter : this.handleChange.bind(this, 'dateFilter')
        }
    };

    async componentDidMount() {
        fetch('/admin/bookings')
            .then(response => response.json())
            .then(data => this.setState({bookings: data, isLoaded: true}));
    }

    

    handleChange(name, event) {
        this.setState({ [name]: event.target.value });
    };
    
    render() {
        const {bookings, isLoaded} = this.state;

        if (!isLoaded) {
            return <p>Loading...</p>;
        }

        // const filteredList = bookings.filtered(booking => {
        //     return booking.room.roomName === this.state.roomNameFilter
        // });
        const BookingList = bookings.filter(booking => 
            booking.room.roomName.toLowerCase().includes(this.state.roomNameFilter)
        && (booking.student.user.firstName||booking.student.user.lastName||booking.student.id).toLowerCase().includes(this.state.studentFilter)
        &&  booking.date.includes(this.state.dateFilter))
        .map(searchedBookings => {
            return (
                <tr>
                    <td>{searchedBookings.id}</td>
                    <td>{searchedBookings.student.user.firstName} {searchedBookings.student.user.lastName} / {searchedBookings.student.id}</td>
                    <td>{searchedBookings.room.roomName}</td>
                    <td>{searchedBookings.date} / {searchedBookings.time}</td>
                    <td>{searchedBookings.duration} hour</td>
                </tr>
            );
        });

        return(
            <Container className='mt-5'>
                <div className="float-end">
                <label for="room">Room name:&nbsp;&nbsp;</label>
                <input type="text" onChange={this.onChange.roomNameFilter} id="room"/>
                <label for="studentname">Student name:&nbsp;&nbsp;</label>
                <input type="text" onChange={this.onChange.studentFilter} id="studentname"/>
                <label for="date">Date:&nbsp;&nbsp;</label>
                <input type="date" onChange={this.onChange.dateFilter} id="date" min="2022-01-01" max="2023-12-31"></input>
                </div>
                <div>
                    <h2>Booking List</h2>
                </div>
                <table className='table table-hover text-center mt-3'>
                    <thead className='table-light'>
                        <tr>
                            <th>Booking ID</th>
                            <th>Student Name / ID</th>
                            <th>Room Name</th>
                            <th>Booking Date & Time</th>
                            <th>Duration</th>
                        </tr>
                    </thead>
                <tbody>
                {BookingList}
                </tbody>
            </table>
            </Container>
        );
    }
}
export default Booking;