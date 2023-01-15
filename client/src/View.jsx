import React from "react";
import Contracts from "./ContractsView"; 
import Customer from "./CustomerView"; 
import Users from "./UserView"; 
import { BrowserRouter, Route, Link, Routes } from "react-router-dom"; 

import Button from "@mui/material/Button";



class View extends React.Component{

    constructor(props) {
		super(props);
	    this.state = {	  
            list: [],
            
            
		};		
	}

    componentDidMount(){
        fetch(process.env.REACT_APP_API_BASE + "/company/all")
        .then( response => response.json())
        .then( list => {
          console.log(list)
          this.setState({list})
        })            
    }

    render(){

        return(
          <BrowserRouter>
                <div >
                    <div className="button">
                    <Button id="customerbtn" variant="contained" size="large" ><Link to="/customer" style={{ textDecoration: 'none' }} className="link" >Customers</Link></Button>
                    <Button id="contractsbtn" variant="contained" size="large"><Link to="/contracts" style={{ textDecoration: 'none' }} className="link">Conracts</Link></Button>
                    <Button id="userbtn" variant="contained" size="large"><Link to="/users" style={{ textDecoration: 'none' }} className="link">Users</Link></Button>
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


export default View;
