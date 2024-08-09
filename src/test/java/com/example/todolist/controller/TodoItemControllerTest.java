package com.example.todolist.controller;

import com.example.todolist.model.TodoItem;
import com.example.todolist.service.TodoItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoItemController.class)
public class TodoItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoItemService todoItemService;

    @Test
    public void testGetAllTodoItems() throws Exception {
        // Arrange
        TodoItem todoItem1 = new TodoItem();
        todoItem1.setId(1L);
        todoItem1.setTitle("Title 1");
        todoItem1.setDescription("Description 1");
        TodoItem todoItem2 = new TodoItem();
        todoItem2.setId(2L);
        todoItem2.setTitle("Title 2");
        todoItem2.setDescription("Description 2");
        List<TodoItem> todoItems = Arrays.asList(todoItem1, todoItem2);
        when(todoItemService.findAll()).thenReturn(todoItems);

        // Act & Assert
        mockMvc.perform(get("/api/todoitems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Title 2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"));

        verify(todoItemService, times(1)).findAll();
    }

    @Test
    public void testGetTodoItemById_WhenTodoItemExists() throws Exception {
        // Arrange
        Long id = 1L;
        TodoItem todoItem = new TodoItem();
        todoItem.setId(id);
        todoItem.setTitle("Title");
        todoItem.setDescription("Description");
        when(todoItemService.findById(id)).thenReturn(todoItem);

        // Act & Assert
        mockMvc.perform(get("/api/todoitems/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.description").value("Description"));

        verify(todoItemService, times(1)).findById(id);
    }

    @Test
    public void testGetTodoItemById_WhenTodoItemDoesNotExist() throws Exception {
        // Arrange
        Long id = 1L;
        when(todoItemService.findById(id)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/todoitems/{id}", id))
                .andExpect(status().isNotFound());

        verify(todoItemService, times(1)).findById(id);
    }

    @Test
    public void testCreateTodoItem() throws Exception {
        // Arrange
        TodoItem todoItem = new TodoItem();
        todoItem.setId(1L);
        todoItem.setTitle("New Title");
        todoItem.setDescription("New Description");
        when(todoItemService.save(any(TodoItem.class))).thenReturn(todoItem);

        String requestBody = "{\"title\":\"New Title\",\"description\":\"New Description\"}";

        // Act & Assert
        mockMvc.perform(post("/api/todoitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.description").value("New Description"));

        verify(todoItemService, times(1)).save(any(TodoItem.class));
    }

    @Test
    public void testUpdateTodoItem_WhenTodoItemExists() throws Exception {
        // Arrange
        Long id = 1L;
        TodoItem existingTodoItem = new TodoItem();
        existingTodoItem.setId(id);
        existingTodoItem.setTitle("Existing Title");
        existingTodoItem.setDescription("Existing Description");
        TodoItem updatedTodoItem = new TodoItem();
        updatedTodoItem.setId(id);
        updatedTodoItem.setTitle("Updated Title");
        updatedTodoItem.setDescription("Updated Description");

        when(todoItemService.findById(id)).thenReturn(existingTodoItem);
        when(todoItemService.save(updatedTodoItem)).thenReturn(updatedTodoItem);

        String requestBody = "{\"title\":\"Updated Title\",\"description\":\"Updated Description\"}";

        // Act & Assert
        mockMvc.perform(put("/api/todoitems/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"));

        verify(todoItemService, times(1)).findById(id);
        verify(todoItemService, times(1)).save(updatedTodoItem);
    }

    @Test
    public void testUpdateTodoItem_WhenTodoItemDoesNotExist() throws Exception {
        // Arrange
        Long id = 999L; // Assuming this ID does not exist in the database
        when(todoItemService.findById(id)).thenReturn(null);

        String requestBody = "{\"title\":\"Updated Title\",\"description\":\"Updated Description\"}";

        // Act & Assert
        mockMvc.perform(put("/api/todoitems/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());

        verify(todoItemService, times(1)).findById(id);
        verify(todoItemService, never()).save(any(TodoItem.class));
    }

    @Test
    public void testDeleteItem_WhenTodoItemExists() throws Exception {
        // Arrange
        Long id = 1L;
        TodoItem todoItem = new TodoItem();
        when(todoItemService.findById(id)).thenReturn(todoItem);

        // Act & Assert
        mockMvc.perform(delete("/api/todoitems/{id}", id))
                .andExpect(status().isNoContent());

        verify(todoItemService, times(1)).delete(id);
    }

    @Test
    public void testDeleteItem_WhenTodoItemDoesNotExist() throws Exception {
        // Arrange
        Long id = 1L;
        when(todoItemService.findById(id)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(delete("/api/todoitems/{id}", id))
                .andExpect(status().isNotFound());

        verify(todoItemService, never()).delete(id);
    }

}
