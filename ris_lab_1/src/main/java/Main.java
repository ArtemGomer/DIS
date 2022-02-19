import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try (
                InputStream fileInputStream = Main.class.getClassLoader().getResourceAsStream("RU-NVS.osm.bz2");
                InputStream bZip2CompressorInputStream = new BZip2CompressorInputStream(fileInputStream)
        ) {
            logger.info("Create parser");
            OsmParser parser = new OsmParser();
            logger.info("Try to parse stream");
            parser.parseStream(bZip2CompressorInputStream);
            OsmResult result = parser.getResult();
            logger.info("Get parse result");
            result.printUserToChanges();
            result.printKeyToNodes();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
