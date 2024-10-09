import React, { useState } from 'react';
import { Dialog, DialogActions, DialogContent, DialogTitle, Button, TextField } from '@mui/material';
import Discussion from '../../interfaces/Discussion';
import discussionValidationSchema from '../../validation/discussionValidation';
import { ErrorMessage, Field, Form, Formik } from 'formik';

interface DiscussionFormDialogBoxProps {
	open: boolean;
	onClose: () => void;
	onSubmit: (discussion: Discussion) => void;
	title: string; // Add title prop
	discussion?: Discussion;
}

const DiscussionFormDialogBox: React.FC<DiscussionFormDialogBoxProps> = ({
	open,
	onClose,
	onSubmit,
	title,
	discussion,
}) => {
	return (
		<Dialog open={open} onClose={onClose}>
			<DialogTitle sx={{ color: '#008080' }}>{title}</DialogTitle> {/* Use the title prop */}
			<DialogContent>
				<Formik
					initialValues={{ title: discussion?.title || '' }}
					validationSchema={discussionValidationSchema}
					onSubmit={(values, { setSubmitting }) => {
						const newDiscussion: Discussion = { title: values.title };
						onSubmit(newDiscussion);
						onClose();
						setSubmitting(false);
					}}
				>
					{({ isSubmitting, errors, touched }) => (
						<Form>
							<Field
								as={TextField}
								margin='dense'
								label='Title'
								type='text'
								fullWidth
								name='title'
								error={touched.title && !!errors.title}
								helperText={<ErrorMessage name='title' component='div' />}
							/>
							<DialogActions>
								<Button onClick={onClose} color='primary'>
									Cancel
								</Button>
								<Button type='submit' color='primary' variant='contained' disabled={isSubmitting}>
									{discussion ? 'Edit' : 'Create'}
								</Button>
							</DialogActions>
						</Form>
					)}
				</Formik>
			</DialogContent>
		</Dialog>
	);
};

export default DiscussionFormDialogBox;
