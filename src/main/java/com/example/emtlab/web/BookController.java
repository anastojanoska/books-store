package com.example.emtlab.web;

import com.example.emtlab.business.BookService;
import com.example.emtlab.business.CategoryService;
import com.example.emtlab.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Binding;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView books(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("books", bookService.findAll());
        modelAndView.setViewName("books");

        return modelAndView;
    }

    @GetMapping("/new")
    public String addBook(Model model){
//        modelAndView.addObject("categories", categoryService.findAll());
        Long id = bookService.findAll().size()+1L;
        model.addAttribute("book", new Book(id));
//        model.addAttribute("bookId", id);

        return "newBook";
    }

    @PostMapping
    public ModelAndView saveBook(@Valid Book book, BindingResult bindingResult, Model model, @RequestParam MultipartFile base64image) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.getAllErrors().size() > 1){
            modelAndView.addObject("errors", bindingResult.getAllErrors().subList(1, bindingResult.getAllErrors().size()));
            if(bookService.findById(book.getId())!= null)
                modelAndView.setViewName("editBook");
            else
                modelAndView.setViewName("newBook");

            return modelAndView;
        }
        bookService.save(book, base64image);
        modelAndView.addObject("books", bookService.findAll());
        modelAndView.setViewName("books");
        return modelAndView;
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id){
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/edit")
    public ModelAndView editBook(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("book", bookService.findById(id));
        modelAndView.setViewName("editBook");

        return modelAndView;
    }


}
