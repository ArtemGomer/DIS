import generated.Osm;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.io.InputStream;

public class OsmParser {

    private final static Logger logger = LogManager.getLogger(OsmParser.class);

    private final Unmarshaller unmarshaller;

    public OsmParser() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Osm.class);
        unmarshaller = context.createUnmarshaller();
    }

    public OsmResult parseStream(String fileName) throws IOException, JAXBException, SAXException {
        InputStream fileInputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        BZip2CompressorInputStream inputStream = new BZip2CompressorInputStream(fileInputStream);
        XMLReader reader = XMLReaderFactory.createXMLReader();
        NamespaceFilter filter = new NamespaceFilter("http://openstreetmap.org/osm/0.6", true);
        filter.setParent(reader);
        InputSource source = new InputSource(inputStream);
        SAXSource saxSource = new SAXSource(filter, source);
        logger.info("Try to unmarshall osm");
        Osm osm = (Osm) unmarshaller.unmarshal(saxSource);
        logger.info("Process result");
        return processOsm(osm);
    }

    private OsmResult processOsm(Osm osm) {
        OsmResult result = new OsmResult();
        osm.getNode().forEach(node -> {
            result.addChangeToUser(node.getUser());
            node.getTag().forEach(tag -> result.addNodeToKey(tag.getK()));
        });
        return result;
    }

}
