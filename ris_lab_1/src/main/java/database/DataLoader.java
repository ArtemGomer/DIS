package database;

import database.dao_api.NodeDao;
import database.dao_api.TagDao;
import generated.Node;

import java.util.List;

public class DataLoader {

    private final NodeDao nodeDao;
    private final TagDao tagDao;

    public DataLoader(NodeDao nodeDao, TagDao tagDao) {
        this.nodeDao = nodeDao;
        this.tagDao = tagDao;
    }

    public void addNodeStatement(List<Node> nodes) {
        nodes.forEach(node -> {
            nodeDao.addNodeStatement(node);
            node.getTag().forEach(tagDao::addTagStatement);
        });
    }

    public void addNodePreparedStatement(List<Node> nodes) {
        nodes.forEach(node -> {
            nodeDao.addNodePreparedStatement(node);
            node.getTag().forEach(tagDao::addTagPreparedStatement);
        });
    }

    public void addNodeBatch(List<Node> nodes) {
        nodeDao.addNodeBatch(nodes);
        nodes.forEach(node -> {
            tagDao.addTagBatch(node.getTag());
        });
    }
}
