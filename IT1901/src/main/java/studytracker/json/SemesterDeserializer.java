package studytracker.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import studytracker.core.Course;
import studytracker.core.Semester;

public class SemesterDeserializer extends JsonDeserializer<Semester> {

    private CourseDeserializer courseDeserializer = new CourseDeserializer();
    private Semester semester;

    @Override
    public Semester deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        if (treeNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) treeNode;
            this.semester = new Semester();
            JsonNode itemsNode = objectNode.get("courses");
            if (itemsNode instanceof ArrayNode) {
                for (JsonNode elementNode : ((ArrayNode) itemsNode)){
                    Course course = this.courseDeserializer.deserialize(elementNode);
                    if (course != null) {
                        this.semester.addCourse(course);
                    }

                }
            }
            return this.semester;
        }
        return null;
    }
}