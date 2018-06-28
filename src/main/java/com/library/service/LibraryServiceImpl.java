package com.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.bo.BookView;
import com.library.bo.InputObject;
import com.library.bo.LibraryView;
import com.library.constants.ResponseStatus;
import com.library.repository.LibraryRepository;

/**
 * service implementation for Library Service
 * @author farshaik4
 *
 */
@Service
public class LibraryServiceImpl implements LibraryService
{
	
	@Autowired
	LibraryRepository repository;

	@Override
	public String addBook(InputObject input)
	{
		String result = ResponseStatus.ADD_BOOK_FILURE;
		try {
			if (input != null && input.getTitle() != null && !input.getTitle().isEmpty())
				result = repository.addBook(input);
		} catch (Exception e) {
			return result = e.getMessage();
		}
		return result;
	}

	@Override
	public String removeBook(String input)
	{
		String result = ResponseStatus.REMOVE_BOOK_FILURE;
		try {
			if (input != null && !input.isEmpty())
				result = repository.removeBook(input);
		} catch (Exception e) {
			return result = e.getMessage();
		}
		return result;

	}

	@Override
	public BookView searchBook(String input)
	{		
		BookView bookView = null;
		try {
			if (input != null && !input.isEmpty())
				bookView = repository.searchBook(input);
			else
			{
				bookView = new BookView();
				bookView.setResult(ResponseStatus.SEARCH_BOOK_FILURE);
			}
			
		} catch (Exception e) {
			bookView.setResult(ResponseStatus.SEARCH_BOOK_FILURE);
		}
		return bookView;
	}

	@Override
	public LibraryView searchBooks()
	{
		LibraryView library = null;
		try {
			library = repository.searchBooks();
			if(library == null)
			{
				library = new LibraryView();
				library.setResult(ResponseStatus.SEARCH_BOOK_FILURE);
			}
		} catch (Exception e) {
			library.setResult(ResponseStatus.SEARCH_BOOKS_FILURE);
		}
		return library;
	}

}
