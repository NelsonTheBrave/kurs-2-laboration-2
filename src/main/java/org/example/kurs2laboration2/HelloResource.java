package org.example.kurs2laboration2;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.ProductDTO;
import org.example.service.Warehouse;
import org.example.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/")
public class HelloResource {

    private static final Logger logger = LoggerFactory.getLogger(HelloResource.class);

    @Inject
    private Warehouse warehouse;
    private final WarehouseService warehouseService = new WarehouseService();

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        List<Product.ProductRecord> allProducts =  warehouseService.performReadOperation(warehouse::getAllProducts);
        logger.info("Returning all products");
        return Response.ok(allProducts).build();
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") int id) {
        logger.info("Returning product with id {}", id);
        return warehouseService.performReadOperation(() -> warehouse.getProductById(id))
                .map(product -> Response.ok(product).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/products/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByCategory(@PathParam("category") String category) {
        Category categoryEnum;
        try {
            categoryEnum = Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid category").build();
        }

        logger.info("Returning products with category {}", categoryEnum);
        List<Product.ProductRecord> products = warehouseService.performReadOperation(() -> warehouse.getProductsByCategory(categoryEnum));
        if (products.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(products).build();
        }
    }

    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(ProductDTO productDTO) {
        logger.info("Adding product with name {}", productDTO.name);
        Product.ProductRecord addedProduct = warehouseService.performWriteOperation(() -> warehouse.addProduct(productDTO.name, productDTO.category, productDTO.rating));
        return Response.status(Response.Status.CREATED).entity(addedProduct).build();
    }
}