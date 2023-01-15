import React from "react";
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Customer from CustomerView;


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
                >
            <Box sx={style}>
                <div>
                    <form onSubmit={this.handleSubmit}>
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
            </div>
            
        ); 
    }
}

export default AddCustomer; 