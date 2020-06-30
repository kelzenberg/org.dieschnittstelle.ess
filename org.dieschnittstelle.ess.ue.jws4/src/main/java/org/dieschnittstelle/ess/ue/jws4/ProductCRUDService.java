package org.dieschnittstelle.ess.ue.jws4;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;

/*
 * TODO JWS4: machen Sie die Funktionalitaet dieser Klasse als Web Service verfuegbar und verwenden Sie fuer
 *  die Umsetzung der Methoden die Instanz von GenericCRUDExecutor<AbstractProduct>,
 *  die Sie aus dem ServletContext auslesen koennen
 */
@WebService(targetNamespace = "http://dieschnittstelle.org/ess/jws", name = "IProductCRUDService", serviceName = "ProductCRUDWebService", portName = "ProductCRUDPort")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class ProductCRUDService {

    protected static Logger logger = org.apache.logging.log4j.LogManager
            .getLogger(ProductCRUDService.class);

    @Resource
    private WebServiceContext wscontext;

    private GenericCRUDExecutor<AbstractProduct> getProductCRUD() {
        return (GenericCRUDExecutor<AbstractProduct>) ((ServletContext) wscontext
                .getMessageContext().get(MessageContext.SERVLET_CONTEXT))
                .getAttribute("productCRUD");
    }

    @PostConstruct
    @WebMethod(exclude = true)
    public void initialiseContext() {
        logger.info("@PostConstruct: the wscontext is: " + wscontext);

        // we cannot read out any context attributes (ServletContext,
        // HttpServletRequest) from the WebServiceContext as this is only
        // allowed from a thread that actually handles a particular request to a
        // service operation
        // wscontext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
    }

    @WebMethod
    public List<AbstractProduct> readAllProducts() {
        logger.info("readAllProducts()");
        logger.info("readAllProducts(): I am: " + this);


        ServletContext ctx = (ServletContext) wscontext.getMessageContext()
                .get(MessageContext.SERVLET_CONTEXT);
        logger.info("readAllProducts(): servlet context is: " + ctx);

        HttpServletRequest httpRequest = (HttpServletRequest) wscontext
                .getMessageContext().get(MessageContext.SERVLET_REQUEST);
        logger.info("readAllProducts(): servlet request is: " + httpRequest);

        logger.info("readAllProducts(): read productCRUD from servletContext: "
                + getProductCRUD());

        return getProductCRUD().readAllObjects();
    }

    @WebMethod
    public AbstractProduct createProduct(AbstractProduct product) {
        logger.info("createProduct(): " + product);
        return getProductCRUD().createObject(product);
    }

    @WebMethod
    public AbstractProduct updateProduct(AbstractProduct product) {
        logger.info("updateProduct(): " + product);
        return getProductCRUD().updateObject(product);
    }

    @WebMethod
    public boolean deleteProduct(long id) {
        logger.info("deleteProduct(): " + id);
        return getProductCRUD().deleteObject(id);
    }

    @WebMethod
    public AbstractProduct readProduct(long id) {
        logger.info("readProduct(): " + id);
        return getProductCRUD().readObject(id);
    }

}
