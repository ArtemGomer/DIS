import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class OsmResult {

    private final Logger logger = LogManager.getLogger(OsmResult.class);

    private final Map<String, Integer> userToChanges = new HashMap<>();
    private final Map<String, Integer> keyToNodes = new HashMap<>();

    public void addChangeToUser(String userName) {
        Integer newValue = userToChanges.getOrDefault(userName, 0) + 1;
        userToChanges.put(userName, newValue);
    }

    public void addNodeToKey(String key) {
        keyToNodes.put(key, keyToNodes.getOrDefault(key, 0) + 1);
    }

    public void printUserToChanges() {
        logger.info("---Node to changes---");
        userToChanges.entrySet().stream()
                .sorted((l, r) -> -(l.getValue() - r.getValue()))
                .forEach(it -> logger.info(it.getKey() + ":" + it.getValue()));
    }

    public void printKeyToNodes() {
        logger.info("---Key to nodes---");
        keyToNodes.entrySet().stream()
                .sorted((l, r) -> -(l.getValue() - r.getValue()))
                .forEach(it -> logger.info(it.getKey() + ":" + it.getValue()));
    }
}
