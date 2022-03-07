package database.dao_api;

import generated.Node;

import java.sql.SQLException;
import java.util.List;

public interface NodeDao {

    boolean addNodeStatement(Node node);
    boolean addNodePreparedStatement(Node node);
    boolean addNodeBatch(List<Node> nodes);

}
