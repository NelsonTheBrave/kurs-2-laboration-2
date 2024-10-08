package org.example.kurs2laboration2;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.entities.Product;
import org.example.entities.ProductDTO;
import org.example.service.Warehouse;
import org.example.service.WarehouseService;

import java.util.List;

@Path("/")
public class HelloResource {
    @Inject
    private Warehouse warehouse;
    private final WarehouseService warehouseService = new WarehouseService();

    @GET
    @Path("/products")
    @Produces("text/plain")
    public List getAllProducts() {
        List<Product.ProductRecord> productList = warehouseService.performReadOperation(warehouse::getAllProducts);
        return productList;
    }

    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addProduct(ProductDTO productDTO) {
        warehouseService.performWriteOperation(() -> warehouse.addProduct(productDTO.name, productDTO.category, productDTO.rating));
        List<Product.ProductRecord> productList = warehouseService.performReadOperation(warehouse::getAllProducts);
        productList.forEach(System.out::println);
        return productDTO.name + " added to warehouse";
    }
}