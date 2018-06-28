package com.library.bean;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Publisher")
public class Publisher 
{
	@Id
	String id;
	String name;
	Date date;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	@Override
	public String toString()
	{
		return "Publisher [id=" + id + ", name=" + name + ", date=" + date + "]";
	}

	
	
}
