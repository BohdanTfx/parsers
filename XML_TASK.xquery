for $book in doc("XML_TASK.xml")/catalog/booksList/book
where $book/price>5.95
return $book