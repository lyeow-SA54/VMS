import React, { Component } from 'react';
import { Button, ButtonGroup, Container } from 'reactstrap';
import { Link } from 'react-router-dom';



class Report extends Component {

    constructor(props) {
        super(props);
        this.state = {
            reports: [],
            // roomNameFilter: '',
            // studentFilter: '',
            dateFilter: '',
            isLoaded: false
        };

        this.onChange = {
            // roomNameFilter: this.handleChange.bind(this, 'roomNameFilter'),
            studentFilter: this.handleChange.bind(this, 'studentFilter'),
            dateFilter: this.handleChange.bind(this, 'dateFilter')
        }
    };

    async componentDidMount() {
        fetch('/admin/reports')
            .then(response => response.json())
            .then(data => this.setState({ reports: data, isLoaded: true }));
    }

    async updateReport(id, status) {
        if (status==="APPROVE")
        {
            if(window.confirm("Please confirm approval."))
            {await fetch(`/admin/reports/approval/${id}`, {
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
            if(window.confirm("Please confirm rejection."))
            {await fetch(`/admin/reports/reject/${id}`, {
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
    };


    handleChange(name, event) {
        this.setState({ [name]: event.target.value });
    };

    render() {
        const { reports, isLoaded } = this.state;

        if (!isLoaded) {
            return <p>Loading...</p>;
        }

        // const ReportListProcessed = reports.forEach(x => x.imgPath = "/img/"+x.imgPath);
        const ReportList = reports
        .filter(report =>
            // (
            //     report.student.user.firstName.toLowerCase().includes(this.state.studentFilter)
            //     || report.student.user.lastName.toLowerCase().includes(this.state.studentFilter)
            // )
           report.booking.date.includes(this.state.dateFilter))
            .map(searchedReports => {
                return (
                    <tr>
                        <td>{searchedReports.id}</td>
                        <td> <img src ={searchedReports.imgPath}></img></td>
                        <td>{searchedReports.details}</td>
                        <td>{searchedReports.booking.date} / {searchedReports.booking.time}</td>
                        <td>{searchedReports.booking.duration} hour</td>
                        <td>{searchedReports.reportStatus}</td>
                        {/* <td>{searchedReports.room.roomName}</td> */}
                        <td>
                            <ButtonGroup>
                                <Button size="sm" color='primary' onClick={() => this.updateReport(searchedReports.id, "APPROVE")} style={ { display: (searchedReports.reportStatus==="PROCESSING")||(searchedReports.reportStatus==="REJECTED") ? 'block' : 'none' } }>Approve<span className="fa fa-thumbs-up"></span></Button>
                                <Button size="sm" color='danger' onClick={() => this.updateReport(searchedReports.id, "REJECT")} style={ { display: (searchedReports.reportStatus==="PROCESSING")||(searchedReports.reportStatus==="APPROVED") ? 'block' : 'none' } }>Reject<span className="fa fa-times"></span></Button>
                            </ButtonGroup></td>
                    </tr>
                );
            });

        return (
            <Container className='mt-5'>
                <div className="float-end">
                    {/* <label for="studentname">Student search:&nbsp;&nbsp;</label>
                    <input type="text" onChange={this.onChange.studentFilter} id="studentname" /> */}
                    <label for="date">Date:&nbsp;&nbsp;</label>
                    <input type="date" onChange={this.onChange.dateFilter} id="date" min="2022-01-01" max="2023-12-31"></input>
                </div>
                <div>
                    <h2>Report List</h2>
                </div>
                <table className='table table-hover text-center mt-3'>
                    <thead className='table-light'>
                        <tr>
                            <th>Report ID</th>
                            <th>Image</th>
                            <th>Details</th>
                            <th>Booking Date & Time</th>
                            <th>Duration</th>
                            <th>Status</th>
                            <th>Options</th>
                        </tr>
                    </thead>
                    <tbody>
                        {ReportList}
                    </tbody>
                </table>
            </Container>
        );
    }
}
export default Report;