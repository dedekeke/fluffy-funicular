CREATE TABLE Account (
                         user_mail VARCHAR(100) NOT NULL,
                         password VARCHAR(64) NOT NULL,
                         account_role INT NOT NULL,
                         user_name VARCHAR(50) NOT NULL,
                         user_address VARCHAR(255),
                         user_phone VARCHAR(10),
                         PRIMARY KEY (user_mail)
);

CREATE TABLE Products (
                          product_id SERIAL PRIMARY KEY,
                          product_name VARCHAR(100) NOT NULL,
                          product_des VARCHAR(255),
                          product_price DOUBLE PRECISION NOT NULL,
                          product_img_source VARCHAR(255),
                          product_type VARCHAR(100),
                          product_brand VARCHAR(100)
);

CREATE TABLE Orders (
                        user_mail VARCHAR(100),
                        order_id SERIAL PRIMARY KEY,
                        order_status INT,
                        order_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        order_discount_code VARCHAR(8),
                        order_address VARCHAR(255) NOT NULL
);

CREATE TABLE Orders_detail (
                               order_id INT NOT NULL,
                               product_id INT NOT NULL,
                               amount_product INT,
                               price_product INT,
                               PRIMARY KEY (order_id, product_id),
                               FOREIGN KEY (order_id) REFERENCES Orders (order_id),
                               FOREIGN KEY (product_id) REFERENCES Products (product_id)
);

INSERT INTO Products (product_name, product_des, product_price, product_img_source, product_type, product_brand)
VALUES
    ('iPhone 11 Pro Max 512GB', 'Màn hình: 6.5", Super Retina XDR\nHĐH: iOS 13\nCPU: Apple A13 Bionic 6 nhân\nRAM: 4 GB, ROM: 512 GB\nCamera: 3 camera 12 MP, Selfie: 12 MP', 43990, 'https://cdn.tgdd.vn/Products/Images/42/210654/iphone-11-pro-max-512gb-gold-600x600.jpg', 'cellphone', 'apple'),
    ('iPhone 11 Pro Max 256GB', 'Màn hình: 6.5", Super Retina XDR\nHĐH: iOS 13\nCPU: Apple A13 Bionic 6 nhân\nRAM: 4 GB, ROM: 512 GB\nCamera: 3 camera 12 MP, Selfie: 12 MP', 37990, 'https://cdn.tgdd.vn/Products/Images/42/210653/iphone-11-pro-max-256gb-black-600x600.jpg', 'cellphone', 'apple'),
    ('iPhone Xs Max 256GB', 'Màn hình: 6.5", Super Retina\nHĐH: iOS 12\nCPU: Apple A12 Bionic 6 nhân\nRAM: 4 GB, ROM: 256 GB\nCamera: Chính 12 MP & Phụ 12 MP, Selfie: 7 MP', 32990, 'https://cdn.tgdd.vn/Products/Images/42/190322/iphone-xs-max-256gb-white-600x600.jpg', 'cellphone', 'apple'),
    ('iPhone X 256GB', 'Màn hình: 5.8", Super Retina\nHĐH: iOS 12\nCPU: Apple A11 Bionic 6 nhân\nRAM: 3 GB, ROM: 256 GB\nCamera: Chính 12 MP & Phụ 12 MP, Selfie: 7 MP', 27990, 'https://cdn.tgdd.vn/Products/Images/42/190324/iphone-xs-256gb-white-600x600.jpg', 'cellphone', 'apple'),
    ('iPhone Xs 64GB', 'Màn hình: 5.8", Super Retina\nHĐH: iOS 12\nCPU: Apple A12 Bionic 6 nhân\nRAM: 4 GB, ROM: 64 GB\nCamera: Chính 12 MP & Phụ 12 MP, Selfie: 7 MP', 24990, 'https://cdn.tgdd.vn/Products/Images/42/190321/iphone-xs-max-gold-600x600.jpg', 'cellphone', 'apple'),
    ('iPhone Xr 128GB', 'Màn hình: 6.1", Liquid Retina\nHĐH: iOS 12\nCPU: Apple A12 Bionic 6 nhân\nRAM: 3 GB, ROM: 128 GB\nCamera: 12 MP, Selfie: 7 MP', 17990, 'https://cdn.tgdd.vn/Products/Images/42/191483/iphone-xr-128gb-red-600x600.jpg', 'cellphone', 'apple'),
    ('iPhone Xr 128GB', 'Màn hình: 6.1", Liquid Retina\nHĐH: iOS 12\nCPU: Apple A12 Bionic 6 nhân\nRAM: 3 GB, ROM: 128 GB\nCamera: 12 MP, Selfie: 7 MP', 17990, 'https://cdn.tgdd.vn/Products/Images/42/191483/iphone-xr-128gb-red-600x600.jpg', 'cellphone', 'apple'),
    ('iPhone 8 Plus 64GB', 'Màn hình: 5.5", Retina HD\nHĐH: iOS 12\nCPU: Apple A11 Bionic 6 nhân\nRAM: 3 GB, ROM: 64 GB\nCamera: Chính 12 MP & Phụ 12 MP, Selfie: 7 MP', 16590, 'https://cdn.tgdd.vn/Products/Images/42/114110/iphone-8-plus-hh-600x600.jpg', 'cellphone', 'apple'),
    ('iPhone 7 Plus 32GB', 'Màn hình: 5.5", Retina HD\nHĐH: iOS 12\nCPU: Apple A10 Fusion 4 nhân 64-bit\nRAM: 3 GB, ROM: 32 GB\nCamera: Chính 12 MP & Phụ 12 MP, Selfie: 7 MP', 12490, 'https://cdn.tgdd.vn/Products/Images/42/78124/iphone-7-plus-32gb-gold-600x600.jpg', 'cellphone', 'apple'),
    ('iPhone 7 32GB', 'Màn hình: 4.7", Retina HD\nHĐH: iOS 12\nCPU: Apple A10 Fusion 4 nhân 64-bit\nRAM: 2 GB, ROM: 32 GB\nCamera: 12 MP, Selfie: 7 MP', 10490, 'https://cdn.tgdd.vn/Products/Images/42/74110/iphone-7-gold-600x600.jpg', 'cellphone', 'apple');

