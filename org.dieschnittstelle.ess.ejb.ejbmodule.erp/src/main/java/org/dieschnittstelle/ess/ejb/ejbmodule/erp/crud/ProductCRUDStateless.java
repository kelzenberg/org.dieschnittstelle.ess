package org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud;

import org.dieschnittstelle.ess.entities.erp.AbstractProduct;

import javax.ejb.Stateless;
import java.util.*;

@Stateless
public class ProductCRUDStateless implements ProductCRUDRemote {
    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        return prod;
    }

    @Override
    public List<AbstractProduct> readAllProducts() {
        return new ArrayList<>();
    }

    @Override
    public AbstractProduct updateProduct(AbstractProduct update) {
        return null;
    }

    @Override
    public AbstractProduct readProduct(long productID) {
        return null;
    }

    @Override
    public boolean deleteProduct(long productID) {
        return false;
    }
}
