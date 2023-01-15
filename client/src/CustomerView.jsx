import React from "react";
//import AddCustomer from "./AddCustomer";
import { ButtonToolbar } from "react-bootstrap";

import { Button, Table, TableBody, TableCell, TableHead, TableRow, Modal, Box } from "@mui/material";

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

class Customer extends React.Component{
    
    constructor(props){
        super(props); 
            this.state = {
                list: [], 
                showAddCustomer : false,
                modalOpen: false
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

    handleAdd = () => {
        this.setState({showAddCustomer:true});
        console.log("add clicked");
    }
    
    handleDelete = (id) => {
        console.log(id)
        fetch(process.env.REACT_APP_API_BASE + "/company/remove/"+id, {method: 'DELETE'})
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
            "companyName": e.target.customerName.value,
            "department": e.target.department.value,
            "address": {
                "country": e.target.country.value,
                "area": e.target.area.value,
                "city": e.target.city.value,
                "zipCode": e.target.zipCode.value,
                "streetName": e.target.streetName.value,
                "houseNumber": e.target.houseNumber.value
            }
        }
        fetch(process.env.REACT_APP_API_BASE + "/company/create", {method: 'POST', headers:{'Content-Type':"application/json"},body: JSON.stringify(payload)})
            .then( response => { 
                console.log(response) 
            })
            .then (() => this.handleModalClose())
            .then (() => this.componentDidMount()) 
    }

    render() {

        return(    
            <div className="view">
            <h1>Customers</h1>
            <Button variant="contained" onClick={this.handleModalOpen}>+ Add Customer</Button>
            <Modal  open={this.state.modalOpen} onClose={this.handleModalClose}
                >
                <Box sx={style}>
                <div>
                    <form onSubmit={this.handleModalSubmit}>
                        <div id="Div_CustomerName">
                            <label>
                                Customer Name:
                                <input type="text" name="customerName" />
                            </label>
                        </div>
                        <div id="Div_Department">
                            <label>
                                Department:
                                <input type="text" name="department" />
                            </label>
                        </div>
                        <div id="Div_Adress">
                            <h3>Adress:</h3>
                            <label>
                                Country:
                                <input type="text" name="country" />
                            </label>
                            <label>
                                Area:
                                <input type="text" name="area" />
                            </label>
                            <label>
                                City:
                                <input type="text" name="city" />
                            </label>
                            <label>
                                Zip Code:
                                <input type="text" name="zipCode" />
                            </label>
                            <label>
                                Street Name:
                                <input type="text" name="streetName" />
                            </label>
                            <label>
                                House Number:
                                <input type="text" name="houseNumber" />
                            </label>
                        </div>
                        <div className="Button_Submit">
                            <Button variant="contained" type="submit" value="submit">Submit</Button>
                        </div>
                    </form>
                </div>
                </Box>
            </Modal>
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