import React from "react";
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Form from "@mui/material/FormControl"
import FormControl from "@mui/material/FormControl";
import { FormGroup, FormLabel, Input } from "@mui/material";


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

class AddCustomer extends React.Component{
    constructor(props){
        super(props); 
        this.state = {
            open: false
        }

    }
    handleClose = () => {
        this.setState({open:false});
    }
    handleOpen = () =>{
        this.setState({open:true});
    }

    handleSubmit = (e) =>{
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
            .then (() => this.handleClose()) 

    }
    render(){
        return(
            <div>
            <Button onClick={this.handleOpen}> + Add customer</Button>
                <Modal
                    open={this.state.open}
                    onClose={this.handleClose} 
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                
            <Box sx={style}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
                Add customer details
            </Typography>
                <div>
                    <form onSubmit={this.handleSubmit}>
                        <div >         
                            <input type="text" placeholder="Name" name="customerName" />
                            <input type="text" placeholder="Department" name="department" />
                        </div> 
                        <div className="address">  
                        <input type="text" className="str" placeholder="Street" name="streetName" />
                        <input type="text" className="nr" placeholder="Nr." name="houseNumber" />  
                        </div> 
                        <div className="address">
                            <input type="text" className="nr" placeholder="Zip" name="zipCode" />
                            <input type="text" className="str" placeholder="City" name="city" />
                        </div>
                        <div>
                            <input type="text" placeholder="Area" name="area" />
                            <input type="text" placeholder="Country" name="country" />
                        </div>
                                         
                        <div className="Button_Submit">
                            <Button variant="contained" type="submit" value="submit">Submit</Button>
                        </div>
                    </form>            
                </div>
            </Box>
                </Modal>
            </div>
            
        ); 
    }
}

export default AddCustomer; 