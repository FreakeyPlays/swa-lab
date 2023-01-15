import React from "react";

import { Button, Table, TableBody, TableCell, TableHead, TableRow, Modal, Box, Typography } from "@mui/material";

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

class Contracts extends React.Component{

    constructor(props){
        super(props); 
            this.state = {
                contracts: [], 
                showAddCustomer : false,
                modalOpen: false
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
    handleModalClose = () => {
        this.setState({modalOpen:false});
    }
    handleModalOpen = () =>{
        this.setState({modalOpen:true});
    }
    handleModalSubmit = (e) =>{
        e.preventDefault()
        let payload={
            "licenseKey": e.target.licenseKey.value,
            "startDate": e.target.startDate.value,
            "endDate": e.target.endDate.value,
            "version": e.target.version.value,
            "ips": [
                {
                    "address":e.target..value
                },
                {
                    "address":e.target..value
                }
            ],
            "users": [
                {
                    "id": e.target.version.value
                }
            ],
            "features": [
                {
                    "number": e.target.version.value
                },
                {
                    "number": e.target.version.value
                }
            ]
        }
        fetch(process.env.REACT_APP_API_BASE + "/contract/create", {method: 'POST', headers:{'Content-Type':"application/json"},body: JSON.stringify(payload)})
            .then( response => { 
                console.log(response) 
            })
            .then (() => this.handleModalClose())
            .then (() => this.componentDidMount()) 
    }

    render() {

        return(
            
            <div className="view">
            <h1>Contracts</h1>
            <Button id="addbtn_contracts" variant="contained" size="medium" onClick={this.handleModalOpen}>+ Add Contract</Button>
            <Modal
                open={this.state.modalOpen} onClose={this.handleModalClose}
                aria-labelledby="modal-modal-title" aria-describedby="modal-modal-description">
                
            <Box sx={style}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
                Add contract details
            </Typography>
                <div>
                    <form onSubmit={this.handleModalSubmit}>
                        <div >         
                            <input type="text" className="input_add" placeholder="Start date" name="startDate" />
                            <input type="text" className="input_add" placeholder="End date" name="endDate" />
                        </div> 
                        <div className="address">  
                        <input type="text" id="str" className="input_add" placeholder="Key" name="licenseKey" />
                        <input type="text" id="nr" className="input_add" placeholder="version" name="version" />  
                        </div> 
                        <div className="address">
                            <input type="text" id="nr" className="input_add" placeholder="CompanyID" name="companyId" />
                            <input type="text" id="str" className="input_add" placeholder="ISP" name="address" />
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
                            <TableCell><Button variant="contained" size="small" onClick={() => {this.handleDelete(contract.id)}}>Delete</Button></TableCell>
                            <TableCell><Button variant="contained" size="small" onClick={this.handleShowContracts}>Details</Button></TableCell>
                        </TableRow>
                    ))}
                    </TableBody>
            </Table>
            </div>
        ); 
    }
}


export default Contracts; 