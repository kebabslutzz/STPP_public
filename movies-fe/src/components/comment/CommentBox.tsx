import React, { useEffect, useRef, useState } from 'react';
import { Button, Container, TextField } from '@mui/material';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import Comment from '../../interfaces/Comment';
import commentValidationSchema from '../../validation/commentValidation';
import './CommentBox.css';
import SendIcon from '@mui/icons-material/Send';
import CancelIcon from '@mui/icons-material/Cancel';

interface CommentBoxProps {
	comment?: Comment;
	onSubmit: (comment: Comment) => void;
	visible: boolean;
	onCancel: () => void;
}

const CommentBox: React.FC<CommentBoxProps> = ({ comment, onSubmit, visible, onCancel }) => {
	const formRef = useRef<HTMLDivElement>(null);
	const [isVisible, setIsVisible] = useState(visible);

	useEffect(() => {
		const handleKeyDown = (event: KeyboardEvent) => {
			if (event.key === 'Escape') {
				onCancel();
			}
		};

		const handleClickOutside = (event: MouseEvent) => {
			if (formRef.current && !formRef.current.contains(event.target as Node)) {
				onCancel();
			}
		};

		document.addEventListener('keydown', handleKeyDown);
		document.addEventListener('mousedown', handleClickOutside);

		return () => {
			document.removeEventListener('keydown', handleKeyDown);
			document.removeEventListener('mousedown', handleClickOutside);
		};
	}, [onCancel]);

	useEffect(() => {
		setIsVisible(visible);
	}, [visible]);

	if (!isVisible) return null;

	const handleCancel = (resetForm: () => void) => {
		resetForm();
		setIsVisible(false);
		onCancel();
	};

	return (
		<Container className='PageContainer' maxWidth={false} ref={formRef}>
			{isVisible && (
				<Container>
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
							<Form className='CommentBox'>
								<Field
									className='TextField'
									as={TextField}
									label={comment ? 'Edit a comment...' : 'Write a comment...'}
									variant='outlined'
									fullWidth
									multiline
									sizes='large'
									sx={{ colors: 'primary' }}
									name='content'
									error={touched.content && !!errors.content}
									helperText={<ErrorMessage name='content' component='div' />}
								/>
								<Container className='ButtonContainer'>
									<Button
										className='Button cancel-button'
										onClick={() => handleCancel(resetForm)}
										endIcon={<CancelIcon />}
									>
										Cancel
									</Button>
									<Button
										type='submit'
										className='Button add-edit-button'
										disabled={isSubmitting}
										endIcon={<SendIcon />}
									>
										Submit
									</Button>
								</Container>
							</Form>
						)}
					</Formik>
				</Container>
			)}
		</Container>
	);
};

export default CommentBox;
