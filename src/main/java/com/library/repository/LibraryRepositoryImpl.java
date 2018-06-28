package com.library.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.library.bean.Author;
import com.library.bean.Genre;
import com.library.bean.Publisher;
import com.library.bo.BookView;
import com.library.bo.InputObject;
import com.library.bo.LibraryView;
import com.library.constants.ResponseStatus;
import com.library.util.MongoDBUtil;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Repository
public class LibraryRepositoryImpl implements LibraryRepository {

	/*
	 * @Value("${mongo.host}") private String host;
	 */

	public String addBook(InputObject input) {
		String result = "";
		try {
			if(input == null)
				return ResponseStatus.ADD_BOOK_FILURE;
			MongoCursor<Document> geners = fetchBookDependencies(input.getGenres(), "Genre");
			MongoCursor<Document> authors = fetchBookDependencies(input.getAuthors(), "Author");
			Document publisher = addAndGetPublisher(input);
			MongoCollection<Document> coll = MongoDBUtil.getCollection("Book");

			if (input.getTitle() != null) {
				if (coll.withDocumentClass(Document.class).find(Filters.eq("title", input.getTitle()))
						.first() == null) {
					Document document = new Document();
					document.append("title", input.getTitle());
					document.append("description", input.getDescription());
					if (publisher != null)
						document.append("pid", publisher.get("_id"));
					document.append("cost", input.getCost());
					document.append("noOfCopies", input.getNoOfCopies());
					if (geners != null) {
						String ids = "";
						while (geners.hasNext())
							ids += ids == "" ? geners.next().get("_id") : "," + geners.next().get("_id");
						document.append("genreIds", ids);
					}
					if (authors != null) {
						String ids = "";
						while (authors.hasNext())
							ids += ids == "" ? authors.next().get("_id") : "," + authors.next().get("_id");
						document.append("authorIds", ids);
					}
					coll.insertOne(document);
					result = ResponseStatus.ADD_BOOK_SUCCESS;
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return result;
	}

	private MongoCursor<Document> fetchBookDependencies(String input, String collName) {

		MongoCursor<Document> docs = null;
		try {
			MongoCollection<Document> coll = MongoDBUtil.getCollection(collName);

			if (input != null) {
				Stream<String> streamNames = Arrays
						.stream(input.replace("[", "").replace("]", "").replaceAll("'", "").split(","));
				List<String> listNames = streamNames.filter(
						name -> coll.withDocumentClass(Document.class).find(Filters.eq("name", name)).first() == null)
						.collect(Collectors.toList());

				listNames.forEach(str -> {
					Document document = new Document();
					document.append("name", str);
					coll.insertOne(document);
				});

				docs = coll.withDocumentClass(Document.class).find(
						Filters.in("name", input.replace("[", "").replace("]", "").replaceAll("'", "").split(",")))
						.iterator();
			}

		} catch (Exception e) {
			throw e;
		}

		return docs;
	}

	private Document addAndGetPublisher(InputObject input) {

		Document doc = null;
		try {
			MongoCollection<Document> genre = MongoDBUtil.getCollection("Publisher");

			if (input.getPublisher() != null) {
				doc = genre.withDocumentClass(Document.class).find(Filters.eq("name", input.getPublisher())).first();
				if (doc == null) {
					doc = new Document();
					doc.append("name", input.getPublisher());
					genre.insertOne(doc);
					doc = genre.withDocumentClass(Document.class).find(Filters.eq("name", input.getPublisher()))
							.first();
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return doc;
	}

	public String removeBook(String input) {
		String result = "";
		try {
			if(input == null || input.isEmpty())
				return ResponseStatus.REMOVE_BOOK_FILURE;
			MongoCollection<Document> coll = MongoDBUtil.getCollection("Book");

			Document doc = coll.withDocumentClass(Document.class).find(Filters.eq("title", input)).first();
			if (doc == null)
				doc = coll.withDocumentClass(Document.class).find(Filters.eq("_id", new ObjectId(input))).first();
			if (doc != null) {
				coll.deleteOne(doc);
				result = ResponseStatus.REMOVE_BOOK_SUCCESS;
			}
		} catch (Exception e) {
			throw e;
		}

		return result;
	}

	public BookView searchBook(String input) {

		BookView bookView = new BookView();
		try {
			if(input == null || input.isEmpty())
			{
				bookView.setResult(ResponseStatus.SEARCH_BOOK_FILURE);
				return bookView;
			}
			MongoCollection<Document> coll = MongoDBUtil.getCollection("Book");
			Document doc = coll.withDocumentClass(Document.class).find(Filters.eq("title", input)).first();
			if (doc == null)
				doc = coll.withDocumentClass(Document.class).find(Filters.eq("_id", new ObjectId(input))).first();
			if (doc != null) {
				bookView.setId(doc.get("_id").toString());
				bookView.setTitle(doc.get("title").toString());
				if (doc.get("description") != null)
					bookView.setDescription(doc.get("description").toString());
				if (doc.get("cost") != null)
					bookView.setCost((Double) doc.get("cost"));
				if (doc.get("noOfCopies") != null)
					bookView.setNoOfCopies((int) doc.get("noOfCopies"));

				if (doc.get("pid") != null) {
					Document pdoc = MongoDBUtil.getCollection("Publisher").withDocumentClass(Document.class)
							.find(Filters.eq("_id", new ObjectId(doc.get("pid").toString()))).first();
					Publisher p = new Publisher();
					p.setId(doc.get("pid").toString());
					p.setName(pdoc.get("name").toString());
					bookView.setPublisher(p);
				}

				bookView.setGenres(getGenreData("genreIds", doc, "Genre"));
				bookView.setAuthors(getAuthorData("authorIds", doc, "Author"));
				bookView.setResult(ResponseStatus.SEARCH_BOOK_SUCCESS);

			}
		} catch (Exception e) {
			throw e;
		}

		return bookView;
	}

	public LibraryView searchBooks() {
		LibraryView view = new LibraryView();
		try {
			List<BookView> list = new ArrayList<>();
			MongoCollection<Document> coll = MongoDBUtil.getCollection("Book");
			FindIterable<Document> docs = coll.withDocumentClass(Document.class).find();
			docs.iterator().forEachRemaining(doc -> list.add(searchBook(doc.get("title").toString())));
			view.setLibrary(list);
			view.setResult(ResponseStatus.SEARCH_BOOKS_SUCCESS);
		} catch (Exception e) {
			throw e;
		}

		return view;
	}

	private List<Genre> getGenreData(String ids, Document doc, String collName) {
		List<Genre> list = new ArrayList<>();
		try {
			if (doc.get(ids) != null) {
				List<Document> doccs = new ArrayList<>();
				for (String s : doc.get(ids).toString().replace("\"", "").split(",")) {
					Document docs = MongoDBUtil.getCollection(collName).withDocumentClass(Document.class)
							.find(Filters.eq("_id", new ObjectId(s))).first();
					if (docs != null)
						doccs.add(docs);
				}
				Iterator<Document> ite = doccs.iterator();

				while (ite.hasNext()) {

					Document gDoc = ite.next();
					Genre p = new Genre();
					p.setId(gDoc.get("_id").toString());
					p.setName(gDoc.get("name").toString());
					list.add(p);
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return list;
	}

	private List<Author> getAuthorData(String ids, Document doc, String collName) {
		List<Author> list = new ArrayList<>();
		try {
			if (doc.get(ids) != null) {
				List<Document> doccs = new ArrayList<>();
				for (String s : doc.get(ids).toString().replace("\"", "").split(",")) {
					Document docs = MongoDBUtil.getCollection(collName).withDocumentClass(Document.class)
							.find(Filters.eq("_id", new ObjectId(s))).first();
					if (docs != null)
						doccs.add(docs);
				}
				Iterator<Document> ite = doccs.iterator();

				while (ite.hasNext()) {

					Document gDoc = ite.next();
					Author p = new Author();
					p.setId(gDoc.get("_id").toString());
					p.setName(gDoc.get("name").toString());
					list.add(p);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		
		return list;
	}
}
