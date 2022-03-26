package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Long>{}

