
package testservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.4.1-hudson-346-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "TestService", targetNamespace = "urn:TestService", wsdlLocation = "file:/C:/workspaces/workspace1/TestService/src/main/webapp/WEB-INF/wsdl/TestService.wsdl")
public class TestService
    extends Service
{

    private final static URL TESTSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(testservice.TestService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = testservice.TestService.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/workspaces/workspace1/TestService/src/main/webapp/WEB-INF/wsdl/TestService.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/workspaces/workspace1/TestService/src/main/webapp/WEB-INF/wsdl/TestService.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        TESTSERVICE_WSDL_LOCATION = url;
    }

    public TestService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TestService() {
        super(TESTSERVICE_WSDL_LOCATION, new QName("urn:TestService", "TestService"));
    }

    /**
     * 
     * @return
     *     returns TestServicePortType
     */
    @WebEndpoint(name = "TestService_v1.0_Port")
    public TestServicePortType getTestServiceV10Port() {
        return super.getPort(new QName("urn:TestService", "TestService_v1.0_Port"), TestServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TestServicePortType
     */
    @WebEndpoint(name = "TestService_v1.0_Port")
    public TestServicePortType getTestServiceV10Port(WebServiceFeature... features) {
        return super.getPort(new QName("urn:TestService", "TestService_v1.0_Port"), TestServicePortType.class, features);
    }

}