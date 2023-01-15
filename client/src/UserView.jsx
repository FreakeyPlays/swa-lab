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
        fetch(process.env.REACT_APP_API_BASE + "/user/all")
        .then( response => response.json())
        .then( users => {
          console.log(users)
          this.setState({users})
        })
    }

    handleDelete = (id) => {
        console.log(id)
        fetch(process.env.REACT_APP_API_BASE + "/user/remove/"+id, {method: 'DELETE'})
            .then( response => { 
                console.log(response) 
            })
            .then (() => this.setState({status: 'Delete successful'}))
            .then (() => this.componentDidMount())
    }

    render() {

        return(
            
            <div className="view">
            <h1>Users</h1>
            <Button id="addbtn_user" className="addbtn" variant="contained" size="medium">Add</Button>
            <Table className="table">
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
                            <TableCell><Button variant="contained" size="small" onClick={() => {this.handleDelete(user.id)}}>Delete</Button></TableCell>
                            
                        </TableRow>
                    ))}
                    </TableBody>
            </Table>
            </div>
        ); 
    }
}


export default Users; 