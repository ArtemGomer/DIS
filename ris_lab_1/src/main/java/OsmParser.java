import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

public class OsmParser {

    private final Logger logger = LogManager.getLogger(OsmParser.class);

    private final String NODE_TAG = "node";
    private final String TAG_TAG = "tag";

    private final QName USER_ATTR = new QName("user");
    private final QName K_ATTR = new QName("k");

    private OsmResult result = null;

    private XMLEventReader reader = null;

    public void parseStream(InputStream stream) throws XMLStreamException {
        result = new OsmResult();
        logger.info("Try to create xml reader");
        reader = createReader(stream);
        logger.info("Xml reader created");
        logger.info("Try to iterate nodes");
        iterateNodes();
        reader.close();
        logger.info("Xml reader closed");
    }

    public OsmResult getResult() {
        return result;
    }

    private void iterateNodes() throws XMLStreamException {
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (startElement.getName().getLocalPart().equals(NODE_TAG)) {
                    processNode(startElement);
                }
            }
        }
        logger.info("All nodes are iterated");
    }

    private void processNode(StartElement startElement) throws XMLStreamException {
        String userName = startElement.getAttributeByName(USER_ATTR).getValue();
        result.addChangeToUser(userName);
        processNodeKeys();
    }

    private void processNodeKeys() throws XMLStreamException {
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart().equals(NODE_TAG)) {
                    return;
                }
            }
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (startElement.getName().getLocalPart().equals(TAG_TAG)) {
                    String key = startElement.getAttributeByName(K_ATTR).getValue();
                    result.addNodeToKey(key);
                }
            }
        }
    }

    private XMLEventReader createReader(InputStream stream) throws XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        return xmlInputFactory.createXMLEventReader(stream);
    }

}
