import database.DataLoader;
import database.DatabaseManager;
import database.dao_api.NodeDao;
import database.dao_api.TagDao;
import database.dao_impl.NodeDaoImpl;
import database.dao_impl.TagDaoImpl;
import generated.Node;
import generated.Osm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import osm.OsmParser;

import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            OsmParser parser = new OsmParser();

            DatabaseManager databaseManager = DatabaseManager.getInstance();
            databaseManager.connect();

            NodeDao nodeDao = new NodeDaoImpl(databaseManager.getConnection());
            TagDao tagDao = new TagDaoImpl(databaseManager.getConnection());

            DataLoader dataLoader = new DataLoader(nodeDao, tagDao);

            Osm osm = parser.parseStream("RU-NVS.osm.bz2");
            List<Node> nodes = osm.getNode();

            databaseManager.createSchema();
            long time1 = System.currentTimeMillis();
            dataLoader.addNodeStatement(nodes);
            time1 = System.currentTimeMillis() - time1;

            databaseManager.createSchema();
            long time2 = System.currentTimeMillis();
            dataLoader.addNodePreparedStatement(nodes);
            time2 = System.currentTimeMillis() - time2;

            databaseManager.createSchema();
            long time3 = System.currentTimeMillis();
            dataLoader.addNodeBatch(nodes);
            time3 = System.currentTimeMillis() - time3;

            databaseManager.close();

            logger.info("Statement: " + time1);
            logger.info("Prepared statement: " + time2);
            logger.info("Batch: " + time3);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
