import React, { Component } from 'react';
import { Switch, Route, Redirect } from 'react-router-dom';


import Booking from './BookingComponent';
import Report from './ReportComponent';
import Student from './StudentComponent';
class Main extends Component {

  constructor(props) {
    super(props);

    this.state = {}

  }

  render() { //render() is used to define the view 

    return ( //any function that does not match home or menu will be redirected to dashboard
      <div>
        <Switch>
          <Route path="/admin/reports" exact={true} component={Report} />
          <Route path="/admin/bookings" exact={true} component={Booking} />
          <Route path="/admin/students" exact={true} component={Student} />
          <Redirect to="http:localhost:8080/admin/index" />
        </Switch>
      </div>

    )
  }
}
export default Main;