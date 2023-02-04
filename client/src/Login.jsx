import React from "react";
import clsx from 'clsx';
import "./index.css"
import Button from "@mui/material/Button";
import InputLabel from "@mui/material/InputLabel";
import FormControl from "@mui/material/FormControl";
import Input from "@mui/material/Input";
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton"; 



class Login extends React.Component{
    //Constructor
    constructor(props){
        super(props); 
        this.state = {
            password:"", 
            username:"", 
            showPassword: false, 
            loginButtonDisable: true,
        }; 
    }
    handleChange = (e) => {
        const username = this.state.username; 
        if(e.target.id==="username"){
            this.setState({username: e.target.value}); 
                if(username !== null && username.lenght < 2){
                    this.setState({loginButtonDisable:true});
                } else {
                    this.setState({loginButtonDisable:false});
                }
        }
        if(e.target.id==="password"){
            this.setState({password:e.target.value});
        }
    }; 

   status (response){
        if(response.status >= 200 && response.status < 300 ) {
            return Promise.resolve(response); 
        } else {
            return Promise.reject(new Error(response.statusText))
        }
    }

    processData(response) {
        console.log(response); 
        if(response !== false){
            this.props.authorized(); 
        }
        else{
            document.getElementById('error').style.display = "flex";
            }
    }
    handleLoginSubmit = (e) =>{
        fetch("http://localhost:8080/user/login?username="+this.state.username+"&password="+this.state.password , {
            method: 'POST',
        })
        .then(this.status)
        .then(function(response) { return response.json()})
        .then((response)=>this.processData(response))
        .catch(function(error) { console.log('Request failed', error);
        });
    
    e.preventDefault(); 
    }


    render() {   
        return(
            <div className="center">
                <p id="error" className="error"> Login failed!</p>
                <h1>Log in </h1>
                <div className="loginbox">
                    <form  className="loginform" onSubmit={this.handleLoginSubmit}>
                        <div className="usernamefield">
                        <FormControl className="formContrl">
                            <InputLabel htmlFor="Login"> USERNAME </InputLabel>
                            <Input id="username" type="text" value={this.state.username} onChange={this.handleChange}/>
                        </FormControl>
                        </div>
                        <div className="passwordfield">
                        <FormControl>
                            <InputLabel htmlFor="password"> PASSWORD </InputLabel>
                            <Input id="password" type="password" value={this.state.password} onChange={this.handleChange}/> 
                        </FormControl>
                        </div>
                        <div className="buttonRightAlign">
                            <Button variant="contained" color="primary" type="submit" value="submit" disabled={this.state.loginButtonDisable} >Login</Button>
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}

export default Login; 