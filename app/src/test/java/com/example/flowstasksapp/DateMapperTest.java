package com.example.flowstasksapp;

import static org.junit.Assert.assertEquals;

import com.example.flowstasksapp.data.mappers.DateMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;

@RunWith(JUnit4.class)
public class DateMapperTest {
    private DateMapper mapper;

    @Before
    public void initMapper(){
        mapper = new DateMapper();
    }

    @Test
    public void toDomainTest(){
        var date = LocalDate.of(2007, 6, 30);
        assertEquals(date, mapper.toDomain("30.06.2007"));
    }

}