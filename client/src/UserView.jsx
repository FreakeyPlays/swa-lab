import React from "react";

import { Button, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";


class Users extends React.Component{

    constructor(props){
        super(props); 
            this.state = {
                users: []
            }; 
    }
    componentDidMount(){
        fetch("http://localhost:8080/user/all")
        .then( response => response.json())
        .then( users => {
          console.log(users)
          this.setState({users})
        })
    }

    render() {

        return(
            
            <div className="view">
            <h1>Users</h1>
            <Button variant="contained" size="medium">Add</Button>
            <Button className="contractsbtn" variant="contained" size="large" >Contracts</Button>
            <Button className="costumerbtn" variant="contained" size="large" >Costumer</Button>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Name</TableCell>
                        <TableCell>Surname</TableCell>
                        <TableCell>Email</TableCell>
                        <TableCell></TableCell>
                        <TableCell></TableCell> 
                           
                    </TableRow>
                </TableHead>
                    <TableBody>
                    {this.state.users.map(user => ( 
                        <TableRow key={user.username}>
                            <TableCell>{user.firstName}</TableCell>
                            <TableCell>{user.lastName}</TableCell>
                            <TableCell>{user.email}</TableCell>
                            <TableCell><Button variant="contained" size="small" onClick={this.handleEdit}>Edit</Button></TableCell>
                            <TableCell><Button variant="contained" size="small" onClick={this.handleDelete}>Delete</Button></TableCell>
                            
                        </TableRow>
                    ))}
                    </TableBody>
            </Table>
            </div>
        ); 
    }
}


export default Users; 