import React from 'react';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Button } from '@mui/material';

interface DeleteConfirmationDialogProps {
	open: boolean;
	onClose: () => void;
	onConfirm: () => void;
}

const DeleteConfirmationDialog: React.FC<DeleteConfirmationDialogProps> = ({ open, onClose, onConfirm }) => {
	return (
		<Dialog open={open} onClose={onClose}>
			<DialogTitle sx={{ color: '#008080' }}>Confirm Delete</DialogTitle>
			<DialogContent>
				<DialogContentText>Are you sure you want to delete this movie?</DialogContentText>
			</DialogContent>
			<DialogActions>
				<Button onClick={onClose} color='primary'>
					Cancel
				</Button>
				<Button onClick={onConfirm} color='primary' variant='contained'>
					Delete
				</Button>
			</DialogActions>
		</Dialog>
	);
};

export default DeleteConfirmationDialog;
