package com.library.service;

import org.springframework.stereotype.Service;

import com.library.bo.BookView;
import com.library.bo.InputObject;
import com.library.bo.LibraryView;

@Service
public interface LibraryService
{
	String addBook(InputObject input);

	String removeBook(String input);

	BookView searchBook(String input);

	LibraryView searchBooks();

}
