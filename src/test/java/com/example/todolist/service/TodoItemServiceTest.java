package com.example.todolist.service;

import com.example.todolist.model.TodoItem;
import com.example.todolist.repository.TodoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoItemServiceTest {

    @Mock
    private TodoItemRepository repository;

    @InjectMocks
    private TodoItemService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById_WhenTodoItemExists() {
        // Arrange
        Long id = 1L;
        TodoItem todoItem = new TodoItem();
        todoItem.setId(id);
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("Test Description");
        when(repository.findById(id)).thenReturn(Optional.of(todoItem));

        // Act
        TodoItem result = service.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(todoItem, result);
    }

    @Test
    public void testFindById_WhenTodoItemDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        // Act
        TodoItem result = service.findById(id);

        // Assert
        assertNull(result);
    }

    @Test
    public void testFindAll() {
        // Arrange
        List<TodoItem> todoItemList = new ArrayList<>();
        todoItemList.add(new TodoItem());
        todoItemList.add(new TodoItem());
        when(repository.findAll()).thenReturn(todoItemList);

        // Act
        List<TodoItem> result = service.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testSave() {
        // Arrange
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("Test Description");
        when(repository.save(todoItem)).thenReturn(todoItem);

        // Act
        TodoItem result = service.save(todoItem);

        // Assert
        assertNotNull(result);
        assertEquals(todoItem.getTitle(), result.getTitle());
        assertEquals(todoItem.getDescription(), result.getDescription());
    }

    @Test
    public void testDelete_WhenTodoItemExists() {
        // Arrange
        Long id = 1L;
        TodoItem todoItem = new TodoItem();
        todoItem.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(todoItem));

        // Act
        service.delete(id);

        // Assert
        verify(repository, times(1)).delete(todoItem);
    }

    @Test
    public void testDelete_WhenTodoItemDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> service.delete(id));
    }
}
