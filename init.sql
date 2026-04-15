-- ==========================================
-- ZOMATO DATABASE INITIALIZATION SCRIPT
-- Complete with all current records
-- ==========================================

-- Drop existing tables if they exist (for clean setup)
DROP TABLE IF EXISTS order_item CASCADE;
DROP TABLE IF EXISTS payment CASCADE;
DROP TABLE IF EXISTS review CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS delivery_person CASCADE;
DROP TABLE IF EXISTS menu_item CASCADE;
DROP TABLE IF EXISTS menu CASCADE;
DROP TABLE IF EXISTS restaurant_category CASCADE;
DROP TABLE IF EXISTS restaurant CASCADE;
DROP TABLE IF EXISTS category CASCADE;

-- ==========================================
-- 1. CATEGORY TABLE
-- ==========================================
CREATE TABLE category (
    category_id BIGSERIAL PRIMARY KEY,
    category_name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(200),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 2. RESTAURANT TABLE
-- ==========================================
CREATE TABLE restaurant (
    restaurant_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(100),
    city VARCHAR(50),
    area VARCHAR(50),
    rating DECIMAL(3,2),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 3. RESTAURANT_CATEGORY (Junction Table)
-- ==========================================
CREATE TABLE restaurant_category (
    restaurant_id BIGINT REFERENCES restaurant(restaurant_id) ON DELETE CASCADE,
    category_id BIGINT REFERENCES category(category_id) ON DELETE CASCADE,
    PRIMARY KEY (restaurant_id, category_id)
);

-- ==========================================
-- 4. MENU TABLE
-- ==========================================
CREATE TABLE menu (
    menu_id BIGSERIAL PRIMARY KEY,
    restaurant_id BIGINT NOT NULL REFERENCES restaurant(restaurant_id) ON DELETE CASCADE,
    menu_name VARCHAR(100) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 5. MENU_ITEM TABLE
-- ==========================================
CREATE TABLE menu_item (
    item_id BIGSERIAL PRIMARY KEY,
    menu_id BIGINT NOT NULL REFERENCES menu(menu_id) ON DELETE CASCADE,
    item_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    available_status VARCHAR(3) DEFAULT 'YES' CHECK (available_status IN ('YES', 'NO')),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 6. CUSTOMER TABLE
-- ==========================================
CREATE TABLE customer (
    customer_id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) UNIQUE,
    email VARCHAR(100) UNIQUE,
    gender CHAR(1) CHECK (gender IN ('M', 'F', 'O')),
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 7. ADDRESS TABLE
-- ==========================================
CREATE TABLE address (
    address_id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customer(customer_id) ON DELETE CASCADE,
    house_no VARCHAR(20),
    street VARCHAR(100),
    city VARCHAR(50),
    pincode VARCHAR(10),
    address_type VARCHAR(10) CHECK (address_type IN ('HOME', 'OFFICE', 'OTHER'))
);

-- ==========================================
-- 8. DELIVERY_PERSON TABLE
-- ==========================================
CREATE TABLE delivery_person (
    delivery_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) UNIQUE,
    vehicle_no VARCHAR(20),
    status VARCHAR(10) DEFAULT 'AVAILABLE' CHECK (status IN ('AVAILABLE', 'BUSY'))
);

-- ==========================================
-- 9. ORDERS TABLE
-- ==========================================
CREATE TABLE orders (
    order_id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customer(customer_id),
    restaurant_id BIGINT NOT NULL REFERENCES restaurant(restaurant_id),
    delivery_id BIGINT REFERENCES delivery_person(delivery_id),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) CHECK (total_amount >= 0),
    order_status VARCHAR(20) DEFAULT 'PLACED' CHECK (order_status IN ('PLACED', 'ONWAY', 'DELIVERED', 'CANCELLED'))
);

-- ==========================================
-- 10. ORDER_ITEM TABLE
-- ==========================================
CREATE TABLE order_item (
    order_id BIGINT REFERENCES orders(order_id) ON DELETE CASCADE,
    item_id BIGINT REFERENCES menu_item(item_id),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    PRIMARY KEY (order_id, item_id)
);

-- ==========================================
-- 11. PAYMENT TABLE
-- ==========================================
CREATE TABLE payment (
    payment_id BIGSERIAL PRIMARY KEY,
    order_id BIGINT UNIQUE NOT NULL REFERENCES orders(order_id) ON DELETE CASCADE,
    payment_mode VARCHAR(10) NOT NULL CHECK (payment_mode IN ('CARD', 'CASH', 'UPI')),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10,2) NOT NULL CHECK (amount >= 0),
    payment_status VARCHAR(10) DEFAULT 'PENDING' CHECK (payment_status IN ('SUCCESS', 'FAILED', 'PENDING'))
);

-- ==========================================
-- 12. REVIEW TABLE
-- ==========================================
CREATE TABLE review (
    review_id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customer(customer_id),
    restaurant_id BIGINT NOT NULL REFERENCES restaurant(restaurant_id),
    rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comments VARCHAR(500),
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- INSERT SAMPLE DATA
-- ==========================================

-- ==========================================
-- 1. INSERT CATEGORIES
-- ==========================================
INSERT INTO category (category_id, category_name, description) VALUES 
(1, 'Italian', 'Delicious Italian cuisine with pasta, pizza and more'),
(2, 'Chinese', 'Authentic Chinese dishes'),
(3, 'Indian', 'Traditional Indian cuisine with rich flavors'),
(4, 'Mexican', 'Spicy Mexican delicacies'),
(5, 'Japanese', 'Sushi and Japanese specialties'),
(6, 'Thai', 'Thai cuisine with exotic flavors')
ON CONFLICT (category_id) DO NOTHING;

-- Reset sequence
SELECT setval('category_category_id_seq', (SELECT MAX(category_id) FROM category));

-- ==========================================
-- 2. INSERT RESTAURANTS
-- ==========================================
INSERT INTO restaurant (restaurant_id, name, phone, email, city, area, rating) VALUES 
(1, 'Spice Garden Premium', '9876543211', 'spicepremium@example.com', 'Mumbai', 'Andheri West', 4.5),
(2, 'Pizza Paradise', '9876543211', 'pizza@example.com', 'Delhi', 'Connaught Place', 4.3),
(3, 'Sushi House', '9876543212', 'sushi@example.com', 'Bangalore', 'Indiranagar', 4.7),
(4, 'Thai Delight', '9876543213', 'thai@example.com', 'Mumbai', 'Bandra', 4.4),
(5, 'Curry House', '9876543214', 'curry@example.com', 'Delhi', 'Rajouri Garden', 4.2)
ON CONFLICT (restaurant_id) DO NOTHING;

-- Reset sequence
SELECT setval('restaurant_restaurant_id_seq', (SELECT MAX(restaurant_id) FROM restaurant));

-- ==========================================
-- 3. INSERT RESTAURANT-CATEGORY RELATIONSHIPS
-- ==========================================
INSERT INTO restaurant_category (restaurant_id, category_id) VALUES 
(1, 1),  -- Spice Garden Premium -> Italian
(1, 2),  -- Spice Garden Premium -> Chinese
(1, 3),  -- Spice Garden Premium -> Indian
(2, 1),  -- Pizza Paradise -> Italian
(3, 5),  -- Sushi House -> Japanese
(4, 6),  -- Thai Delight -> Thai
(5, 3)   -- Curry House -> Indian
ON CONFLICT (restaurant_id, category_id) DO NOTHING;

-- ==========================================
-- 4. INSERT MENUS
-- ==========================================
INSERT INTO menu (menu_id, restaurant_id, menu_name) VALUES 
(1, 1, 'Lunch Special'),
(2, 1, 'Dinner Delight'),
(3, 1, 'Weekend Brunch'),
(4, 2, 'Pizza Menu'),
(5, 2, 'Pasta Menu'),
(6, 2, 'Family Combo'),
(7, 3, 'Sushi Menu'),
(8, 3, 'Hot Dishes'),
(9, 3, 'Party Platter'),
(10, 4, 'Thai Special'),
(11, 4, 'Vegan Options'),
(12, 4, 'Chef Special'),
(13, 5, 'Curry Menu'),
(14, 5, 'Tandoori Special'),
(15, 5, 'Thali Special')
ON CONFLICT (menu_id) DO NOTHING;

-- Reset sequence
SELECT setval('menu_menu_id_seq', (SELECT MAX(menu_id) FROM menu));

-- ==========================================
-- 5. INSERT MENU ITEMS
-- ==========================================
INSERT INTO menu_item (item_id, menu_id, item_name, price, available_status) VALUES 
-- Lunch Special (menu_id = 1)
(1, 1, 'Butter Chicken', 350.00, 'YES'),
(2, 1, 'Naan', 40.00, 'YES'),
(3, 1, 'Chicken Biryani', 280.00, 'YES'),
(4, 1, 'Dal Makhani', 220.00, 'YES'),
-- Dinner Delight (menu_id = 2)
(5, 2, 'Paneer Tikka', 250.00, 'YES'),
(6, 2, 'Rogan Josh', 380.00, 'YES'),
(7, 2, 'Garlic Naan', 45.00, 'YES'),
(8, 2, 'Gulab Jamun', 120.00, 'YES'),
-- Weekend Brunch (menu_id = 3)
(9, 3, 'Masala Dosa', 150.00, 'YES'),
(10, 3, 'Idli Sambhar', 80.00, 'YES'),
(11, 3, 'Vada Pav', 60.00, 'YES'),
-- Pizza Menu (menu_id = 4)
(12, 4, 'Margherita Pizza', 299.00, 'YES'),
(13, 4, 'Pepperoni Pizza', 399.00, 'YES'),
(14, 4, 'Farmhouse Pizza', 349.00, 'YES'),
(15, 4, 'Cheese Burst Pizza', 449.00, 'YES'),
-- Pasta Menu (menu_id = 5)
(16, 5, 'White Sauce Pasta', 249.00, 'YES'),
(17, 5, 'Red Sauce Pasta', 229.00, 'YES'),
(18, 5, 'Pesto Pasta', 279.00, 'YES'),
(19, 5, 'Garlic Bread', 99.00, 'YES'),
-- Family Combo (menu_id = 6)
(20, 6, 'Large Pizza + Garlic Bread', 599.00, 'YES'),
(21, 6, '2 Medium Pizzas + Drink', 699.00, 'YES'),
-- Sushi Menu (menu_id = 7)
(22, 7, 'California Roll', 450.00, 'YES'),
(23, 7, 'Nigiri Set', 550.00, 'YES'),
(24, 7, 'Sashimi Platter', 650.00, 'YES'),
(25, 7, 'Dragon Roll', 500.00, 'YES'),
-- Hot Dishes (menu_id = 8)
(26, 8, 'Tempura', 350.00, 'YES'),
(27, 8, 'Miso Soup', 120.00, 'YES'),
(28, 8, 'Teriyaki Chicken', 400.00, 'YES'),
(29, 8, 'Fried Rice', 250.00, 'YES'),
-- Party Platter (menu_id = 9)
(30, 9, 'Sushi Party Platter (48 pcs)', 2500.00, 'YES'),
(31, 9, 'Sake Set', 1800.00, 'YES'),
-- Thai Special (menu_id = 10)
(32, 10, 'Pad Thai', 320.00, 'YES'),
(33, 10, 'Green Curry', 380.00, 'YES'),
(34, 10, 'Tom Yum Soup', 250.00, 'YES'),
(35, 10, 'Thai Fried Rice', 280.00, 'YES'),
-- Vegan Options (menu_id = 11)
(36, 11, 'Vegan Pad Thai', 320.00, 'YES'),
(37, 11, 'Tofu Green Curry', 350.00, 'YES'),
(38, 11, 'Vegan Spring Rolls', 180.00, 'YES'),
-- Chef Special (menu_id = 12)
(39, 12, 'Chef Special Curry', 450.00, 'YES'),
(40, 12, 'Seafood Pad Thai', 420.00, 'YES'),
-- Curry Menu (menu_id = 13)
(41, 13, 'Chicken Curry', 280.00, 'YES'),
(42, 13, 'Mutton Curry', 350.00, 'YES'),
(43, 13, 'Fish Curry', 320.00, 'YES'),
(44, 13, 'Egg Curry', 180.00, 'YES'),
-- Tandoori Special (menu_id = 14)
(45, 14, 'Tandoori Chicken', 300.00, 'YES'),
(46, 14, 'Seekh Kebab', 250.00, 'YES'),
(47, 14, 'Paneer Tikka', 220.00, 'YES'),
(48, 14, 'Tandoori Roti', 25.00, 'YES'),
-- Thali Special (menu_id = 15)
(49, 15, 'Veg Thali', 199.00, 'YES'),
(50, 15, 'Non-Veg Thali', 299.00, 'YES'),
(51, 15, 'Seafood Thali', 399.00, 'YES')
ON CONFLICT (item_id) DO NOTHING;

-- Reset sequence
SELECT setval('menu_item_item_id_seq', (SELECT MAX(item_id) FROM menu_item));

-- ==========================================
-- 6. INSERT CUSTOMERS
-- ==========================================
INSERT INTO customer (customer_id, full_name, phone, email, gender) VALUES 
(1, 'John Updated', '9876543211', 'john.updated@example.com', 'M'),
(2, 'Amit Sharma', '9988776655', 'amit@email.com', 'M'),
(3, 'Priya Patel', '9988776644', 'priya@email.com', 'F'),
(4, 'Rajesh Kumar', '9988776633', 'rajesh@email.com', 'M'),
(5, 'Neha Gupta', '9988776622', 'neha@email.com', 'F'),
(6, 'Vikram Singh', '9988776611', 'vikram@email.com', 'M')
ON CONFLICT (customer_id) DO NOTHING;

-- Reset sequence
SELECT setval('customer_customer_id_seq', (SELECT MAX(customer_id) FROM customer));

-- ==========================================
-- 7. INSERT ADDRESSES (Complete for all customers)
-- ==========================================
INSERT INTO address (address_id, customer_id, house_no, street, city, pincode, address_type) VALUES 
-- Customer 1: John Updated
(1, 1, 'A-101', 'Marine Drive', 'Mumbai', '400001', 'HOME'),
(2, 1, 'B-202', 'Andheri East', 'Mumbai', '400093', 'OFFICE'),
(3, 1, 'C-303', 'Powai', 'Mumbai', '400076', 'OTHER'),

-- Customer 2: Amit Sharma
(4, 2, 'D-404', 'Juhu', 'Mumbai', '400049', 'HOME'),
(5, 2, 'E-505', 'Bandra West', 'Mumbai', '400050', 'OFFICE'),
(6, 2, 'F-606', 'Navi Mumbai', 'Mumbai', '400706', 'OTHER'),

-- Customer 3: Priya Patel
(7, 3, 'G-707', 'Connaught Place', 'Delhi', '110001', 'HOME'),
(8, 3, 'H-808', 'Rajouri Garden', 'Delhi', '110027', 'OFFICE'),
(9, 3, 'I-909', 'Saket', 'Delhi', '110017', 'OTHER'),

-- Customer 4: Rajesh Kumar
(10, 4, 'J-010', 'Indiranagar', 'Bangalore', '560038', 'HOME'),
(11, 4, 'K-111', 'Koramangala', 'Bangalore', '560034', 'OFFICE'),
(12, 4, 'L-222', 'Whitefield', 'Bangalore', '560066', 'OTHER'),

-- Customer 5: Neha Gupta
(13, 5, 'M-333', 'Salt Lake City', 'Kolkata', '700064', 'HOME'),
(14, 5, 'N-444', 'Park Street', 'Kolkata', '700016', 'OFFICE'),

-- Customer 6: Vikram Singh
(15, 6, 'O-555', 'MG Road', 'Pune', '411001', 'HOME'),
(16, 6, 'P-666', 'Hinjewadi', 'Pune', '411057', 'OFFICE'),
(17, 6, 'Q-777', 'Koregaon Park', 'Pune', '411001', 'OTHER')
ON CONFLICT (address_id) DO NOTHING;

-- Reset sequence
SELECT setval('address_address_id_seq', (SELECT MAX(address_id) FROM address));

-- ==========================================
-- 8. INSERT DELIVERY PERSONS
-- ==========================================
INSERT INTO delivery_person (delivery_id, name, phone, vehicle_no, status) VALUES 
(1, 'Ramesh Kumar Singh', '9988776656', 'MH01AB5678', 'BUSY'),
(2, 'Arjun Reddy', '7778889991', 'MH02CD5678', 'AVAILABLE'),
(3, 'Kabir Singh', '7778889992', 'MH03EF9012', 'AVAILABLE'),
(4, 'Rana Pratap', '7778889993', 'DL04AB1234', 'AVAILABLE'),
(5, 'Devendra Singh', '7778889994', 'KA05CD5678', 'BUSY'),
(6, 'Manoj Tiwari', '7778889995', 'MH06KL6789', 'AVAILABLE'),
(7, 'Nawaz Khan', '7778889996', 'MH07MN3456', 'AVAILABLE'),
(8, 'Pankaj Tripathi', '7778889997', 'MH08OP7890', 'BUSY'),
(9, 'Irrfan Khan', '7778889998', 'MH09QR1234', 'AVAILABLE')
ON CONFLICT (delivery_id) DO NOTHING;

-- Reset sequence
SELECT setval('delivery_person_delivery_id_seq', (SELECT MAX(delivery_id) FROM delivery_person));

-- ==========================================
-- 9. INSERT SAMPLE ORDERS (NEW)
-- ==========================================
INSERT INTO orders (order_id, customer_id, restaurant_id, delivery_id, total_amount, order_status) VALUES 
-- Order 1: John Updated orders from Spice Garden Premium
(1, 1, 1, 2, 630.00, 'DELIVERED'),
-- Order 2: Amit Sharma orders from Pizza Paradise
(2, 2, 2, 3, 698.00, 'DELIVERED'),
-- Order 3: Priya Patel orders from Sushi House
(3, 3, 3, 4, 1000.00, 'ONWAY'),
-- Order 4: Rajesh Kumar orders from Thai Delight
(4, 4, 4, 6, 700.00, 'PLACED'),
-- Order 5: Neha Gupta orders from Curry House
(5, 5, 5, 7, 600.00, 'DELIVERED'),
-- Order 6: Vikram Singh orders from Spice Garden Premium
(6, 6, 1, 8, 450.00, 'ONWAY'),
-- Order 7: John Updated orders from Curry House
(7, 1, 5, 9, 850.00, 'PLACED'),
-- Order 8: Priya Patel orders from Spice Garden Premium
(8, 3, 1, 2, 350.00, 'DELIVERED')
ON CONFLICT (order_id) DO NOTHING;

-- Reset sequence
SELECT setval('orders_order_id_seq', (SELECT MAX(order_id) FROM orders));

-- ==========================================
-- 10. INSERT ORDER ITEMS (for the orders above)
-- ==========================================
INSERT INTO order_item (order_id, item_id, quantity, price) VALUES 
-- Order 1 items (John - Spice Garden)
(1, 1, 2, 350.00),   -- Butter Chicken x2
(1, 2, 3, 40.00),    -- Naan x3
(1, 3, 1, 280.00),   -- Chicken Biryani x1

-- Order 2 items (Amit - Pizza Paradise)
(2, 12, 2, 299.00),  -- Margherita Pizza x2
(2, 19, 2, 99.00),   -- Garlic Bread x2

-- Order 3 items (Priya - Sushi House)
(3, 22, 2, 450.00),  -- California Roll x2
(3, 23, 1, 550.00),  -- Nigiri Set x1
(3, 26, 1, 350.00),  -- Tempura x1

-- Order 4 items (Rajesh - Thai Delight)
(4, 32, 2, 320.00),  -- Pad Thai x2
(4, 33, 1, 380.00),  -- Green Curry x1
(4, 34, 1, 250.00),  -- Tom Yum Soup x1

-- Order 5 items (Neha - Curry House)
(5, 41, 2, 280.00),  -- Chicken Curry x2
(5, 48, 4, 25.00),   -- Tandoori Roti x4

-- Order 6 items (Vikram - Spice Garden)
(6, 5, 2, 250.00),   -- Paneer Tikka x2
(6, 7, 2, 45.00),    -- Garlic Naan x2

-- Order 7 items (John - Curry House)
(7, 45, 2, 300.00),  -- Tandoori Chicken x2
(7, 42, 1, 350.00),  -- Mutton Curry x1
(7, 48, 3, 25.00),   -- Tandoori Roti x3

-- Order 8 items (Priya - Spice Garden)
(8, 1, 1, 350.00)    -- Butter Chicken x1
ON CONFLICT (order_id, item_id) DO NOTHING;

-- ==========================================
-- 11. INSERT PAYMENTS (for completed orders)
-- ==========================================
INSERT INTO payment (payment_id, order_id, payment_mode, amount, payment_status) VALUES 
-- Payment for Order 1 (Delivered)
(1, 1, 'UPI', 630.00, 'SUCCESS'),
-- Payment for Order 2 (Delivered)
(2, 2, 'CARD', 698.00, 'SUCCESS'),
-- Payment for Order 3 (Onway)
(3, 3, 'CASH', 1000.00, 'SUCCESS'),
-- Payment for Order 4 (Placed - Pending)
(4, 4, 'UPI', 700.00, 'PENDING'),
-- Payment for Order 5 (Delivered)
(5, 5, 'CARD', 600.00, 'SUCCESS'),
-- Payment for Order 6 (Onway)
(6, 6, 'UPI', 450.00, 'SUCCESS'),
-- Payment for Order 7 (Placed - Pending)
(7, 7, 'CARD', 850.00, 'PENDING'),
-- Payment for Order 8 (Delivered)
(8, 8, 'UPI', 350.00, 'SUCCESS')
ON CONFLICT (payment_id) DO NOTHING;

-- Reset sequence
SELECT setval('payment_payment_id_seq', (SELECT MAX(payment_id) FROM payment));

-- ==========================================
-- 12. INSERT REVIEWS
-- ==========================================
INSERT INTO review (review_id, customer_id, restaurant_id, rating, comments) VALUES 
-- Reviews for delivered orders
(1, 1, 1, 5, 'Excellent food and great ambiance! The butter chicken was amazing. Will order again.'),
(2, 2, 2, 4, 'Good pizza but delivery was a bit slow. Still tasty!'),
(3, 3, 3, 5, 'Best sushi in town! The California roll is fresh and delicious.'),
(4, 5, 5, 4, 'Great Indian curry, reasonable prices. The chicken curry is authentic.'),
(5, 3, 1, 5, 'Spice Garden never disappoints! The butter chicken is my favorite.'),
(6, 1, 5, 4, 'Good curry house, tandoori chicken was excellent!')
ON CONFLICT (review_id) DO NOTHING;

-- Reset sequence
SELECT setval('review_review_id_seq', (SELECT MAX(review_id) FROM review));

-- ==========================================
-- CREATE INDEXES FOR PERFORMANCE
-- ==========================================
CREATE INDEX IF NOT EXISTS idx_restaurant_city ON restaurant(city);
CREATE INDEX IF NOT EXISTS idx_restaurant_rating ON restaurant(rating);
CREATE INDEX IF NOT EXISTS idx_menu_item_name ON menu_item(item_name);
CREATE INDEX IF NOT EXISTS idx_orders_customer ON orders(customer_id);
CREATE INDEX IF NOT EXISTS idx_orders_restaurant ON orders(restaurant_id);
CREATE INDEX IF NOT EXISTS idx_orders_date ON orders(order_date);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders(order_status);
CREATE INDEX IF NOT EXISTS idx_review_restaurant ON review(restaurant_id);
CREATE INDEX IF NOT EXISTS idx_review_customer ON review(customer_id);
CREATE INDEX IF NOT EXISTS idx_customer_email ON customer(email);
CREATE INDEX IF NOT EXISTS idx_customer_phone ON customer(phone);
CREATE INDEX IF NOT EXISTS idx_delivery_person_status ON delivery_person(status);
CREATE INDEX IF NOT EXISTS idx_menu_item_price ON menu_item(price);

-- ==========================================
-- VERIFICATION QUERIES (Run to verify data)
-- ==========================================
DO $$
BEGIN
    RAISE NOTICE '==========================================';
    RAISE NOTICE 'DATA VERIFICATION SUMMARY';
    RAISE NOTICE '==========================================';
    RAISE NOTICE 'Categories: %', (SELECT COUNT(*) FROM category);
    RAISE NOTICE 'Restaurants: %', (SELECT COUNT(*) FROM restaurant);
    RAISE NOTICE 'Restaurant-Category Links: %', (SELECT COUNT(*) FROM restaurant_category);
    RAISE NOTICE 'Menus: %', (SELECT COUNT(*) FROM menu);
    RAISE NOTICE 'Menu Items: %', (SELECT COUNT(*) FROM menu_item);
    RAISE NOTICE 'Customers: %', (SELECT COUNT(*) FROM customer);
    RAISE NOTICE 'Addresses: %', (SELECT COUNT(*) FROM address);
    RAISE NOTICE 'Delivery Persons: %', (SELECT COUNT(*) FROM delivery_person);
    RAISE NOTICE 'Orders: %', (SELECT COUNT(*) FROM orders);
    RAISE NOTICE 'Order Items: %', (SELECT COUNT(*) FROM order_item);
    RAISE NOTICE 'Payments: %', (SELECT COUNT(*) FROM payment);
    RAISE NOTICE 'Reviews: %', (SELECT COUNT(*) FROM review);
    RAISE NOTICE '==========================================';
    RAISE NOTICE 'Database initialization complete!';
    RAISE NOTICE '==========================================';
END $$;