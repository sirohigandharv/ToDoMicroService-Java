package com.example.todolist.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TodoItemTest {

    @Test
    public void testGetterAndSetterMethods() {
        // Arrange
        TodoItem todoItem = new TodoItem();

        // Act
        todoItem.setId(2L);
        todoItem.setTitle("New Title");
        todoItem.setDescription("New Description");

        // Assert
        assertEquals(2L, todoItem.getId());
        assertEquals("New Title", todoItem.getTitle());
        assertEquals("New Description", todoItem.getDescription());
    }

    @Test
    public void testEqualsMethod_WhenEqual() {
        // Arrange
        TodoItem todoItem1 = new TodoItem();
        todoItem1.setId(1L);
        todoItem1.setTitle("Title");
        todoItem1.setDescription("Description");
        TodoItem todoItem2 = new TodoItem();
        todoItem2.setId(1L);
        todoItem2.setTitle("Title");
        todoItem2.setDescription("Description");
        // Assert
        assertEquals(todoItem1, todoItem2);
    }

    @Test
    public void testEqualsMethod_WhenNotEqual() {
        // Arrange
        TodoItem todoItem1 = new TodoItem();
        todoItem1.setId(1L);
        todoItem1.setTitle("Title");
        todoItem1.setDescription("Description");
        TodoItem todoItem2 = new TodoItem();
        todoItem2.setId(2L);
        todoItem2.setTitle("Title");
        todoItem2.setDescription("Description");

        // Assert
        assertNotEquals(todoItem1, todoItem2);
    }

    @Test
    public void testHashCodeMethod() {
        // Arrange
        TodoItem todoItem1 = new TodoItem();
        todoItem1.setId(1L);
        todoItem1.setTitle("Title");
        todoItem1.setDescription("Description");
        TodoItem todoItem2 = new TodoItem();
        todoItem2.setId(1L);
        todoItem2.setTitle("Title");
        todoItem2.setDescription("Description");

        // Assert
        assertEquals(todoItem1.hashCode(), todoItem2.hashCode());
    }
}