INSERT INTO Account (user_mail, password, account_role, user_name, user_address, user_phone)
VALUES
    ('duongdt@fpt.com.vn', '123', 1, 'Đinh Tùng Dương', 'Đại học FPT', '0765870407'),
    ('quytd@fpt.com.vn', '123', 1, 'Thái Duy Quý', 'Đại học Đà Lạt', '1234567');

-- SP
CREATE OR REPLACE FUNCTION GetProductsType(p_u INT, p_v INT, p_b VARCHAR(50))
    RETURNS TABLE(
                     product_id INT,
                     product_name VARCHAR(100),
                     product_des VARCHAR(255),
                     product_price DOUBLE PRECISION,
                     product_img_source VARCHAR(255),
                     product_type VARCHAR(100),
                     product_brand VARCHAR(100)
                 ) AS $$
BEGIN
    RETURN QUERY
        SELECT p.product_id, p.product_name, p.product_des, p.product_price, p.product_img_source, p.product_type, p.product_brand
        FROM (
                 SELECT *, ROW_NUMBER() OVER (ORDER BY product_id) AS row
                 FROM Products p
                 WHERE p.product_type = p_b
             ) p
        WHERE p.row >= p_u AND p.row <= p_v;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetProducts(p_u INT, p_v INT)
    RETURNS TABLE(
                     product_id INT,
                     product_name VARCHAR(100),
                     product_des VARCHAR(255),
                     product_price DOUBLE PRECISION,
                     product_img_source VARCHAR(255),
                     product_type VARCHAR(100),
                     product_brand VARCHAR(100)
                 ) AS $$
BEGIN
    RETURN QUERY
        SELECT p.product_id, p.product_name, p.product_des, p.product_price, p.product_img_source, p.product_type, p.product_brand
        FROM (
                 SELECT *, ROW_NUMBER() OVER (ORDER BY product_id) AS row
                 FROM Products p
             ) p
        WHERE p.row >= p_u AND p.row <= p_v;
END;
$$ LANGUAGE plpgsql;
