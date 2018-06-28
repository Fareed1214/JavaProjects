package com.library.bo;

import java.util.List;

public class LibraryView
{
	private List<BookView> library;
	private String result;

	public List<BookView> getLibrary()
	{
		return library;
	}

	public void setLibrary(List<BookView> library)
	{
		this.library = library;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

}
