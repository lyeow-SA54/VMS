import React, {Component} from 'react';
import {Switch, Route, Redirect } from 'react-router-dom';


import Booking from './BookingComponent';
class Main extends Component {

  constructor(props) {
    super(props);

    this.state = {}
    
  }

  render() { //render() is used to define the view 
    
    return ( //any function that does not match home or menu will be redirected to dashboard
      <div>
        <Switch>
            <Route path="/admin/bookings" exact={true} component={Booking} />
            <Redirect to="/admin/bookings" />
        </Switch>
      </div>

    )
  }
}
export default Main;