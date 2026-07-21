package com.example.flowstasksapp;

import static org.junit.Assert.assertEquals;

import com.example.flowstasksapp.data.mappers.CategoryMapper;
import com.example.flowstasksapp.domain.Category;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CategoryMapperTest {
    private CategoryMapper mapper;

    @Before
    public void initMapper() {
        mapper = new CategoryMapper();
    }

    @Test
    public void toDomainCorrectTest() {
        Category work = Category.Work;
        assertEquals(work, mapper.toDomain("Работа"));
    }

    @Test
    public void toDomainIncorrectTest() {
        assertEquals(Category.Unknown, mapper.toDomain("работа"));
    }

    @Test
    public void toEntity() {
        String work = "Дом и быт";
        assertEquals(work, mapper.toEntity(Category.HomeAndChores));
    }
}
