package com.zomato.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_item")
@IdClass(OrderItemId.class)
public class OrderItem {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private MenuItem menuItem;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "price", nullable = false)
    private Double price;
    
    // Default constructor
    public OrderItem() {}
    
    // Constructor with fields
    public OrderItem(Orders order, MenuItem menuItem, Integer quantity, Double price) {
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters
    public Orders getOrder() { return order; }
    public MenuItem getMenuItem() { return menuItem; }
    public Integer getQuantity() { return quantity; }
    public Double getPrice() { return price; }
    
    // Setters
    public void setOrder(Orders order) { this.order = order; }
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setPrice(Double price) { this.price = price; }

	@Override
	public String toString() {
		return "OrderItem [order=" + order + ", menuItem=" + menuItem + ", quantity=" + quantity + ", price=" + price
				+ "]";
	}
}


// Composite Key Class
@Embeddable
class OrderItemId implements Serializable {
    private Long order;
    private Long menuItem;
    
    public OrderItemId() {}
    
    public OrderItemId(Long order, Long menuItem) {
        this.order = order;
        this.menuItem = menuItem;
    }
    
    public Long getOrder() { return order; }
    public void setOrder(Long order) { this.order = order; }
    public Long getMenuItem() { return menuItem; }
    public void setMenuItem(Long menuItem) { this.menuItem = menuItem; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(order, that.order) && 
               Objects.equals(menuItem, that.menuItem);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(order, menuItem);
    }
}

