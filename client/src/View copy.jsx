import React from "react";

import "./View.css";
import { styled } from '@mui/system';
import TabsListUnstyled from '@mui/base/TabsListUnstyled';
import TabPanelUnstyled from '@mui/base/TabPanelUnstyled';
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import TabsUnstyled from '@mui/base/TabsUnstyled';
import { buttonUnstyledClasses } from '@mui/base/ButtonUnstyled';
import TabUnstyled, { tabUnstyledClasses } from '@mui/base/TabUnstyled';


class Viewcopy extends React.Component{

    constructor(props) {
		super(props);
	    this.state = {	  
            list: []            
		};
    
	}

  componentDidMount(){
        fetch("http://localhost:8080/company/all")
        .then( (response) => response.json())
        .then( (data) => {
          console.log(data)
          this.setState({list:data.data})    
          })
    }

    render(){
            
      return(
        <div>
          <table>
            <tbody>
              {this.state.list.map(company =>(
                <TableRow key={company.id}>
                <TableCell>{company.companyName}</TableCell>
                <TableCell>{company.id}</TableCell>
                <TableCell>{company.adress}</TableCell>
                <TableCell><button onClick={this.handleEdit}>Edit</button></TableCell>
                <TableCell><button onClick={this.handleDelete}>Delete</button></TableCell>
                <TableCell><button onClick={this.handleShowContracts}>Contracts</button></TableCell>
                <TableCell><button onClick={this.handleShowUsers}>Users</button></TableCell>
            </TableRow>
              ))}
            </tbody>
          </table>
        </div>
        
            
      )
        
    }
}

export default Viewcopy;
