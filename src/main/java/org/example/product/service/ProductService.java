package org.example.product.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.example.product.entity.ProductEntity;
import org.example.product.request.ProductRequest;
import org.example.product.response.ProductResponse;

import java.util.List;
import jakarta.ws.rs.core.Response;

public class ProductService {

    public String getProduct(int id) {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("jax-rs-sample")
                .createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ProductEntity existingProductEntity = entityManager.find(ProductEntity.class, id);
            if (existingProductEntity == null) {
                return "No products found";
            }
            ProductResponse productResponse = new ProductResponse();
            productResponse.setMessage(new StringBuilder()
                    .append("Product: ")
                    .append(existingProductEntity.getMessage())
                    .toString());
            transaction.commit();
            return productResponse.getMessage();
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    public List<ProductEntity> listProducts() {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("jax-rs-sample")
                .createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            List<ProductEntity> products = entityManager.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class).getResultList();
            transaction.commit();
            return products;
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }

    public Response postProduct(ProductRequest productRequest) {
        ProductEntity productEntity = new ProductEntity();
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("jax-rs-sample")
                .createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            productEntity.setMessage(productRequest.getMessage());
            entityManager.persist(productEntity);
            transaction.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.clear();
            entityManager.close();
        }

        return Response.ok(new String("Product added!")).build();
    }

    public Response editProduct(int id, ProductRequest productRequest) {
        EntityManager entityManager = Persistence
            .createEntityManagerFactory("jax-rs-sample")
            .createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ProductEntity existingProductEntity = entityManager.find(ProductEntity.class, id);
            if (existingProductEntity == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingProductEntity.setMessage(productRequest.getMessage());
            transaction.commit();
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.clear();
            entityManager.close();
        }

        return Response.ok(new String("Product edited!")).build();
    }

    public Response removeProduct(int id) {
        EntityManager entityManager = Persistence
            .createEntityManagerFactory("jax-rs-sample")
            .createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            ProductEntity existingProduct = entityManager.find(ProductEntity.class, id);
            if (existingProduct == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            entityManager.remove(existingProduct);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.clear();
            entityManager.close();
        }

        return Response.ok(new String("Product removed!")).build();
    }
}
