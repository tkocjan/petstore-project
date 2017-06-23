package org.agoncal.application.petstore.service;

import org.agoncal.application.petstore.model.Category;
import org.agoncal.application.petstore.model.Item;
import org.agoncal.application.petstore.model.Product;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Tom on 4/13/2017.
 */
@Remote
public interface ICatalogService {
    Category findCategory(@NotNull Long categoryId);

    Category findCategory(@NotNull String categoryName);

    List<Category> findAllCategories();

    Category createCategory(@NotNull Category category);

    Category updateCategory(@NotNull Category category);

    void removeCategory(@NotNull Category category);

    void removeCategory(@NotNull Long categoryId);

    List<Product> findProducts(@NotNull String categoryName);

    Product findProduct(@NotNull Long productId);

    List<Product> findAllProducts();

    Product createProduct(@NotNull Product product);

    Product updateProduct(@NotNull Product product);

    void removeProduct(@NotNull Product product);

    void removeProduct(@NotNull Long productId);

    List<Item> findItems(@NotNull Long productId);

    Item findItem(@NotNull Long itemId);

    List<Item> searchItems(String keyword);

    List<Item> findAllItems();

    Item createItem(@NotNull Item item);

    Item updateItem(@NotNull Item item);

    void removeItem(@NotNull Item item);

    void removeItem(@NotNull Long itemId);
}
