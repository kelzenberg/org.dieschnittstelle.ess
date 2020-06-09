package org.dieschnittstelle.ess.ejb.ejbmodule.erp;


import org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud.StockItemCRUDLocal;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.StockItem;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.List;

@Singleton
public class StockSystemSingleton implements StockSystemLocal {

    @EJB
    private StockItemCRUDLocal stockItemCRUDLocal;

    @EJB
    private PointOfSaleCRUDLocal pointOfSaleCRUDLocal;

    @Override
    public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        System.out.println("addToStock(): stockItemCRUDLocal: " + stockItemCRUDLocal + " of class: " + stockItemCRUDLocal.getClass());
        System.out.println("addToStock(): pointOfSaleCRUDLocal: " + pointOfSaleCRUDLocal + " of class: " + pointOfSaleCRUDLocal.getClass());
    }

    @Override
    public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        addToStock(product, pointOfSaleId, -units);
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        return null;
    }

    @Override
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        return null;
    }

    @Override
    public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
        return 0;
    }

    @Override
    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
        return 0;
    }

    @Override
    public List<Long> getPointsOfSale(IndividualisedProductItem product) {
        return null;
    }

    @Override
    public List<StockItem> getCompleteStock() {
        throw new UnsupportedOperationException("getCompleteStock() is not supported.");
    }
}
