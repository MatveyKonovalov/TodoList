package com.example.myapplication;

import static org.junit.Assert.assertSame;

import com.example.myapplication.data.mappers.PriorityMapper;
import com.example.myapplication.domain.Priority;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class PriorityMapperTest {
    private PriorityMapper mapper;
    private final Priority.Easy easy = Priority.Easy.INSTANCE;
    private final Priority.Medium medium = Priority.Medium.INSTANCE;
    private final Priority.Hard hard = Priority.Hard.INSTANCE;

    @Before
    public void initPriorityMapper() {
        mapper = new PriorityMapper();
    }

    @Test
    public void toDomainCorrectInputTest() {
        ArrayList<String> priorityString = new ArrayList<>();
        priorityString.add("easy");
        priorityString.add("medium");
        priorityString.add("hard");

        Priority easy = mapper.toDomain(priorityString.get(0));
        Priority medium = mapper.toDomain(priorityString.get(1));
        Priority hard = mapper.toDomain(priorityString.get(2));

        assertSame(this.easy, easy);
        assertSame(this.medium, medium);
        assertSame(this.hard, hard);
    }

    @Test(expected = Exception.class)
    public void toDomainInCorrectInputTest() {
        Priority easy = mapper.toDomain("Easy"); // должно быть easy
        assertSame(this.easy, easy);
    }

    @Test
    public void toEntityTest(){
        assertSame("easy", mapper.toEntity(easy));
        assertSame("medium", mapper.toEntity(medium));
        assertSame("hard", mapper.toEntity(hard));
    }
}
