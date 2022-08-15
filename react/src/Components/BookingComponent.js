import React, { Component } from 'react';
import { Button, ButtonGroup,  Container} from 'reactstrap';

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
    
    async cancelBooking(id, status) {
        if (status!=="REJECTED"&&status!=="CANCELLED")
        {
            if(window.confirm("Please confirm cancellation."))
            {await fetch(`/admin/bookings/${id}`, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            })
            .then(
                window.location.reload(false)
            )};
        }

        else
        {
            window.alert("Booking status invalid for cancellation");
        }
    };
    

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
            booking.room.roomName.toLowerCase().includes(this.state.roomNameFilter.toLowerCase())
        && (
            booking.student.user.firstName.toLowerCase().includes(this.state.studentFilter.toLowerCase())
         || booking.student.user.lastName.toLowerCase().includes(this.state.studentFilter.toLowerCase())
         || booking.student.id.includes(this.state.studentFilter.toLowerCase())
        )
        && booking.date.includes(this.state.dateFilter))
        .map(searchedBookings => {
            return (
                <tr>
                    <td>{searchedBookings.id}</td>
                    <td>{searchedBookings.status}</td>
                    <td>{searchedBookings.student.user.firstName} {searchedBookings.student.user.lastName} / {searchedBookings.student.id}</td>
                    <td>{searchedBookings.room.roomName}</td>
                    <td>{searchedBookings.date} / {searchedBookings.time}</td>
                    <td>{searchedBookings.duration} minutes</td>
                    <td>
                    <ButtonGroup>
                        <Button size="sm" color='danger' onClick={() => this.cancelBooking(searchedBookings.id, searchedBookings.status)} style={ { display: (searchedBookings.status!=="REJECTED")&&(searchedBookings.status!=="CANCELLED") ? 'block' : 'none' } }>Cancel Booking<span className="fa fa-times"></span></Button>
                    </ButtonGroup></td>
                </tr>
            );
        });

        return(
            <Container className='mt-5'>
                <div className="float-end">
                <label for="room">Room name:&nbsp;&nbsp;</label>
                <input type="text" onChange={this.onChange.roomNameFilter} id="room"/>
                <label for="studentname">Student search:&nbsp;&nbsp;</label>
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
                            <th>Status</th>
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