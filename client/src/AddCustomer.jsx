import React from "react";
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { Form } from "react-bootstrap";


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
    handleSave(e){
        e.preventDefault(); 
        fetch("http://localhost:8080/company/create", {
            method : "POST", 
            headers : {
                'Accept':'application/json', 
                'Content-Type':'application/json'
            }, 
            body:JSON.stringify({
                companyName : e.target.companyName.value,
                department : e.target.department.value
            })
        })
        .then( response => response.json())
        
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
                    <Form onSubmit={this.handleSave}>
                        <Form.Group controlId="companyName">
                            <Form.Control type="text" name="companyName" required placeholder="Companyname"/>
                        </Form.Group>
                        <Form.Group controlId="department">
                            <Form.Control type="text" name="department" required placeholder="Department"/>
                        </Form.Group>
                        <Form.Group controlId="addressStr">
                            <Form.Control type="text" name="addressStr"  placeholder="Street"/>
                        </Form.Group>
                        <Form.Group controlId="addressCity">
                            <Form.Control type="text" name="addressCity"  placeholder="City"/>
                        </Form.Group>
                        <Form.Group>
                            <Button type="submit">Save</Button>
                        </Form.Group>
                    </Form>
                </div>
            </Box>
                </Modal>
            </div>
            
        ); 
    }
}

export default AddCustomer; 