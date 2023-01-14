import React from "react";
import { BrowserRouter, Route, Link, Routes } from "react-router-dom"; 
import Customer from "./CustomerView";
import Contracts from "./ContractsView"
import Users from "./UserView";
import { Button } from "@mui/material";

import "./Views.css";



class Test extends React.Component{


    render(){
        return(
            <BrowserRouter>
                <div >
                    <div className="button">
                    <Button variant="contained" size="large"><Link to="/customer">Customers</Link></Button>
                    <Button variant="contained" size="large"><Link to="/contracts">Conracts</Link></Button>
                    <Button variant="contained" size="large"><Link to="/users">Users</Link></Button>
                    </div>
                <Routes>
                    <Route>
                        <Route exact path="/customer" element={<Customer/>} />
                        <Route exact path="/contracts" element={<Contracts/>}  />
                        <Route exact path="/users" element={<Users/>} />
                    </Route>
                </Routes>
                </div>
            </BrowserRouter>


        ); 
    }
}

export default Test; 