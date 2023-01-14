import React from "react";
import Contracts from "./ContractsView"; 
import Customer from "./CustomerView"; 
import Users from "./UserView"; 

import "./Views.css";
import Button from "@mui/material/Button";
import { Stack } from "@mui/material";



class View extends React.Component{

    constructor(props) {
		super(props);
	    this.state = {	  
            list: [],
            customerView: false, 
            contractsView: false, 
            userView: false, 
		};		
	}

    componentDidMount(){
        fetch("http://localhost:8080/company/all")
        .then( response => response.json())
        .then( list => {
          console.log(list)
          this.setState({list})
        })            
    }

    showCustomerTable = () => {
      this.setState({customerView:true}); 
    }; 

    showContractsTable = () => {
      this.setState({contractsView:true}); 
    }; 

    showUserTable = () => {
      this.setState({userView:true}); 
    }; 

    render(){

      if(this.state.customerView){
        return(
          <Customer> </Customer>
        ); 
      } else {
          if(this.state.contractsView){
            return(
              <Contracts></Contracts>
            );
          } else {
              if(this.state.userView){
                return(
                  <Users></Users>
                );
              }
          }
        }
        return(
          <div className="button">
          <Stack spacing={5}>
            <Button variant="contained" size="large" onClick={this.showCustomerTable}>Customers</Button>
            <Button variant="contained" size="large" onClick={this.showContractsTable}>Contracts</Button>
            <Button variant="contained" size="large" onClick={this.showUserTable}>Users</Button>
          </Stack>
          </div>
        ); 
      }        
    }


export default View;
