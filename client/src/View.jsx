import React from "react";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";

class View extends React.Component{

    constructor(props) {
		super(props);
	    this.state = {	  
            companies: [],
            isLoading: true
		};		
	}

    componentDidMount(){
        fetch("http://localhost:8080/company/all")
        .then( response => response.json())
        .then( companies => this.setState({companies}))
        console.log(this.state.companies); 
    }

    render(){

        
        return(
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Column 1</TableCell>
                        <TableCell>Column 2</TableCell>
                        <TableCell>Column 3</TableCell>
                        <TableCell>Actions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {this.state.companies.map(company => ( 
                        <TableRow key={company.value}>
                            <TableCell>{company.companyName}</TableCell>
                            <TableCell></TableCell>
                            <TableCell></TableCell>
                            <TableCell><button onClick={this.handleSave}>Save</button></TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        );
    }
}

export default View;
