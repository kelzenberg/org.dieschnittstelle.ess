package org.dieschnittstelle.ess.ejb.ejbmodule.erp.crud;

import org.dieschnittstelle.ess.entities.erp.AbstractProduct;

import javax.ejb.Remote;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/*
 * TODO EJB+JPA1/2/5:
 * this interface shall be implemented using a stateless EJB with an EntityManager.
 * See TouchpointCRUDStateless for an example EJB with a similar scope of functionality
 */

@Remote
@Path("/products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface ProductCRUDRemote {
    @POST
    public AbstractProduct createProduct(AbstractProduct prod);

    @GET
    public List<AbstractProduct> readAllProducts();

    @PUT
    @Path("{id}")
    public AbstractProduct updateProduct(@PathParam("id") AbstractProduct update);

    @GET
    @Path("{id}")
    public AbstractProduct readProduct(@PathParam("id") long productID);

    @DELETE
    @Path("{id}")
    public boolean deleteProduct(@PathParam("id") long productID);

}
