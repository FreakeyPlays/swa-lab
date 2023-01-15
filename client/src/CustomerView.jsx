import React from "react";
import AddCustomer from "./AddCustomer";
import { Button, ButtonToolbar } from "react-bootstrap";

import { Button, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import View from "./View";


class Customer extends React.Component{

    constructor(props){
        super(props); 
            this.state = {
                list: [], 
                showAddCustomer : false 
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

    handleAdd = () => {
        this.setState({showAddCustomer:true});
        console.log("add clicked");
    }
    

    handleDelete = (id) => {
        fetch("http://localhost:8080/company/remove/"+id, {method: 'DELETE'})
            .then( response => { 
                response.json()
                console.log(response) 
            })
            .then (() => this.setState({status: 'Delete successful'}));
    }

    render() {
        let disableAddCustomer = () => this.setState({showAddCustomer:false}); 

        return(    
            <div className="view">
            <h1>Customers</h1>
            <Button id="addbtn_customer" className="addbtn" variant="contained" size="medium">Add</Button>
            <AddCustomer show={this.state.showAddCustomer} onHide = {disableAddCustomer}/>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Name</TableCell>
                        <TableCell>Street</TableCell>
                        <TableCell>Number</TableCell>
                        <TableCell>City</TableCell>
                        <TableCell></TableCell> 
                        <TableCell></TableCell> 
                        <TableCell></TableCell>     
                        <TableCell></TableCell>    
                    </TableRow>
                </TableHead>
                    <TableBody>
                    {this.state.list.map(company => ( 
                        <TableRow key={company.id}>
                            <TableCell>{company.companyName}</TableCell>
                            <TableCell>{company.address.streetName}</TableCell>
                            <TableCell>{company.address.houseNumber}</TableCell>
                            <TableCell>{company.address.city}</TableCell>
                            <TableCell><Button variant="contained" size="small" onClick={this.handleEdit}>Edit</Button></TableCell>
                            <TableCell><Button variant="contained" size="small" onClick={() => {this.handleDelete(company.id)}}>Delete</Button></TableCell>
                            <TableCell><Button variant="contained" size="small" onClick={this.handleShowContracts}>Contracts</Button></TableCell>
                            <TableCell><Button variant="contained" size="small" onClick={this.handleShowUsers}>Users</Button></TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
            </Table>
            </div>
        ); 
    }
}


export default Customer; 