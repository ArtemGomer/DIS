import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            logger.info("Create parser");
            OsmParser parser = new OsmParser();
            logger.info("Try to parse stream");
            OsmResult result = parser.parseStream("RU-NVS.osm.bz2");
            logger.info("Get parse result");
            result.printUserToChanges();
            result.printKeyToNodes();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
