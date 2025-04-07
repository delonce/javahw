package app.specifications;

import app.models.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    private StudentSpecification() {}

    public static Specification<Student> createSpec(String fullName, Integer age, String email) {
        Specification<Student> spec = Specification.where(null);

        if (fullName != null) spec = spec.and(hasFullName(fullName));
        if (age != null) spec = spec.and(hasAge(age));
        if (email != null) spec = spec.and(hasEmail(email));

        return spec;
    }

    public static Specification<Student> hasFullName(String fullName) {
        return (root, query, builder) ->
                builder.like(root.get("fullName"), "%" + fullName + "%");
    }

    public static Specification<Student> hasAge(Integer age) {
        return (root, query, builder) ->
                builder.equal(root.get("age"), age);
    }

    public static Specification<Student> hasEmail(String email) {
        return (root, query, builder) ->
                builder.like(root.get("email"), "%" + email + "%");
    }
}
