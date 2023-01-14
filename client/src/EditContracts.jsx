import React from "react";
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
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

class EditCustomer extends React.Component{
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
        //fetch
        
    }


    render(){
        return(
            <div>
            <Button onClick={this.handleOpen}> Edit</Button>
                <Modal
                    open={this.state.open}
                    onClose={this.handleClose}
                    
                >
            <Box sx={style}>
                <div>
                    <Form onSubmit={this.handleSave}>
                       
                    </Form>
                </div>
            </Box>
                </Modal>
            </div>
            
        ); 
    }
}

export default EditCustomer; 