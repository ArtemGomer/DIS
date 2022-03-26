package spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.entity.NodeEntity;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {}
