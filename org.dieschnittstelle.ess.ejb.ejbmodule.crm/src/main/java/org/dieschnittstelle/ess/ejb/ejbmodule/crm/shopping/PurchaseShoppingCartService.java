package org.dieschnittstelle.ess.ejb.ejbmodule.crm.shopping;

import org.dieschnittstelle.ess.ejb.ejbmodule.crm.ShoppingException;

import javax.ejb.Remote;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// TODO: PAT1: this is the interface to be provided as a rest service if rest service access is used
@Remote
@Path("/purchase")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface PurchaseShoppingCartService {

    @POST
    public void purchase(
            @QueryParam("shoppingCartId") long shoppingCartId,
            @QueryParam("touchpointId") long touchpointId,
            @QueryParam("customerId") long customerId
    ) throws ShoppingException;

}
