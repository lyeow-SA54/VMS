import React, { useState } from 'react';
import './App.css';
import { BrowserRouter } from 'react-router-dom';

import Main from './Components/MainComponent'
// import Login from './Components/LoginComponent'
// import useToken from './Components/useToken';

function App() {

fetch('/react/auth')
    .then(data => data.json())
    .then(data => {
      if (data.response === 'NULL') {
        window.location.replace("http://localhost:8080/login");
      } 
    });

    return (
      <BrowserRouter>
        <div>
          <Main />
        </div>
      </BrowserRouter>
    )
}

export default App;