import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {useEffect} from "react";
import {Link, useNavigate} from "react-router-dom";

export default function AlertDialog(props) {
    const navigate = useNavigate();
    const [open, setOpen] = React.useState(true);
    const[header,setHeader] = React.useState("");

    useEffect(() => {
        setOpen(true);
        if(props.data.type == "registration" && props.data.error == false){
            setHeader("Registration successful")
        }
        if(props.data.error == true){
            setHeader("Error");
        }
        if(props.data.type == "resetPassword"  && props.data.error == false){
            setHeader("Reset Password successful");
        }
    }, [props]);

    const handleClose = () => {
        if(props.data.type == "registration" && props.data.error == false){
            navigate("/login");
            setOpen(false);
        }
        if(props.data.type == "resetPassword" && props.data.error== false){
            navigate("/resetSuccess");
            setOpen(false);
        }
        setOpen(false);
    };

    return (
        <div>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {header}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        {props.data.message}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>
                        Ok
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
