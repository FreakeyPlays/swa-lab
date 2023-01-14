import React from "react";

import { Button, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";


class Customer extends React.Component{

    constructor(props){
        super(props); 
            this.state = {
                list: []
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

    render() {

        return(
            
            <div className="view">
            <h1>Customers</h1>
            <Button id="addbtn_customer" className="addbtn" variant="contained" size="medium">Add</Button>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Name</TableCell>
                        <TableCell>Street</TableCell>
                        <TableCell></TableCell>
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
                            <TableCell><Button variant="contained" size="small" onClick={this.handleDelete}>Delete</Button></TableCell>
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