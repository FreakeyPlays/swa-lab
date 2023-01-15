import React from "react";

import { Button, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";


class Customer extends React.Component{

    constructor(props){
        super(props); 
            this.state = {
                contracts: []
            }; 
    }
    componentDidMount(){
        fetch(process.env.REACT_APP_API_BASE + "/contract/all")
        .then( response => response.json())
        .then( contracts => {
          console.log(contracts)
          this.setState({contracts})
        })
    }

    handleDelete = (id) => {
        console.log(id)
        fetch(process.env.REACT_APP_API_BASE + "/contract/remove/"+id, {method: 'DELETE'})
            .then( response => { 
                console.log(response) 
            })
            .then (() => this.setState({status: 'Delete successful'}))
            .then (() => this.componentDidMount())
    }

    render() {

        return(
            
            <div className="view">
            <h1>Contracts</h1>
            <Button id="addbtn_contracts" variant="contained" size="medium">Add</Button>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Start</TableCell>
                        <TableCell>End</TableCell>
                        <TableCell>Version</TableCell>
                        <TableCell></TableCell> 
                        <TableCell></TableCell> 
                        <TableCell></TableCell>     
                    </TableRow>
                </TableHead>
                    <TableBody>
                    {this.state.contracts.map(contract => ( 
                        <TableRow key={contract.id}>
                            <TableCell>{contract.id}</TableCell>
                            <TableCell>{contract.startDate}</TableCell>
                            <TableCell>{contract.endDate}</TableCell>
                            <TableCell>{contract.version}</TableCell>
                            <TableCell><Button variant="contained" size="small" onClick={this.handleEdit}>Edit</Button></TableCell>
                            <TableCell><Button variant="contained" size="small" onClick={() => {this.handleDelete(contract.id)}}>Delete</Button></TableCell>                            <TableCell><Button variant="contained" size="small" onClick={this.handleShowContracts}>Details</Button></TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
            </Table>
            </div>
        ); 
    }
}


export default Customer; 