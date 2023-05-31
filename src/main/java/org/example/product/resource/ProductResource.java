package org.example.product.resource;

import java.util.List;

import org.example.product.entity.ProductEntity;
import org.example.product.request.ProductRequest;
import org.example.product.service.ProductService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/v1/product")
public class ProductResource {

    private ProductService productService = new ProductService();

    /**
     * Retrieves a product and returns it as a JSON response.
     *
     * @param  id  the ID of the product to retrieve
     * @return     a JSON response containing the product
     */
    @GET
    @Path("/{id}/view")
    @Produces(MediaType.APPLICATION_JSON)
    public Response view(@PathParam("id") Integer id) {
        String product = this.productService.getProduct(id);
        return Response.ok(new String(product)).build();
    }

    /**
     * Retrieves a list of ProductEntity objects.
     *
     * @return  a list of ProductEntity objects
     */
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductEntity> list() {
        List<ProductEntity> products = this.productService.listProducts();
        return products;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(ProductRequest productRequest) {
        productService.postProduct(productRequest);
        return Response.ok(new String("Product added!")).build();
    }

    /**
     * Receives a ProductRequest object and adds it to the productService. Returns a Response object 
     * with the message "Product added!".
     *
     * @param  productRequest  the request object containing information about the product to be added
     * @return                 a Response object with the message "Product added!"
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") int id, ProductRequest productRequest) {
        productService.editProduct(id, productRequest);
        return Response.ok(new String("Product edited!")).build();
    }

    /**
     * Deletes a product with the specified ID.
     *
     * @param  id	ID of the product to be removed
     * @return     	Response indicating the success of the operation
     */
    @DELETE
    @Path("/{id}/remove")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") int id) {
        productService.removeProduct(id);
        return Response.ok(new String("Product removed!")).build();
    }
}
