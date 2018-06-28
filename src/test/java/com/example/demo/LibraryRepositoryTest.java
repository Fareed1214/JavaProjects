package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.library.bo.BookView;
import com.library.bo.InputObject;
import com.library.bo.LibraryView;
import com.library.constants.ResponseStatus;
import com.library.repository.LibraryRepository;
import com.library.repository.LibraryRepositoryImpl;
import com.library.service.LibraryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LibraryRepositoryTest {

	@Mock
	InputObject input;

	@InjectMocks
	LibraryRepositoryImpl repoImpl;

	/*******************
	 * Library Repository addBook() test cases
	 *******************/

	// Passing InputObject as Null
	@Test
	public void testAddBookWithNullInput() {
		assertEquals(ResponseStatus.ADD_BOOK_FILURE, repoImpl.addBook(null));
	}

	// Passing Valid Title
	@Test
	public void testAddBookWithValidData() {
		when(input.getTitle()).thenReturn("Java Book");
		when(input.getAuthors()).thenReturn("['David','John','kiran']");
		when(input.getCost()).thenReturn(300.00f);
		when(input.getDescription()).thenReturn("The Java Complete Book ");
		when(input.getGenres()).thenReturn("['Romance','Horror']");
		when(input.getNoOfCopies()).thenReturn(10);
		when(input.getPublisher()).thenReturn("Sapient");		
		assertEquals(ResponseStatus.ADD_BOOK_SUCCESS, repoImpl.addBook(input));
	}

	// Passing InputObject of Title with Empty
	@Test
	public void testAddBookWithEmptyTitle() {
		when(input.getTitle()).thenReturn("");
		assertEquals(ResponseStatus.ADD_BOOK_FILURE, repoImpl.addBook(input));
	}

	// Passing InputObject of Title with null
	@Test
	public void testAddBookWithNullTitle() {
		when(input.getTitle()).thenReturn(null);
		assertEquals(ResponseStatus.ADD_BOOK_FILURE, repoImpl.addBook(input));
	}

	/***************** Library Repository removeBook() test cases ***************/

	// Passing BookID or BookTitle as Null
	@Test
	public void testRemoveBookWithNullValue() {
		assertEquals(ResponseStatus.REMOVE_BOOK_FILURE, repoImpl.removeBook(null));
	}

	// Passing BookID or BookTitle as Empty
	@Test
	public void testRemoveBookWithBlankValue() {
		assertEquals(ResponseStatus.REMOVE_BOOK_FILURE, repoImpl.removeBook(""));
	}

	// Passing Valid BookID
	@Test
	public void testRemoveBookWithValidBookID() {
		assertEquals(ResponseStatus.REMOVE_BOOK_SUCCESS, repoImpl.removeBook("5b34843e57bf0d3704994853"));
	}

	// Passing Valid BookTitle
	@Test
	public void testRemoveBookWithValidTitle() {
		assertEquals(ResponseStatus.REMOVE_BOOK_SUCCESS, repoImpl.removeBook("Java Book"));
	}

	/***************** Library Repository searchBook() test cases ***************/

	// Passing BookID or BookTitle as Null
	@Test
	public void testSearchBookWithNullValue() {
		BookView bookview = new BookView();
		bookview.setResult(ResponseStatus.SEARCH_BOOK_FILURE);
		assertEquals(bookview.getResult(), repoImpl.searchBook(null).getResult());
	}

	// Passing BookID or BookTitle as Empty
	@Test
	public void testSearchBookWithEmptyValue() {
		BookView bookview = new BookView();
		bookview.setResult(ResponseStatus.SEARCH_BOOK_FILURE);
		assertEquals(bookview.getResult(), repoImpl.searchBook("").getResult());
	}

	// Passing Valid BookID
	@Test
	public void testSearchBookWithValidID() {
		BookView bookview = new BookView();
		bookview.setResult(ResponseStatus.SEARCH_BOOK_SUCCESS);
		assertEquals(bookview.getResult(), repoImpl.searchBook("5b34843e57bf0d3704994853").getResult());
	}

	// Passing Valid BookTitle
	@Test(expected=IllegalArgumentException.class)
	public void testSearchBookWithValidTitle() {
		BookView bookview = new BookView();
		bookview.setResult(ResponseStatus.SEARCH_BOOK_SUCCESS);
		repoImpl.searchBook("Java Book");
	}

	/***************** Library Repository searchBooks() test cases ***************/

	// Testing search Books with failure case
	@Test
	public void testSearchBooksWithFailure() {
		LibraryView view = new LibraryView();
		view.setResult(ResponseStatus.SEARCH_BOOKS_FILURE);
		assertEquals(view.getResult(), repoImpl.searchBooks().getResult());
	}

	// Testing search Books with success case
	@Test
	public void testSearchBooksWithSuccess() {
		LibraryView view = new LibraryView();
		view.setResult(ResponseStatus.SEARCH_BOOKS_SUCCESS);
		assertEquals(view.getResult(), repoImpl.searchBooks().getResult());
	}
}
