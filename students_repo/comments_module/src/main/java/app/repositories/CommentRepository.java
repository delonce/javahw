package app.repositories;

import app.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByGradeId(Integer id);

    List<Comment> findByStudentId(Integer id);
}