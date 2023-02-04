import React from "react";
import FormControlLabel from "@mui/material/FormControlLabel";

import { Checkbox, Button, Table, TableBody, TableCell, TableHead, TableRow, Typography, Modal, Box} from "@mui/material";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
  };


class Users extends React.Component{

    constructor(props){
        super(props); 
            this.state = {
                users: [], 
                showAddCustomer : false,
                modalOpen: false
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
    handleModalClose = () => {
        this.setState({modalOpen:false});
    }
    handleModalOpen = () =>{
        this.setState({modalOpen:true});
    }
    handleModalSubmit = (e) =>{
        e.preventDefault()
        let payload={
            "firstName": e.target.firstName.value,
            "lastName": e.target.lastName.value,
            "mail": e.target.mail.value,
            "password": e.target.password.value,
            "username": e.target.username.value,
            //"isAdmin": e.target.password.value,
            "companyId": {
                "id": e.target.companyID.value,
            },
            "phoneNumbers": {
                "number": e.target.number.value,
            }
        }
        fetch(process.env.REACT_APP_API_BASE + "/user/create", 
        {method: 'POST', 
        headers:{'Content-Type':"application/json"},
        body: JSON.stringify(payload)})
            .then( response => { 
                console.log(response) 
            })
            .then (() => this.handleModalClose())
            .then (() => this.componentDidMount()) 
    }

    render() {

        return(
            
            <div className="view">
            <h1>Users</h1>
            <Button variant="contained" onClick={this.handleModalOpen}> + Add User</Button>
            <Modal
                open={this.state.modalOpen} onClose={this.handleModalClose}
                aria-labelledby="modal-modal-title" aria-describedby="modal-modal-description">
                
            <Box sx={style}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
                Add user details
            </Typography>
                <div>
                    <form onSubmit={this.handleModalSubmit}>
                        <div >         
                            <input type="text" className="input_add" placeholder="First name" name="firstName" />
                            <input type="text" className="input_add" placeholder="Last name" name="lastName" />
                        </div> 
                        <div>
                            <input type="text" className="input_add" placeholder="example@mail.com" name="mail" />
                            <input type="text" className="input_add" placeholder="Password" name="password" />
                        </div>
                        <div className="address">  
                            <input type="text" id="str" className="input_add" placeholder="+49 123 4567890" name="number" />
                            <input type="text" id="nr" className="input_add" placeholder="@username" name="username" />  
                        </div> 
                        <div className="address">  
                            <FormControlLabel control={<Checkbox />} label="Administrator" />  
                            <input type="text" id="nr" className="input_add" placeholder="CompanyID" name="companyID" />
                                                  
                        </div>                 
                        <div className="Button_Submit">
                            <Button variant="contained" type="submit" value="submit">Submit</Button>
                        </div>
                    </form>            
                </div>
            </Box>
                </Modal>
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