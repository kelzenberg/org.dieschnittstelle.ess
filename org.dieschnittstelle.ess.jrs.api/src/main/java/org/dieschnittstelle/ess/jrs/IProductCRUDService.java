package org.dieschnittstelle.ess.jrs;

import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/*
 * UE JRS2:
 * deklarieren Sie hier Methoden fuer:
 * - die Erstellung eines Produkts
 * - das Auslesen aller Produkte
 * - das Auslesen eines Produkts
 * - die Aktualisierung eines Produkts
 * - das Loeschen eines Produkts
 * und machen Sie diese Methoden mittels JAX-RS Annotationen als WebService verfuegbar
 */

/*
 * TODO JRS3: aendern Sie Argument- und Rueckgabetypen der Methoden von IndividualisedProductItem auf AbstractProduct
 */
@Path("/products")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface IProductCRUDService {

    @GET
    public List<IndividualisedProductItem> readAllProducts();

    @GET
    @Path("/{id}")
    public IndividualisedProductItem readProduct(@PathParam("id") long id);

    @POST
    public IndividualisedProductItem createProduct(IndividualisedProductItem prod);

    @PUT
    @Path("/{id}")
    public IndividualisedProductItem updateProduct(@PathParam("id") long id,
                                                   IndividualisedProductItem update);

    @DELETE
    @Path("/{id}")
    boolean deleteProduct(@PathParam("id") long id);

}
