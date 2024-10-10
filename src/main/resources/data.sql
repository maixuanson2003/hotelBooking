
CREATE TABLE IF NOT EXISTS account_hotel (
   id BIGINT NOT NULL AUTO_INCREMENT,
   username VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   phone VARCHAR(255) DEFAULT NULL,
   PRIMARY KEY (id),
   UNIQUE (username)
);
CREATE TABLE IF NOT EXISTS actor (
   id bigint NOT NULL AUTO_INCREMENT,
   role varchar(255) DEFAULT NULL,
   address varchar(255) DEFAULT NULL,
   birthday varchar(255) DEFAULT NULL,
   email varchar(255) DEFAULT NULL,
   fullname varchar(255) DEFAULT NULL,
   gender varchar(255) DEFAULT NULL,
   password varchar(255) DEFAULT NULL,
   phone varchar(255) DEFAULT NULL,
   status varchar(255) DEFAULT NULL,
   username varchar(255) DEFAULT NULL,
   PRIMARY KEY (id)
) ;
CREATE TABLE IF NOT EXISTS booking (
   id bigint NOT NULL AUTO_INCREMENT,
   create_at varchar(255) DEFAULT NULL,
   number_people int DEFAULT NULL,
   status varchar(255) DEFAULT NULL,
   total_price bigint DEFAULT NULL,
   total_room int DEFAULT NULL,
   user_id bigint NOT NULL,
   PRIMARY KEY (id),
   KEY FK_booking_user (user_id),
   CONSTRAINT FK_booking_user FOREIGN KEY (user_id) REFERENCES actor (id) ON DELETE CASCADE
) ;
CREATE TABLE IF NOT EXISTS booking_change_details_repository (
   id bigint NOT NULL AUTO_INCREMENT,
   price bigint DEFAULT NULL,
   check_in_date date DEFAULT NULL,
   check_out_date date DEFAULT NULL,
   create_at varchar(255) DEFAULT NULL,
   status varchar(255) DEFAULT NULL,
   booking_id bigint NOT NULL,
   PRIMARY KEY (id),
   KEY FK_booking_id (booking_id),
   CONSTRAINT FK_booking_id FOREIGN KEY (booking_id) REFERENCES booking (id)ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS bookingdetails (
   id bigint NOT NULL AUTO_INCREMENT,
   amount_room bigint DEFAULT NULL,
   check_in_date date DEFAULT NULL,
   check_out_date date DEFAULT NULL,
   create_at varchar(255) DEFAULT NULL,
   price bigint DEFAULT NULL,
   sale_code_id bigint NOT NULL,
   booking_id bigint NOT NULL,
   hotel_room_id bigint NOT NULL,
   PRIMARY KEY (id),
   KEY FK_sale_code_id (sale_code_id),
   KEY FK_booking_id (booking_id),
   KEY FK_hotel_room_id (hotel_room_id),
   CONSTRAINT FK_hotel_room_id FOREIGN KEY (hotel_room_id) REFERENCES hotel_room (id) ON DELETE CASCADE,
   CONSTRAINT FK_sale_code_id FOREIGN KEY (sale_code_id) REFERENCES sale_code (id) ON DELETE CASCADE,
   CONSTRAINT FK_booking_id FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS city (
   id bigint NOT NULL AUTO_INCREMENT,
   code_city bigint DEFAULT NULL,
   description varchar(255) DEFAULT NULL,
   name_city varchar(255) DEFAULT NULL,
   PRIMARY KEY (id),
   UNIQUE KEY UK_code_city (code_city),
   UNIQUE KEY UK_name_city (name_city)
);
CREATE TABLE IF NOT EXISTS event (
   id bigint NOT NULL AUTO_INCREMENT,
   date_end varchar(255) DEFAULT NULL,
   date_start varchar(255) DEFAULT NULL,
   description varchar(255) DEFAULT NULL,
   image varchar(255) DEFAULT NULL,
   name_event varchar(255) DEFAULT NULL,
   city_id bigint NOT NULL,
   PRIMARY KEY (id),
   KEY FK_city_id (city_id),
   CONSTRAINT FK_city_id FOREIGN KEY (city_id) REFERENCES city (id) ON DELETE CASCADE
) ;
CREATE TABLE IF NOT EXISTS hotel (
   id bigint NOT NULL AUTO_INCREMENT,
   address varchar(255) DEFAULT NULL,
   description varchar(255) DEFAULT NULL,
   email varchar(255) DEFAULT NULL,
   hotline varchar(255) DEFAULT NULL,
   total_room int DEFAULT NULL,
   cancelfee bigint DEFAULT NULL,
   changefee bigint DEFAULT NULL,
   name_hotel varchar(255) DEFAULT NULL,
   star_point int DEFAULT NULL,
   account_hotel_id bigint DEFAULT NULL,
   city_id bigint NOT NULL,
   PRIMARY KEY (id),
   UNIQUE KEY UK_account_hotel_id (account_hotel_id),
   KEY FK_city_id (city_id),
   CONSTRAINT FK_city_id FOREIGN KEY (city_id) REFERENCES city (id) ON DELETE CASCADE,
   CONSTRAINT FK_account_hotel_id FOREIGN KEY (account_hotel_id) REFERENCES account_hotel (id) ON DELETE CASCADE
) ;
CREATE TABLE IF NOT EXISTS hotel_facility (
   id bigint NOT NULL AUTO_INCREMENT,
   description varchar(255) DEFAULT NULL,
   name_hotel_facility varchar(255) DEFAULT NULL,
   PRIMARY KEY (id)
) ;
CREATE TABLE IF NOT EXISTS hotel_hotel_facility_hotel (
   hotel_facility_id bigint NOT NULL,
   hotel_id bigint NOT NULL,
   PRIMARY KEY (hotel_facility_id, hotel_id),
   KEY fk_hotel_hotel_facility (hotel_id),
   KEY fk_hotel_facility_hotel (hotel_facility_id),
   CONSTRAINT fk_hotel_hotel_facility FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE,
   CONSTRAINT fk_hotel_facility_hotel FOREIGN KEY (hotel_facility_id) REFERENCES hotel_facility (id) ON DELETE CASCADE
) ;
CREATE TABLE IF NOT EXISTS hotel_image (
   id bigint NOT NULL AUTO_INCREMENT,
   link_image varchar(255) DEFAULT NULL,
   hotel_id bigint NOT NULL,
   PRIMARY KEY (id),
   KEY fk_hotel_image_hotel (hotel_id),
   CONSTRAINT fk_hotel_image_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE
) ;
CREATE TABLE  IF NOT EXISTS hotel_policy (
   id bigint NOT NULL AUTO_INCREMENT,
   description varchar(255) DEFAULT NULL,
   is_free bit(1) DEFAULT NULL,
   is_related_fee bit(1) DEFAULT NULL,
   name_policy varchar(255) DEFAULT NULL,
   PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS hotel_policy_hotel (
   hotel_policy_id bigint NOT NULL,
   hotel_id bigint NOT NULL,
   KEY FK_hotel_policy_hotel (hotel_policy_id),
   KEY FK_hotel_policy_hotel_hotel (hotel_id),
   CONSTRAINT FK_hotel_policy_hotel FOREIGN KEY (hotel_policy_id) REFERENCES hotel_policy (id) ON DELETE CASCADE,
   CONSTRAINT FK_hotel_policy_hotel_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS hotel_room (
   id bigint NOT NULL AUTO_INCREMENT,
   amount_room int DEFAULT NULL,
   floor_number int DEFAULT NULL,
   image varchar(255) DEFAULT NULL,
   number_room_last int DEFAULT NULL,
   number_of_booking int DEFAULT NULL,
   number_people bigint DEFAULT NULL,
   price_per_night bigint DEFAULT NULL,
   status varchar(255) DEFAULT NULL,
   type_room varchar(255) DEFAULT NULL,
   hotel_id bigint NOT NULL,
   PRIMARY KEY (id),
   KEY FK_hotel_room_hotel (hotel_id),
   CONSTRAINT FK_hotel_room_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS hotel_room_features (
   id bigint NOT NULL AUTO_INCREMENT,
   description varchar(255) DEFAULT NULL,
   name_features varchar(255) DEFAULT NULL,
   PRIMARY KEY (id)
) ;
CREATE TABLE IF NOT EXISTS hotel_room_features_room (
   hotel_room_features_id bigint NOT NULL,
   hotel_room_id bigint NOT NULL,
   PRIMARY KEY (hotel_room_features_id, hotel_room_id),  -- Thêm khóa chính cho bảng nhiều-nhiều
   KEY FK_hotel_room_id (hotel_room_id),
   KEY FK_hotel_room_features_id (hotel_room_features_id),
   CONSTRAINT FK_hotel_room_features_id FOREIGN KEY (hotel_room_features_id) REFERENCES hotel_room_features (id) ON DELETE CASCADE,
   CONSTRAINT FK_hotel_room_id FOREIGN KEY (hotel_room_id) REFERENCES hotel_room (id) ON DELETE CASCADE
) ;
CREATE TABLE IF NOT EXISTS payment (
   id bigint NOT NULL AUTO_INCREMENT,
   total_price bigint DEFAULT NULL,
   create_at datetime(6) DEFAULT NULL,
   status varchar(255) DEFAULT NULL,
   actor_id bigint DEFAULT NULL, -- Đổi tên cho dễ hiểu
   booking_id bigint DEFAULT NULL,
   PRIMARY KEY (id),
   UNIQUE KEY UK_booking_id (booking_id), -- Đổi tên cho dễ hiểu
   KEY FK_actor_id (actor_id), -- Đổi tên cho dễ hiểu
   CONSTRAINT FK_actor_id FOREIGN KEY (actor_id) REFERENCES actor (id) ON DELETE CASCADE,
   CONSTRAINT FK_booking_id FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE
) ;
CREATE TABLE IF NOT EXISTS review (
   id bigint NOT NULL AUTO_INCREMENT,
   description varchar(255) DEFAULT NULL,
   star_point int NOT NULL, -- Đã sửa lỗi chính tả từ "star_pont" thành "star_point"
   hotel_id bigint NOT NULL,
   user_id bigint NOT NULL,
   PRIMARY KEY (id),
   KEY FK_hotel_id (hotel_id), -- Đổi tên cho dễ hiểu
   KEY FK_user_id (user_id),   -- Đổi tên cho dễ hiểu
   CONSTRAINT FK_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE,
   CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES actor (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS sale_code (
   id BIGINT NOT NULL AUTO_INCREMENT,                 -- Khóa chính, tự động tăng
   code VARCHAR(255) DEFAULT NULL,                     -- Mã giảm giá
   start_date DATETIME DEFAULT NULL,                   -- Ngày bắt đầu
   end_date DATETIME DEFAULT NULL,                     -- Ngày kết thúc
   description VARCHAR(255) DEFAULT NULL,              -- Mô tả về mã giảm giá
   discount_percentage FLOAT DEFAULT NULL,              -- Phần trăm giảm giá
   status VARCHAR(255) DEFAULT NULL,                    -- Trạng thái của mã giảm giá
   PRIMARY KEY (id)                                    -- Khóa chính
);
-- Bảng booking
--ALTER TABLE booking DROP FOREIGN KEY FK_booking_user;
--ALTER TABLE booking
--ADD CONSTRAINT FK_booking_user FOREIGN KEY (user_id) REFERENCES actor (id) ON DELETE CASCADE;
--
---- Bảng booking_change_details_repository
--ALTER TABLE booking_change_details_repository DROP FOREIGN KEY FK_booking_id;
--ALTER TABLE booking_change_details_repository
--ADD CONSTRAINT FK_booking_id FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE;
--
---- Bảng bookingdetails
--ALTER TABLE bookingdetails DROP FOREIGN KEY FK_booking_id;
--ALTER TABLE bookingdetails
--ADD CONSTRAINT FK_booking_id FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE;
--
--ALTER TABLE bookingdetails DROP FOREIGN KEY FK_hotel_room_id;
--ALTER TABLE bookingdetails
--ADD CONSTRAINT FK_hotel_room_id FOREIGN KEY (hotel_room_id) REFERENCES hotel_room (id) ON DELETE CASCADE;
--
--ALTER TABLE bookingdetails DROP FOREIGN KEY FK_sale_code_id;
--ALTER TABLE bookingdetails
--ADD CONSTRAINT FK_sale_code_id FOREIGN KEY (sale_code_id) REFERENCES sale_code (id) ON DELETE CASCADE;
--
---- Bảng hotel
--ALTER TABLE hotel DROP FOREIGN KEY FK_account_hotel_id;
--ALTER TABLE hotel
--ADD CONSTRAINT FK_account_hotel_id FOREIGN KEY (account_hotel_id) REFERENCES account_hotel (id) ON DELETE CASCADE;
--
--ALTER TABLE hotel DROP FOREIGN KEY FK_city_id;
--ALTER TABLE hotel
--ADD CONSTRAINT FK_city_id FOREIGN KEY (city_id) REFERENCES city (id) ON DELETE CASCADE;
--
---- Bảng hotel_hotel_facility_hotel
--ALTER TABLE hotel_hotel_facility_hotel DROP FOREIGN KEY fk_hotel_hotel_facility;
--ALTER TABLE hotel_hotel_facility_hotel
--ADD CONSTRAINT fk_hotel_hotel_facility FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE;
--
--ALTER TABLE hotel_hotel_facility_hotel DROP FOREIGN KEY fk_hotel_facility_hotel;
--ALTER TABLE hotel_hotel_facility_hotel
--ADD CONSTRAINT fk_hotel_facility_hotel FOREIGN KEY (hotel_facility_id) REFERENCES hotel_facility (id) ON DELETE CASCADE;
--
---- Bảng hotel_image
--ALTER TABLE hotel_image DROP FOREIGN KEY fk_hotel_image_hotel;
--ALTER TABLE hotel_image
--ADD CONSTRAINT fk_hotel_image_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE;
--
---- Bảng event
--ALTER TABLE event DROP FOREIGN KEY FK_city_id;
--ALTER TABLE event
--ADD CONSTRAINT FK_city_id FOREIGN KEY (city_id) REFERENCES city (id) ON DELETE CASCADE;
--
---- Bảng hotel_room
--ALTER TABLE hotel_room DROP FOREIGN KEY FK_hotel_room_hotel;
--ALTER TABLE hotel_room
--ADD CONSTRAINT FK_hotel_room_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE;
--
---- Bảng hotel_policy_hotel
--ALTER TABLE hotel_policy_hotel DROP FOREIGN KEY FK_hotel_policy_hotel;
--ALTER TABLE hotel_policy_hotel
--ADD CONSTRAINT FK_hotel_policy_hotel FOREIGN KEY (hotel_policy_id) REFERENCES hotel_policy (id) ON DELETE CASCADE;
--
--ALTER TABLE hotel_policy_hotel DROP FOREIGN KEY FK_hotel_policy_hotel_hotel;
--ALTER TABLE hotel_policy_hotel
--ADD CONSTRAINT FK_hotel_policy_hotel_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE;
--
---- Bảng payment
--ALTER TABLE payment DROP FOREIGN KEY FK_actor_id;
--ALTER TABLE payment
--ADD CONSTRAINT FK_actor_id FOREIGN KEY (actor_id) REFERENCES actor (id) ON DELETE CASCADE;
--
--ALTER TABLE payment DROP FOREIGN KEY FK_booking_id;
--ALTER TABLE payment
--ADD CONSTRAINT FK_booking_id FOREIGN KEY (booking_id) REFERENCES booking (id) ON DELETE CASCADE;
--
---- Bảng review
--ALTER TABLE review DROP FOREIGN KEY FK_hotel_id;
--ALTER TABLE review
--ADD CONSTRAINT FK_hotel_id FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE;
--
--ALTER TABLE review DROP FOREIGN KEY FK_user_id;
--ALTER TABLE review
--ADD CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES actor (id) ON DELETE CASCADE;
--
---- Bảng bookingdetails
--ALTER TABLE bookingdetails DROP FOREIGN KEY FK_hotel_room_id;
--ALTER TABLE bookingdetails
--ADD CONSTRAINT FK_hotel_room_id FOREIGN KEY (hotel_room_id) REFERENCES hotel_room (id) ON DELETE CASCADE;

-- Chèn dữ liệu vào bảng city
INSERT IGNORE  INTO   account_hotel (id,username, password, phone) VALUES
(1,'account1', 'password1', '123456789'),
(2,'account2', 'password2', '234567890'),
(3,'account3', 'password3', '345678901'),
(4,'account4', 'password4', '456789012'),
(5,'account5', 'password5', '567890123'),
(6,'account6', 'password6', '678901234');
INSERT IGNORE  INTO city (id,code_city, name_city, description) VALUES
(1,29, 'City A', 'Description of City A'),
(2,30, 'City B', 'Description of City B'),
(3,40, 'City C', 'Description of City C');
INSERT IGNORE  INTO hotel (id,address, description, email, hotline, total_room, cancelfee, changefee, name_hotel, star_point, account_hotel_id, city_id) VALUES
(1,'Address 1', 'Description 1', 'hotel1@example.com', '123456789', 50, 100000, 50000, 'Hotel 1', 5, 1, 1),
(2,'Address 2', 'Description 2', 'hotel2@example.com', '234567890', 40, 100000, 50000, 'Hotel 2', 4, 2, 2),
(3,'Address 3', 'Description 3', 'hotel3@example.com', '345678901', 60, 100000, 50000, 'Hotel 3', 3, 3, 1),
(4,'Address 4', 'Description 4', 'hotel4@example.com', '456789012', 70, 100000, 50000, 'Hotel 4', 5, 4, 2),
(5,'Address 5', 'Description 5', 'hotel5@example.com', '567890123', 80, 100000, 50000, 'Hotel 5', 4, 5, 3),
(6,'Address 6', 'Description 6', 'hotel6@example.com', '678901234', 90, 100000, 50000, 'Hotel 6', 3, 6, 3);

INSERT IGNORE INTO hotel_image (link_image, hotel_id) VALUES
('https://example.com/hotel1_image1.jpg', 1),
('https://example.com/hotel1_image2.jpg', 1),
('https://example.com/hotel1_image3.jpg', 1),
('https://example.com/hotel2_image1.jpg', 2),
('https://example.com/hotel2_image2.jpg', 2),
('https://example.com/hotel2_image3.jpg', 2),
('https://example.com/hotel3_image1.jpg', 3),
('https://example.com/hotel3_image2.jpg', 3),
('https://example.com/hotel3_image3.jpg', 3),
('https://example.com/hotel4_image1.jpg', 4),
('https://example.com/hotel4_image2.jpg', 4),
('https://example.com/hotel4_image3.jpg', 4),
('https://example.com/hotel5_image1.jpg', 5),
('https://example.com/hotel5_image2.jpg', 5),
('https://example.com/hotel5_image3.jpg', 5),
('https://example.com/hotel6_image1.jpg', 6),
('https://example.com/hotel6_image2.jpg', 6),
('https://example.com/hotel6_image3.jpg', 6);


INSERT IGNORE  INTO hotel_room (id, amount_room, image, number_room_last, number_of_booking, number_people, price_per_night, status, type_room, hotel_id) VALUES
(1, 1, 'https://example.com/hotel1_room1.jpg', 10, 0, 2, 500000, 'Available', 'Deluxe', 1),
(2, 1, 'https://example.com/hotel1_room2.jpg', 12, 0, 2, 400000, 'Available', 'Standard', 1),
(3, 2, 'https://example.com/hotel2_room1.jpg', 21, 0, 2, 600000, 'Available', 'Suite', 2),
(4, 2, 'https://example.com/hotel2_room2.jpg', 22, 0, 2, 550000, 'Available', 'Deluxe', 2),
(5, 3, 'https://example.com/hotel3_room1.jpg', 31, 0, 2, 650000, 'Available', 'Suite', 3),
(6, 3, 'https://example.com/hotel3_room2.jpg', 32, 0, 2, 500000, 'Available', 'Standard', 3),
(7, 1, 'https://example.com/hotel4_room1.jpg', 41, 0, 2, 700000, 'Available', 'Deluxe', 4),
(8, 1, 'https://example.com/hotel4_room2.jpg', 42, 0, 2, 600000, 'Available', 'Standard', 4),
(9, 2, 'https://example.com/hotel5_room1.jpg', 51, 0, 2, 800000, 'Available', 'Suite', 5),
(10, 2, 'https://example.com/hotel5_room2.jpg', 52, 0, 2, 750000, 'Available', 'Deluxe', 5),
(11, 3, 'https://example.com/hotel6_room1.jpg', 61, 0, 2, 300000, 'Available', 'Standard', 6),
(12, 3, 'https://example.com/hotel6_room2.jpg', 62, 0, 2, 250000, 'Available', 'Budget', 6);

INSERT IGNORE INTO hotel_policy (id, description, is_free, is_related_fee, name_policy) VALUES
(1, 'Free cancellation within 24 hours', 1, 0, 'Free Cancellation'),
(2, 'No refund for non-refundable bookings', 0, 1, 'No Refund'),
(3, 'Breakfast included', 1, 0, 'Breakfast Included'),
(4, 'Late check-out available', 1, 0, 'Late Check-out'),
(5, 'Early check-in available', 1, 0, 'Early Check-in'),
(6, 'Pets allowed with fee', 0, 1, 'Pet Policy');
--ALTER TABLE  hotel_facility ADD description varchar(255);
INSERT IGNORE INTO hotel_facility (id, description, name_hotel_facility) VALUES
(1, 'Free Wi-Fi in all areas', 'Wi-Fi'),
(2, '24-hour front desk service', 'Front Desk'),
(3, 'Swimming pool available', 'Pool'),
(4, 'Fitness center available', 'Fitness Center'),
(5, 'Spa and wellness center', 'Spa'),
(6, 'Conference room available', 'Conference Room');
INSERT IGNORE INTO hotel_room_features (id, description, name_features) VALUES
(1, 'Air conditioning and heating', 'Air Conditioning'),
(2, 'Flat-screen TV with cable channels', 'TV'),
(3, 'Minibar and refrigerator', 'Minibar'),
(4, 'Coffee maker available', 'Coffee Maker'),
(5, 'Hairdryer and toiletries', 'Bathroom Amenities'),
(6, 'Safety deposit box', 'Safety Deposit Box');
INSERT IGNORE INTO hotel_room_features_room (hotel_room_features_id, hotel_room_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(1, 2),
(2, 2),
(3, 2),
(1, 3),
(2, 3),
(4, 3),
(1, 4),
(2, 4),
(5, 4),
(1, 5),
(2, 5),
(6, 5),
(1, 6),
(3, 6);
INSERT IGNORE INTO hotel_policy_hotel (hotel_policy_id, hotel_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3);


INSERT IGNORE INTO hotel_hotel_facility_hotel (hotel_facility_id, hotel_id) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3);




