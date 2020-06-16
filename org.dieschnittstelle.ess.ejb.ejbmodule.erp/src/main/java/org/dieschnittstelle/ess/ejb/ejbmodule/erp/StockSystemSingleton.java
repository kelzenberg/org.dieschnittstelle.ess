package org.dieschnittstelle.ess.ejb.ejbmodule.erp;


import org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud.PointOfSaleCRUDLocal;
import org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud.StockItemCRUDLocal;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.StockItem;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class StockSystemSingleton implements StockSystemLocal {

    @EJB
    private StockItemCRUDLocal stockItemCRUDLocal;

    @EJB
    private PointOfSaleCRUDLocal pointOfSaleCRUDLocal;

    @Override
    public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        PointOfSale pointOfSale = pointOfSaleCRUDLocal.readPointOfSale(pointOfSaleId);
        StockItem stockItem = stockItemCRUDLocal.readStockItem(product, pointOfSale);
        if (stockItem == null) {
            stockItemCRUDLocal.createStockItem(new StockItem(product, pointOfSale, units));
            return;
        }
        stockItem.setUnits(stockItem.getUnits() + units);
        stockItemCRUDLocal.updateStockItem(stockItem);
    }

    @Override
    public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        addToStock(product, pointOfSaleId, -units);
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        PointOfSale pointOfSale = pointOfSaleCRUDLocal.readPointOfSale(pointOfSaleId);
        List<StockItem> stockItems = stockItemCRUDLocal.readStockItemsForPointOfSale(pointOfSale);
        return stockItems.stream().map(StockItem::getProduct).collect(Collectors.toList());
    }

    @Override
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        List<PointOfSale> pointOfSales = pointOfSaleCRUDLocal.readAllPointsOfSale();
        HashSet<IndividualisedProductItem> productItemsSet = new HashSet<>();

        for (PointOfSale pointOfSale : pointOfSales) {
            productItemsSet.addAll(getProductsOnStock(pointOfSale.getId()));
        }
        return new ArrayList<>(productItemsSet);
    }

    @Override
    public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {
        PointOfSale pointOfSale = pointOfSaleCRUDLocal.readPointOfSale(pointOfSaleId);
        return stockItemCRUDLocal.readStockItem(product, pointOfSale).getUnits();
    }

    @Override
    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
        List<StockItem> stockItems = stockItemCRUDLocal.readStockItemsForProduct(product);
        return stockItems.stream().mapToInt(StockItem::getUnits).sum();
    }

    @Override
    public List<Long> getPointsOfSale(IndividualisedProductItem product) {
        List<StockItem> stockItems = stockItemCRUDLocal.readStockItemsForProduct(product);

        return stockItems.stream().map(stockItem -> stockItem.getPos().getId()).distinct().collect(Collectors.toList());
    }

    @Override
    public List<StockItem> getCompleteStock() {
        throw new UnsupportedOperationException("getCompleteStock() is not supported.");
    }
}
