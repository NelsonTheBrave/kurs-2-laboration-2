package org.example.kurs2laboration2;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.entities.ProductDTO;
import org.example.service.Warehouse;
import org.example.service.WarehouseService;
import org.example.validation.ValidCategory;
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
        List<Product.ProductRecord> allProducts = warehouseService.performReadOperation(warehouse::getAllProducts);
        logger.info("Returning all products");
        return Response.ok(allProducts).build();
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductById(@PathParam("id") @Positive int id) {
        return warehouseService.performReadOperation(() -> warehouse.getProductById(id))
                .map(product -> {
                    logger.info("Returning product with id {}", id);
                    return Response.ok(product).build();
                })
                .orElseGet(() -> {
                    logger.error("Unable to return product, product with id {} not found", id);
                    return Response.status(Response.Status.NOT_FOUND).build();
                });
    }

    @GET
    @Path("/products/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsByCategory(@PathParam("category") @ValidCategory String category) {
        Category categoryEnum;
        categoryEnum = Category.valueOf(category.toUpperCase());
        List<Product.ProductRecord> products = warehouseService.performReadOperation(() -> warehouse.getProductsByCategory(categoryEnum));
        if (products.isEmpty()) {
            logger.error("Unable to return products, no products found with category {}", categoryEnum);
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
        logger.info("Returning products with category {}", categoryEnum);
            return Response.ok(products).build();
        }
    }

    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(@Valid ProductDTO productDTO) {
        logger.info("Adding product with name {}", productDTO.name);
        Category categoryEnum;
        categoryEnum = Category.valueOf(productDTO.category.toUpperCase());
        Product.ProductRecord addedProduct = warehouseService.performWriteOperation(() -> warehouse.addProduct(productDTO.name, categoryEnum, productDTO.rating));
        return Response.status(Response.Status.CREATED).entity(addedProduct).build();
    }
}