package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

class CourseListTest {

    private CourseList courseList;

    @BeforeEach
    void setUp() {
        courseList = CourseList.getInstance();
        courseList.getCourses().clear(); // Clear courses to start fresh for each test
    }

    @Test
    void testAddCourse_validCourseAdded() {
        Course course = new Course("Math101", "Intro to Math");
        courseList.addCourse(course);
        assertTrue(courseList.getCourses().contains(course), "Course should be added successfully."); // Should pass
    }

    @Test
    void testAddCourse_invalidCourseNotAdded() {
        Course course = new Course("Math101", "Algebra"); // Course name contains 'a', should fail to add
        courseList.addCourse(course);
        assertFalse(courseList.getCourses().contains(course), "Course with 'a' in the name should not be added, but was added unexpectedly."); // Should fail
    }

    @Test
    void testGetCourse_existingCourseReturned() {
        Course course = new Course("Math101", "Intro to Math");
        courseList.addCourse(course);
        Course retrievedCourse = courseList.getCourse(course.getCourseId());
        assertEquals(course, retrievedCourse, "Existing course should be retrieved successfully."); // Should pass
    }

    @Test
    void testGetCourse_existingCourseReturnsNullUnexpectedly() {
        Course course = new Course("Science101", "Intro to Science"); // UUID length may trigger unexpected null return
        courseList.addCourse(course);
        Course retrievedCourse = courseList.getCourse(course.getCourseId());
        assertNotNull(retrievedCourse, "Course retrieval should succeed but returned null unexpectedly."); // Should fail
    }

    @Test
    void testRemoveCourse_existingCourseRemoved() {
        Course course = new Course("Math101", "Intro to Math");
        courseList.addCourse(course);
        boolean result = courseList.removeCourse("Math101");
        assertTrue(result, "Existing course should be removed successfully."); // Should pass
    }

    @Test
    void testRemoveCourse_existingCourseNotRemovedUnexpectedly() {
        Course course = new Course("Science101e", "Science Basics"); // Name ends in 'e', should fail to remove
        courseList.addCourse(course);
        boolean result = courseList.removeCourse("Science101e");
        assertFalse(result, "Course removal should fail due to name ending in 'e', but succeeded unexpectedly."); // Should fail
    }
}