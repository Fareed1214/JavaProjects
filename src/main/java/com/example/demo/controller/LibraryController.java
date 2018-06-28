package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.library.bo.BookView;
import com.library.bo.InputObject;
import com.library.bo.LibraryView;
import com.library.constants.ResponseStatus;
import com.library.service.LibraryService;

@RestController
public class LibraryController
{

	@Autowired
	LibraryService service;

	@RequestMapping(value = "/library/addbook", method = RequestMethod.POST)
	public ResponseEntity<String> addBook(@RequestBody InputObject input)
	{		
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		String result = service.addBook(input);
		if (result.equals(ResponseStatus.ADD_BOOK_SUCCESS))
			status = HttpStatus.OK;
		return new ResponseEntity<String>(result, status);
	}
		
	@RequestMapping(value = "/library/removebook/{input}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeBook(@PathVariable("input") String input)
	{
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		String result = service.removeBook(input);		
		if (result.equals(ResponseStatus.REMOVE_BOOK_SUCCESS))
			status = HttpStatus.OK;
		return new ResponseEntity<String>(result, status);
	}

	@GetMapping("/library/searchbook/{input}")
	public ResponseEntity<BookView> searchBook(@PathVariable("input") String input)
	{
		HttpStatus status = HttpStatus.NO_CONTENT;
		BookView result = service.searchBook(input);		
		if (result != null)
			status = HttpStatus.OK;
		return new ResponseEntity<BookView>(result, status);
	}

	@GetMapping("/library/searchbooks")
	public ResponseEntity<LibraryView> searchBooks()
	{
		HttpStatus status = HttpStatus.NO_CONTENT;
		LibraryView result = service.searchBooks();		
		if (result != null)
			status = HttpStatus.OK;
		return new ResponseEntity<LibraryView>(result, status);
	}

}
