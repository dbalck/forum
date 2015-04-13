package com.forum.web.parse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.forum.web.atom.AtomEntry;
import com.forum.web.atom.AtomFeed;
import com.forum.web.atom.Author;
import com.forum.web.atom.Contributor;

public class AtomParser extends Parser {
	
	public static final int ATOM_CONTENT_MAX_LEN = 10000;
	public static final int ATOM_LANG_MAX_LEN = 20;
	public static final int ATOM_NAME_MAX_LEN = 50;
	public static final int ATOM_STR_MAX_LEN = 1000;

	// XML input factory for streamReaders
	private XMLInputFactory factory = XMLInputFactory.newInstance();

	// Creates a new Rss reader that can aggregate several RssFeeds.
	public AtomParser() {
	}

	// Method for getting the data from a URL
	@SuppressWarnings("unchecked")
	public Stream parseLink(InputStream input) {

		// the Channel that will be returned
		AtomFeed feed = null;

		// represents the items that will be included in this channel
		Set<AtomEntry> feedEntries = new HashSet<AtomEntry>();

		// a stack to represent the dom
		Stack<HashMap<String, Object>> dom = new Stack<HashMap<String, Object>>();

		// a wrapper object for when popping things off the stack
		HashMap<String, Object> top = null;

		try {

			// File file = new File("testAtom.xml");
			// InputStream input = new FileInputStream(file);

			// System.out.println("reading file testRss.xml");

			// set up XML stream reader from the URL
			XMLStreamReader reader = factory.createXMLStreamReader(input);

			while (reader.hasNext()) {
				String ns = reader.getNamespaceURI();
				reader.next();
				if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {

					// get the xml tag name
					String currentElement = reader.getLocalName();
					switch (currentElement) {

					case "feed":
						System.out.println("starting feed");
						dom.push(new HashMap<String, Object>());
						break;

					case "entry":
						dom.push(new HashMap<String, Object>());
						break;

					case "author":
						top = dom.pop();
						if (!top.containsKey("authors")) {
							top.put("authors", new HashSet<Author>());
						}
						dom.push(top);
						dom.push(new HashMap<String, Object>());
						break;

					case "contributor":
						top = dom.pop();
						if (!top.containsKey("contributors")) {
							top.put("contributors", new HashSet<String>());
						}
						dom.push(top);
						dom.push(new HashMap<String, Object>());
						break;

					case "source":
						dom.push(new HashMap<String, Object>());
						break;

					// used by Feed and entry
					case "title":
						top = dom.pop();
						top.put("title", reader.getElementText());
						dom.push(top);
						top = null;
						break;
						
					// used by Feed and entry
					case "logo":
						top = dom.pop();
						top.put("logo", reader.getElementText());
						dom.push(top);
						top = null;
						break;

					// used by Feed and entry
					case "icon":
						top = dom.pop();
						top.put("icon", reader.getElementText());
						dom.push(top);
						top = null;
						break;

					// used by feed and entry
					case "link":
						String currentLink = reader.getAttributeValue(ns, "href");
						top = dom.pop();
						top.put("link", currentLink);
						dom.push(top);
						top = null;
						break;

					// used by feed and entry
					case "category":
						String cat = reader.getAttributeValue(ns, "term").toLowerCase();
						top = dom.pop();
						if (!top.containsKey("categories")) {
							top.put("categories", new ArrayList<String>());
						}
						List<String> categories = (List<String>) top.get("categories");
						categories.add(cat);
						dom.push(top);
						top = null;
						break;

					// used by entry
					case "summary":
						top = dom.pop();
						top.put("summary", reader.getElementText());
						dom.push(top);
						top = null;
						break;

					// used by entry
					case "content":
						top = dom.pop();
						top.put("content", reader.getElementText());
						dom.push(top);
						top = null;
						break;

					// unique to a channel
					case "rights":
						top = dom.pop();
						top.put("rights", reader.getElementText());
						dom.push(top);
						top = null;
						break;

					// used by feed, entry
					case "updated":
						top = dom.pop();
						top.put("updated", Parser.parseDate(reader.getElementText()));
						dom.push(top);
						top = null;
						break;

					// unique to a feed
					case "generator":
						top = dom.pop();
						top.put("generator", reader.getElementText());
						dom.push(top);
						top = null;
						break;

					// used by feed, entry
					case "id":
						top = dom.pop();
						top.put("id", reader.getElementText());
						dom.push(top);
						top = null;
						break;
						
					case "name":
						top = dom.pop();
						top.put("name", reader.getElementText());
						dom.push(top);
						top = null;
						break;
						
					case "email":
						top = dom.pop();
						top.put("email", reader.getElementText());
						dom.push(top);
						top = null;
						break;

					// used by entry
					case "published":
						top = dom.pop();
						top.put("published", Parser.parseDate(reader.getElementText()));
						dom.push(top);
						top = null;
						break;
						
					// used by feed
					case "subtitle":
						top = dom.pop();
						top.put("subtitle", reader.getElementText());
						dom.push(top);
						top = null;
						break;

					}

					// the entry/feed is finished, we need to use what we've
					// gathered and make an object out of it
				} else if (reader.getEventType() == XMLStreamConstants.END_ELEMENT) {

					// get what type of element is ending
					String endElement = reader.getLocalName();

					// create an object depending on what it is
					switch (endElement) {

					// the channel is closed, create an RssChannel out of its
					// sub-elements
					case "feed":
						top = dom.pop();

						// if the channel has the minimum required elements, get
						// to work!
						if (top.containsKey("title") && top.containsKey("id")
								&& top.containsKey("updated")) {

							// created builder from core required elements
							feed = new AtomFeed((String) top.get("title"),
									(String) top.get("id"),
									(long) top.get("updated"));

							// add recommended elements as they exist
							// if the feed map has an author key, it should
							// point to an ArrayList of author names (Strings)
							if (top.containsKey("authors")) {
								feed.setAuthors((Set<Author>) top.get("authors"));
							}
							
							if (top.containsKey("contributors")) {
								feed.setContributors((Set<Contributor>) top.get("contributors"));
							}

							if (top.containsKey("link")) {
								feed.setLink((String) top.get("link"));
							}

							if (top.containsKey("categories")) {
								feed.setCategories((List<String>) top.get("categories"));
							}

							if (top.containsKey("rights")) {
								feed.setRights((String) top.get("copyright"));
							}

							if (top.containsKey("generator")) {
								feed.setGenerator((String) top.get("generator"));
							}

							if (top.containsKey("logo")) {
								feed.setLogo((String) top.get("logo"));
							}
							
							if (top.containsKey("icon")) {
								feed.setIcon((String) top.get("icon"));
							}
							if (top.containsKey("subtitle")) {
								feed.setSubtitle((String) top.get("subtitle"));
							}
							
							feed.setEntries(feedEntries);

						}
						// top = null;
						break;

					// an item has closed, create an RssItem out of it's
					// sub-elements
					case "entry":
						top = dom.pop();
						AtomEntry entry;

						// check if a required field is missing
						if (top.containsKey("title") && top.containsKey("id") && top.containsKey("updated")) {
							entry = new AtomEntry((String) top.get("title"),
									(String) top.get("id"),
									(long) top.get("updated"));

							// add optional fields if they exist
							if (top.containsKey("authors")) {
								entry.setAuthors((Set<Author>) top.get("authors"));
							}

							if (top.containsKey("content")) {
								entry.setContent((String) top.get("content"));
							}

							if (top.containsKey("link")) {
								entry.setLink((String) top.get("link"));
							}

							if (top.containsKey("summary")) {
								entry.setSummary((String) top.get("summary"));
							}
							
							if (top.containsKey("categories")) {
								entry.setCategories((List<String>) top.get("categories"));
							}
							
							if (top.containsKey("contributors")) {
								entry.setContributors((Set<Contributor>) top.get("contributors"));
							}
							
							if (top.containsKey("published")) {
								entry.setPublished((long) top.get("published"));
							}
							
							if (top.containsKey("source")) {
								entry.setSource((AtomFeed) top.get("source"));
							}
							
							if (top.containsKey("rights")) {
								entry.setRights((String) top.get("rights"));
							}
							
							feedEntries.add(entry);

						} else {
							System.out.println("bad entry");
						}
						top = null;
						break;

					// create an image object from previous data and put it into
					// its supertag's hashmap
					case "author":
						top = dom.pop();
						Author author;

						// if the required field exists...
						if (top.containsKey("name")) {
							author = new Author((String) top.get("name"));
							
							if (top.containsKey("email")) {
								author.setEmail((String) top.get("email"));
							}

							if (top.containsKey("uri")) {
								author.setUri((String) top.get("uri"));
							}

							// put it back onto the dom into its super's hashmap
							top = dom.pop();
							Set<Author> authors = (Set<Author>) top.get("authors");
							authors.add(author);
							top.put("authors", authors);
							dom.push(top);
							top = null;
						}
						break;

					// a textInput has closed. Create a TextInput object and add
					// it to the dom in its super's Object hashmap
					case "contributor":
						top = dom.pop();
						Contributor contributor;

						// if the required field exists...
						if (top.containsKey("name")) {
							contributor = new Contributor((String) top.get("name"));
							
							if (top.containsKey("email")) {
								contributor.setEmail((String) top.get("email"));
							}

							if (top.containsKey("uri")) {
								contributor.setUri((String) top.get("uri"));
							}

							// put it back onto the dom into its super's hashmap
							top = dom.pop();
							Set<Contributor> contributors = (Set<Contributor>) top.get("contributors");
							contributors.add(contributor);
							top.put("contributors", contributors);
							dom.push(top);
							top = null;
						}
						break;

					// and enclosure has closed. create an object and add it to
					// its super element's hashmap
					case "source":
						top = dom.pop();
						AtomFeed source;

						if (top.containsKey("title")
								&& top.containsKey("link")
								&& top.containsKey("updated")) {
							source = new AtomFeed(
									(String) top.get("title"),
									(String) top.get("link"), Parser.parseDate((String) top
											.get("updated")));

							// put it back onto the dom into its super's hashmap
							top = dom.pop();
							top.put("source", source);
							dom.push(top);
							top = null;
						}
						break;

					}
				}

			}

		} catch (XMLStreamException e) {
			System.out.print("Error when parsing xml: ");
			System.out.println(e);
			System.exit(1);

		}

		// print out diagnostics for debugging
//		if (feed != null) {
//			System.out.println(feed.toString());
//			System.out.println("Items in channel:");
//			for (AtomEntry entry : feed.getEntries()) {
//				System.out.println(entry.toString());
//			}
//			System.out.println("Feed's entry count: "
//					+ feed.getEntries().size());
//			System.out.println("parsing complete.");
//
//		} else {
//			System.out
//					.println("parsing failed. Feed returned is a null object");
//		}

		return (Stream) feed;

	}

}
