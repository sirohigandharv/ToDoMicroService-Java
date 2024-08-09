package com.example.todolist.service;

import com.example.todolist.model.TodoItem;
import com.example.todolist.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemService {
    @Autowired
    private TodoItemRepository repository;

    public List<TodoItem> findAll() {
        return repository.findAll();
    }

    public TodoItem save(TodoItem item) {
        return repository.save(item);
    }

    public void delete(Long id) {
        TodoItem todoItem = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem not found with id: " + id));
        repository.delete(todoItem);
    }

    public TodoItem findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
