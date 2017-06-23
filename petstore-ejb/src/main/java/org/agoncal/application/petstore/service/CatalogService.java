package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Category;
import org.agoncal.application.petstore.model.Item;
import org.agoncal.application.petstore.model.Product;
import org.agoncal.application.petstore.util.Loggable;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Stateless
@Loggable
public class CatalogService implements ICatalogService {

    // ======================================
    // =             Attributes             =
    // ======================================

    @PersistenceContext(unitName = "applicationPetstorePU")
    private EntityManager em;

    // ======================================
    // =              Public Methods        =
    // ======================================

    @Override
    public Category findCategory(@NotNull Long categoryId) {
        return em.find(Category.class, categoryId);
    }

    @Override
    public Category findCategory(@NotNull String categoryName) {
        TypedQuery<Category> typedQuery = em.createNamedQuery(Category.FIND_BY_NAME, Category.class);
        typedQuery.setParameter("pname", categoryName);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Category> findAllCategories() {
        TypedQuery<Category> typedQuery = em.createNamedQuery(Category.FIND_ALL, Category.class);
        return typedQuery.getResultList();
    }

    @Override
    public Category createCategory(@NotNull Category category) {
        em.persist(category);
        return category;
    }

    @Override
    public Category updateCategory(@NotNull Category category) {
        return em.merge(category);
    }

    @Override
    public void removeCategory(@NotNull Category category) {
        em.remove(em.merge(category));
    }

    @Override
    public void removeCategory(@NotNull Long categoryId) {
        removeCategory(findCategory(categoryId));
    }

    @Override
    public List<Product> findProducts(@NotNull String categoryName) {
        TypedQuery<Product> typedQuery = em.createNamedQuery(Product.FIND_BY_CATEGORY_NAME, Product.class);
        typedQuery.setParameter("pname", categoryName);
        return typedQuery.getResultList();
    }

    @Override
    public Product findProduct(@NotNull Long productId) {
        Product product = em.find(Product.class, productId);
        return product;
    }

    @Override
    public List<Product> findAllProducts() {
        TypedQuery<Product> typedQuery = em.createNamedQuery(Product.FIND_ALL, Product.class);
        return typedQuery.getResultList();
    }

    @Override
    public Product createProduct(@NotNull Product product) {
        if (product.getCategory() != null && product.getCategory().getId() == null)
            em.persist(product.getCategory());

        em.persist(product);
        return product;
    }

    @Override
    public Product updateProduct(@NotNull Product product) {
        return em.merge(product);
    }

    @Override
    public void removeProduct(@NotNull Product product) {
        em.remove(em.merge(product));
    }

    @Override
    public void removeProduct(@NotNull Long productId) {
        removeProduct(findProduct(productId));
    }

    @Override
    public List<Item> findItems(@NotNull Long productId) {
        TypedQuery<Item> typedQuery = em.createNamedQuery(Item.FIND_BY_PRODUCT_ID, Item.class);
        typedQuery.setParameter("productId", productId);
        return typedQuery.getResultList();
    }

    @Override
    public Item findItem(@NotNull Long itemId) {
        return em.find(Item.class, itemId);
    }

    @Override
    public List<Item> searchItems(String keyword) {
        if (keyword == null)
            keyword = "";

        TypedQuery<Item> typedQuery = em.createNamedQuery(Item.SEARCH, Item.class);
        typedQuery.setParameter("keyword", "%" + keyword.toUpperCase() + "%");
        return typedQuery.getResultList();
    }

    @Override
    public List<Item> findAllItems() {
        TypedQuery<Item> typedQuery = em.createNamedQuery(Item.FIND_ALL, Item.class);
        return typedQuery.getResultList();
    }

    @Override
    public Item createItem(@NotNull Item item) {
        if (item.getProduct() != null && item.getProduct().getId() == null) {
            em.persist(item.getProduct());
            if (item.getProduct().getCategory() != null && item.getProduct().getCategory().getId() == null)
                em.persist(item.getProduct().getCategory());
        }

        em.persist(item);
        return item;
    }

    @Override
    public Item updateItem(@NotNull Item item) {
        return em.merge(item);
    }

    @Override
    public void removeItem(@NotNull Item item) {
        em.remove(em.merge(item));
    }

    @Override
    public void removeItem(@NotNull Long itemId) {
        removeItem(findItem(itemId));
    }
}
