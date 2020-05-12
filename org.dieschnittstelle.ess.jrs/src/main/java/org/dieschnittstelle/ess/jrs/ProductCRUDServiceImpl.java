package org.dieschnittstelle.ess.jrs;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/*
 * TODO JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDServiceImpl implements IProductCRUDService {

    protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(TouchpointCRUDServiceImpl.class);

    /**
     * this accessor will be provided by the ServletContext
     */
    private GenericCRUDExecutor<AbstractProduct> productCRUD;

    /**
     * here we will be passed the context parameters by the resteasy framework
     *
     * @param servletContext
     */
    public ProductCRUDServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
        logger.info("<constructor>: " + servletContext + "/" + request);
        // read out the dataAccessorS
        this.productCRUD = (GenericCRUDExecutor<AbstractProduct>) servletContext.getAttribute("productCRUD");

        logger.debug("read out the productCRUD from the servlet context: " + this.productCRUD);
    }

    @Override
    public List<AbstractProduct> readAllProducts() {
        return this.productCRUD.readAllObjects();
    }

    @Override
    public AbstractProduct readProduct(long id) {
        return this.productCRUD.readObject(id);
    }

    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        return this.productCRUD.createObject(prod);
    }

    @Override
    public AbstractProduct updateProduct(long id, AbstractProduct update) {
        return this.productCRUD.updateObject(update);
    }

    @Override
    public boolean deleteProduct(long id) {
        return this.productCRUD.deleteObject(id);
    }
}
