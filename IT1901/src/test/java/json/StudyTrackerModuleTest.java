package json;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import studytracker.json.StudyTrackerModule;

public class StudyTrackerModuleTest {

    private static ObjectMapper mapper;

    @BeforeAll
    public static void SetUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new StudyTrackerModule());

    }

    @Test
    public void testSerializers(){
        //skriver senere
    }

    @Test
    public void testDeserializers(){
        //skriver senere
    }

    @Test
    public void testDeserializersSerializers(){
        //skriver senere
    }
}