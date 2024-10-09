import React, { useState } from 'react';
import { Button, Container, TextField } from '@mui/material';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Comment from '../../interfaces/Comment';
import commentValidationSchema from '../../validation/commentValidation';
import './CommentBox.css';

interface CommentBoxProps {
	comment?: Comment;
	onSubmit: (comment: Comment) => void;
	visible: boolean;
	onCancel: () => void;
}

const CommentBox: React.FC<CommentBoxProps> = ({ comment, onSubmit, visible, onCancel }) => {
	const [isVisible, setIsVisible] = useState(visible);

	const handleCancel = (resetForm: () => void) => {
		resetForm();
		setIsVisible(false);
		onCancel();
	};

	return (
		<>
			{isVisible && (
				<Container className='CommentBox'>
					<Formik
						initialValues={{ content: comment?.content || '' }}
						validationSchema={commentValidationSchema}
						validateOnBlur={true}
						validateOnChange={true}
						onSubmit={(values, { resetForm }) => {
							const newComment: Comment = {
								content: values.content,
							};
							onSubmit(newComment);
							resetForm();
							setIsVisible(false);
						}}
					>
						{({ isSubmitting, resetForm, touched, errors }) => (
							<Form>
								<Field
									as={TextField}
									label={comment ? 'Edit a comment...' : 'Write a comment...'}
									variant='outlined'
									fullWidth
									multiline
									sx={{ colors: 'primary' }}
									name='content'
									error={touched.content && !!errors.content}
									helperText={<ErrorMessage name='content' component='div' />}
								/>
								<Button variant='contained' color='primary' type='submit' disabled={isSubmitting}>
									{comment ? 'Edit a comment' : 'Add a comment'}
								</Button>
								<Button variant='contained' color='secondary' onClick={() => handleCancel(resetForm)}>
									Cancel
								</Button>
							</Form>
						)}
					</Formik>
				</Container>
			)}
		</>
	);
};

export default CommentBox;
