package com.example.myapplication;

import static org.junit.Assert.assertEquals;

import com.example.myapplication.data.database.TaskEntity;
import com.example.myapplication.data.mappers.CategoryMapper;
import com.example.myapplication.data.mappers.DateMapper;
import com.example.myapplication.data.mappers.PriorityMapper;
import com.example.myapplication.data.mappers.TaskMapper;
import com.example.myapplication.domain.Category;
import com.example.myapplication.domain.Priority;
import com.example.myapplication.domain.Task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;

@RunWith(JUnit4.class)
public class TaskMapperTest {
    private TaskMapper mapper;
    private final Task taskDomain = new Task(
            1, "", "", Priority.Easy.INSTANCE, Category.Unknown, false, LocalDate.of(2007, 6, 30)
    );
    private final TaskEntity taskEntity = new TaskEntity(
            1, "", "", "easy", "Неизвестная", "30.06.2007"
    );

    @Before
    public void initMapper() {
        CategoryMapper catMapper = new CategoryMapper();
        PriorityMapper priorMapper = new PriorityMapper();
        DateMapper dateMapper = new DateMapper();

        mapper = new TaskMapper(priorMapper, catMapper, dateMapper);
    }

    @Test
    public void toDomainTest() {
        assertEquals(taskDomain, mapper.toDomain(taskEntity));
    }

    @Test
    public void toEntityTest() {
        assertEquals(taskEntity, mapper.toEntity(taskDomain));
    }
}
