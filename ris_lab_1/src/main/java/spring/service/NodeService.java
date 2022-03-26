package spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.NodeEntity;
import spring.exception.ObjectNotFoundException;
import spring.repository.NodeRepository;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final NodeRepository nodeRepository;

    @Transactional
    public NodeEntity saveNode(NodeEntity node) {
        return nodeRepository.save(node);
    }

    @Transactional
    public NodeEntity getNode(Long id) {
        return nodeRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Node with id " + id + " not found!")
        );

    }

    @Transactional
    public void deleteNode(Long id) {
        nodeRepository.deleteById(id);
    }

    public Collection<NodeEntity> getNodesInRadius(Double latitude, Double longitude, Double radius) {
        return nodeRepository.getNodesInRadius(latitude, longitude, radius);
    }

}
