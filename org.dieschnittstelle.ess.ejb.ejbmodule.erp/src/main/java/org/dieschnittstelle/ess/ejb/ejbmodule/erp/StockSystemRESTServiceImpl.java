package org.dieschnittstelle.ess.ejb.ejbmodule.erp;

import org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud.ProductCRUDRemote;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.List;

@Remote(StockSystemRESTService.class)
@Stateless
public class StockSystemRESTServiceImpl implements StockSystemRESTService {

    @EJB
    private StockSystemLocal stockSystemLocal;

    @EJB
    private ProductCRUDRemote productCRUDRemote;

    @Override
    public void addToStock(long productId, long pointOfSaleId, int units) {
        AbstractProduct product = productCRUDRemote.readProduct(productId);
        stockSystemLocal.addToStock((IndividualisedProductItem) product, pointOfSaleId, units);
    }

    @Override
    public void removeFromStock(long productId, long pointOfSaleId, int units) {
        AbstractProduct product = productCRUDRemote.readProduct(productId);
        stockSystemLocal.removeFromStock((IndividualisedProductItem) product, pointOfSaleId, units);
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        System.out.println("pointOfSaleId: " + pointOfSaleId);

        return pointOfSaleId != -1
                ? stockSystemLocal.getProductsOnStock(pointOfSaleId)
                : getAllProductsOnStock();
    }

    public List<IndividualisedProductItem> getAllProductsOnStock() {
        return stockSystemLocal.getAllProductsOnStock();
    }

    @Override
    public int getUnitsOnStock(long productId, long pointOfSaleId) {
        AbstractProduct product = productCRUDRemote.readProduct(productId);
        return pointOfSaleId != -1
                ? stockSystemLocal.getUnitsOnStock((IndividualisedProductItem) product, pointOfSaleId)
                : getTotalUnitsOnStock(product);
    }

    public int getTotalUnitsOnStock(AbstractProduct product) {
        return stockSystemLocal.getTotalUnitsOnStock((IndividualisedProductItem) product);
    }

    @Override
    public List<Long> getPointsOfSale(long productId) {
        return null;
    }
}
