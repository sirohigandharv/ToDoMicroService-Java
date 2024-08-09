package com.example.todolist.controller;

import com.example.todolist.model.TodoItem;
import com.example.todolist.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todoitems")
public class TodoItemController {
    @Autowired
    private TodoItemService service;

    @GetMapping
    public List<TodoItem> getAllItems() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getItemById(@PathVariable Long id) {
        TodoItem item = service.findById(id);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public TodoItem createItem(@RequestBody TodoItem item) {
        return service.save(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateItem(@PathVariable Long id, @RequestBody TodoItem itemDetails) {
        TodoItem item = service.findById(id);
        if (item != null) {
            item.setTitle(itemDetails.getTitle());
            item.setDescription(itemDetails.getDescription());
            return new ResponseEntity<>(service.save(item), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        TodoItem item = service.findById(id);
        if (item != null) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}