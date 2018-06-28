package com.library.repository;

import com.library.bo.BookView;
import com.library.bo.InputObject;
import com.library.bo.LibraryView;

public interface LibraryRepository {
	public String addBook(InputObject input);

	public String removeBook(String input);

	public BookView searchBook(String input);

	public LibraryView searchBooks();
}
