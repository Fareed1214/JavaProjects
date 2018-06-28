package com.library.bo;

import java.util.List;
import com.library.bean.*;

public class BookView
{
	private String id;
	private String title;
	private String description;
	private Double cost;
	int noOfCopies;
	private Publisher publisher;
	private List<Author> authors;
	private List<Genre> genres;
	private String result;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Double getCost()
	{
		return cost;
	}
	public void setCost(Double cost)
	{
		this.cost = cost;
	}
	public int getNoOfCopies()
	{
		return noOfCopies;
	}
	public void setNoOfCopies(int noOfCopies)
	{
		this.noOfCopies = noOfCopies;
	}
	public Publisher getPublisher()
	{
		return publisher;
	}
	public void setPublisher(Publisher publisher)
	{
		this.publisher = publisher;
	}
	public List<Author> getAuthors()
	{
		return authors;
	}
	public void setAuthors(List<Author> authors)
	{
		this.authors = authors;
	}
	public List<Genre> getGenres()
	{
		return genres;
	}
	public void setGenres(List<Genre> genres)
	{
		this.genres = genres;
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
