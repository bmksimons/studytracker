package studytracker.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import studytracker.core.Course;

/**
 * Deserialize a json-file to a Course object.
 */
public class CourseDeserializer extends JsonDeserializer<Course> {

  @Override
  public Course deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return this.deserialize((JsonNode) treeNode);
  }

  /*
   * format: { "courseName": "...", "courseTimer": Double }
   */
  public Course deserialize(JsonNode jsonNode) {
    if (jsonNode instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) jsonNode;
      Course course = new Course();
      JsonNode textNode = objectNode.get("courseName");
      if (textNode instanceof TextNode) {
        course.setCourseName(((TextNode) textNode).asText());
      }
      JsonNode doubleNode = objectNode.get("courseTimer");
      if (doubleNode instanceof DoubleNode) {
        course.addTime(((DoubleNode) doubleNode).asDouble(0.0));
      }
      return course;
    }
    return null;
  }
}