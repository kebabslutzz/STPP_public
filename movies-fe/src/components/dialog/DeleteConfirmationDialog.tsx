import React from 'react';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Button } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import CancelIcon from '@mui/icons-material/Cancel';

interface DeleteConfirmationDialogProps {
	open: boolean;
	onClose: () => void;
	onConfirm: () => void;
	text: string;
}

const DeleteConfirmationDialog: React.FC<DeleteConfirmationDialogProps> = ({ open, onClose, onConfirm, text }) => {
	return (
		<Dialog open={open} onClose={onClose}>
			<DialogTitle sx={{ color: '#008080' }}>Confirm Delete</DialogTitle>
			<DialogContent>
				<DialogContentText>{text}</DialogContentText>
			</DialogContent>
			<DialogActions>
				<Button onClick={onClose} className='Button cancel-button' endIcon={<CancelIcon />}>
					Cancel
				</Button>
				<Button onClick={onConfirm} className='Button add-edit-button' endIcon={<DeleteIcon />}>
					Delete
				</Button>
			</DialogActions>
		</Dialog>
	);
};

export default DeleteConfirmationDialog;
