package com.isolis.api_productos_jdbc.dao;

import com.isolis.api_productos_jdbc.model.Producto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductoJdbcDAO {
    private final JdbcTemplate jdbcTemplate;

    public ProductoJdbcDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Producto> listar() {
        return jdbcTemplate.query("SELECT * FROM productos",
                new BeanPropertyRowMapper<>(Producto.class));
    }

    public Producto obtener(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM productos WHERE id = ?",
                new BeanPropertyRowMapper<>(Producto.class), id);
    }

    public int crear(Producto p) {
        return jdbcTemplate.update("INSERT INTO productos(nombre, precio) VALUES (?, ?)",
                p.getNombre(), p.getPrecio());
    }

    public int actualizar(Long id, Producto p) {
        return jdbcTemplate.update("UPDATE productos SET nombre = ?, precio = ? WHERE id = ?",
                p.getNombre(), p.getPrecio(), id);
    }

    public int eliminar(Long id) {
        return jdbcTemplate.update("DELETE FROM productos WHERE id = ?", id);
    }
}
