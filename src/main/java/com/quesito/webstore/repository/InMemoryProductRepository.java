package com.quesito.webstore.repository;

import com.quesito.webstore.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class InMemoryProductRepository implements ProductRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Product> getProducts() {
        return jdbcTemplate.query("SELECT * FROM products", (rs, rowNum) -> mapRowToProduct(rs));
    }

    @Override
    public void updateStock(String productId, long noOfUnits) {
        String SQL = "UPDATE PRODUCTS SET UNITS_IN_STOCK = :unitsInStock WHERE ID = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("unitsInStock", noOfUnits);
        params.put("id", productId);
        jdbcTemplate.update(SQL, params);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        String SQL = "SELECT * FROM PRODUCTS WHERE UPPER(CATEGORY)=:category ";
        Map<String,Object> params=new HashMap<>();
        params.put("category",category);
        return jdbcTemplate.query(SQL,params,
                (rs, rowNum) -> mapRowToProduct(rs));
    }


    @Override
    public List<Product> getProductsByFilter(Map<String,
            List<String>> filterParams) {
        String SQL = "SELECT * FROM PRODUCTS WHERE CATEGORY IN (:categories ) AND MANUFACTURER IN ( :brands)";
        return jdbcTemplate.query(SQL, filterParams, (rs, rowNum) -> mapRowToProduct(rs));
    }

    @Override
    public Product getProductById(String productID) {
        String SQL="SELECT * FROM PRODUCTS WHERE ID = :id";
        Map<String,Object> params=new HashMap<>();
        params.put("id",productID);
        return jdbcTemplate.queryForObject(SQL,params,(rs, rowNum) -> mapRowToProduct(rs));
    }

    @Override
    public void addProduct(Product product) {
        String SQL = "INSERT INTO PRODUCTS (ID, "
                + "NAME,"
                + "DESCRIPTION,"
                + "UNIT_PRICE,"
                + "MANUFACTURER,"
                + "CATEGORY,"
                + "CONDITION,"
                + "UNITS_IN_STOCK,"
                + "UNITS_IN_ORDER,"
                + "DISCONTINUED) "
                + "VALUES (:id, :name, :desc, :price,:manufacturer, :category, :condition, :inStock,:inOrder, :discontinued)";

        Map<String,Object> params=new HashMap<>();
        params.put("id",product.getProductId());
        params.put("name",product.getName());
        params.put("desc",product.getDescription());
        params.put("price",product.getUnitPrice());
        params.put("manufacturer",product.getManufacturer());
        params.put("category",product.getCategory());
        params.put("condition",product.getCondition());
        params.put("inStock",product.getUnitsInStock());
        params.put("inOrder",product.getUnitsInOrder());
        params.put("discontinued",product.isDiscontinued());
        jdbcTemplate.update(SQL,params);

    }


    public Product mapRowToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getString("ID"));
        product.setName(rs.getString("NAME"));
        product.setName(rs.getString("NAME"));
        product.setDescription(rs.getString("DESCRIPTION"));
        product.setUnitPrice(rs.getBigDecimal("UNIT_PRICE"));
        product.setManufacturer(rs.getString("MANUFACTURER"));
        product.setCategory(rs.getString("CATEGORY"));
        product.setCondition(rs.getString("CONDITION"));
        product.setUnitsInStock(rs.getLong("UNITS_IN_STOCK"));
        product.setUnitsInOrder(rs.getLong("UNITS_IN_ORDER"));
        product.setDiscontinued(rs.getBoolean("DISCONTINUED"));
        return product;

    }
}
