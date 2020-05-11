package org.dieschnittstelle.ess.jrs;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

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
    private GenericCRUDExecutor<IndividualisedProductItem> productCRUD;

    /**
     * here we will be passed the context parameters by the resteasy framework
     *
     * @param servletContext
     */
    public ProductCRUDServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
        logger.info("<constructor>: " + servletContext + "/" + request);
        // read out the dataAccessorS
        this.productCRUD = (GenericCRUDExecutor<IndividualisedProductItem>) servletContext.getAttribute("productCRUD");

        logger.debug("read out the productCRUD from the servlet context: " + this.productCRUD);
    }

    @Override
    public List<IndividualisedProductItem> readAllProducts() {
        return this.productCRUD.readAllObjects();
    }

    @Override
    public IndividualisedProductItem readProduct(long id) {
        return this.productCRUD.readObject(id);
    }

    @Override
    public IndividualisedProductItem createProduct(
            IndividualisedProductItem prod) {
        return this.productCRUD.createObject(prod);
    }

    @Override
    public IndividualisedProductItem updateProduct(long id,
                                                   IndividualisedProductItem update) {
        return this.productCRUD.updateObject(update);
    }

    @Override
    public boolean deleteProduct(long id) {
        return this.productCRUD.deleteObject(id);
    }
}
