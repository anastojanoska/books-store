package com.example.emtlab.business;

import com.example.emtlab.exceptions.AuthorNotFoundException;
import com.example.emtlab.model.Author;
import com.example.emtlab.model.Book;
import com.example.emtlab.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author create(String name) {
        Author author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public List<Author> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    @Override
    @Transactional
    public List<Author> getAllAuthorsByBook(Long bookId) {
        List<Author> authors = new ArrayList<>();

        for(Author a : authorRepository.findAll()){
            List<Book> books = a.getBooks();
            for(Book b : books){
                if(b.getId().equals(bookId)){
                    authors.add(a);
                    break;
                }
            }
        }

        return authors;
    }

    @Override
    @Transactional
    public Author findById(Long id) {
        Author author =  authorRepository.findById(id)
                .orElseThrow(()-> new AuthorNotFoundException(id));
        return author;
    }

    @Override
    @Transactional
    public List<Book> getAllBooksByAuthor(Long authorId) {
        Author author = this.findById(authorId);

        return author.getBooks();
    }

    @Override
    @Transactional
    public List<Book> addBookToAuthor(Book book, Long authorId) {
        Author author = this.findById(authorId);
        author.getBooks().add(book);
        this.save(author);
        return author.getBooks();
    }


    @Override
    @Transactional
    public void deleteBook(Long bookId, Long authorId) {
        Author author = this.findById(authorId);
        List<Book> books = author.getBooks().stream().filter(book -> !book.getId().equals(bookId)).collect(Collectors.toList());
        author.setBooks(books);
        this.save(author);
    }

}
