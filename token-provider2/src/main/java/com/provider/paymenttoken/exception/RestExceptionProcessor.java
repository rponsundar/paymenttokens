/**
 * 
 */
package com.provider.paymenttoken.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.provider.paymenttoken.beans.ErrorInfo;
import com.provider.paymenttoken.beans.ErrorsInfo;

/**
 * @author Shivakumar
 *
 */
	@ControllerAdvice
	public class RestExceptionProcessor {
		
		@ExceptionHandler(MethodArgumentNotValidException.class)
		@ResponseStatus(value=HttpStatus.BAD_REQUEST)
		@ResponseBody
		public ErrorsInfo handleMethodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException ex) {
			
			List<ErrorInfo> errorList = new ArrayList<ErrorInfo>();
			ErrorsInfo errors = new ErrorsInfo();
			
			BindingResult result = ex.getBindingResult();		
			for (ObjectError err : result.getAllErrors()) {
				System.out.println(err.getDefaultMessage());
				errorList.add(new ErrorInfo(err.getDefaultMessage()));
			}
			errors.setErrors(errorList);
			//errorInfo.getFieldErrors().addAll(populateFieldErrors(fieldErrors));
			
			return errors;
		}
		
		/**
		 * Method populates {@link List} of {@link FieldErrorDTO} objects. Each list item contains
		 * localized error message and name of a form field which caused the exception.
		 * Use the {@link #localizeErrorMessage(String) localizeErrorMessage} method.
		 * 
		 * @param fieldErrorList - {@link List} of {@link FieldError} items
		 * @return {@link List} of {@link FieldErrorDTO} items
		 *//*
		public List<FieldErrorDTO> populateFieldErrors(List<FieldError> fieldErrorList) {
			List<FieldErrorDTO> fieldErrors = new ArrayList<FieldErrorDTO>();
			StringBuilder errorMessage = new StringBuilder("");
			
			for (FieldError fieldError : fieldErrorList) {
				
				errorMessage.append(fieldError.getCode()).append(".");
				errorMessage.append(fieldError.getObjectName()).append(".");
				errorMessage.append(fieldError.getField());
				
				String localizedErrorMsg = localizeErrorMessage(errorMessage.toString());
				
				fieldErrors.add(new FieldErrorDTO(fieldError.getField(), localizedErrorMsg));
				errorMessage.delete(0, errorMessage.capacity());
			}
			return fieldErrors;
		}
		
		*//**
		 * Method retrieves appropriate localized error message from the {@link MessageSource}.
		 * 
		 * @param errorCode - key of the error message
		 * @return {@link String} localized error message 
		 *//*
		public String localizeErrorMessage(String errorCode) {
			Locale locale = LocaleContextHolder.getLocale();
			String errorMessage = messageSource.getMessage(errorCode, null, locale);
			return errorMessage;
		}*/

}
