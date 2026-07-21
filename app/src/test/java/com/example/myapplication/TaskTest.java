package com.example.myapplication;

import static org.junit.Assert.assertEquals;

import com.example.myapplication.domain.Category;
import com.example.myapplication.domain.Priority;
import com.example.myapplication.domain.Task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;

@RunWith(JUnit4.class)
public class TaskTest {
    private final Task task = new Task(1,
            "",
            "",
            Priority.Easy.INSTANCE,
            Category.Unknown,
            false,
            LocalDate.of(2007, 6, 30));

    @Test
    public void getStringTime() {
        assertEquals("30.06.2007", task.getDateString());
    }
}
