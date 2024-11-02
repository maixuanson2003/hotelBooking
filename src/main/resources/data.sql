
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
   image varchar(255) DEFAULT NULL,
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
   name_Policy varchar(255) DEFAULT NULL,
   PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS hotel_policy_details (
   id BIGINT NOT NULL AUTO_INCREMENT,
   coditional_info VARCHAR(255) DEFAULT NULL,
   number_people VARCHAR(255) DEFAULT NULL,
   fee BIGINT DEFAULT NULL,
   hotel_id BIGINT NOT NULL,
   hotel_policy_id BIGINT NOT NULL,
   PRIMARY KEY (id),
   KEY FK_hotel_policy_details_hotel (hotel_id),
   KEY FK_hotel_policy_details_policy (hotel_policy_id),
   CONSTRAINT FK_hotel_policy_details_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE,
   CONSTRAINT FK_hotel_policy_details_policy FOREIGN KEY (hotel_policy_id) REFERENCES hotel_policy (id) ON DELETE CASCADE
) ;
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
--INSERT IGNORE INTO account_hotel (id, username, password, phone) VALUES
--(1, 'account1', 'password1', '123456789'),
--(2, 'account2', 'password2', '234567890'),
--(3, 'account3', 'password3', '345678901'),
--(4, 'account4', 'password4', '456789012'),
--(5, 'account5', 'password5', '567890123'),
--(6, 'account6', 'password6', '567890123'),
--(7, 'account7', 'password7', '567890123'),
--(8, 'account8', 'password8', '567890123'),
--(9, 'account9', 'password9', '567890123'),
--(10, 'account10', 'password10', '567890123'),
--(11, 'account11', 'password11', '567890123'),
--(12, 'account12', 'password12', '567890123'),
--(13, 'account13', 'password13', '567890123'),
--(14, 'account14', 'password14', '567890123'),
--(15, 'account15', 'password15', '678901234'),
--(16, 'account16', 'password16', '678901234'),
--(17, 'account17', 'password17', '678901234'),
--(18, 'account18', 'password18', '678901234'),
--(19, 'account19', 'password19', '678901234'),
--(20, 'account20', 'password20', '678901234'),
--(21, 'account21', 'password21', '678901234'),
--(22, 'account22', 'password22', '678901234'),
--(23, 'account23', 'password23', '678901234'),
--(24, 'account24', 'password24', '678901234'),
--(25, 'account25', 'password25', '678901234'),
--(26, 'account26', 'password26', '678901234'),
--(27, 'account27', 'password27', '678901234'),
--(28, 'account28', 'password28', '678901234'),
--(29, 'account29', 'password29', '678901234'),
--(30, 'account30', 'password30', '678901234'),
--(31, 'account31', 'password31', '789012345'),
--(32, 'account32', 'password32', '789012345'),
--(33, 'account33', 'password33', '789012345'),
--(34, 'account34', 'password34', '789012345'),
--(35, 'account35', 'password35', '789012345'),
--(36, 'account36', 'password36', '789012345'),
--(37, 'account37', 'password37', '789012345'),
--(38, 'account38', 'password38', '789012345'),
--(39, 'account39', 'password39', '789012345'),
--(40, 'account40', 'password40', '789012345'),
--(41, 'account41', 'password41', '789012345'),
--(42, 'account42', 'password42', '789012345'),
--(43, 'account43', 'password43', '789012345'),
--(44, 'account44', 'password44', '789012345'),
--(45, 'account45', 'password45', '789012345'),
--(46, 'account46', 'password46', '890123456'),
--(47, 'account47', 'password47', '890123456'),
--(48, 'account48', 'password48', '890123456'),
--(49, 'account49', 'password49', '890123456'),
--(50, 'account50', 'password50', '890123456'),
--(51, 'account51', 'password51', '890123456'),
--(52, 'account52', 'password52', '890123456'),
--(53, 'account53', 'password53', '890123456'),
--(54, 'account54', 'password54', '890123456'),
--(55, 'account55', 'password55', '890123456'),
--(56, 'account56', 'password56', '890123456'),
--(57, 'account57', 'password57', '890123456'),
--(58, 'account58', 'password58', '890123456'),
--(59, 'account59', 'password59', '890123456'),
--(60, 'account60', 'password60', '890123456'),
--(61, 'account61', 'password61', '901234567'),
--(62, 'account62', 'password62', '901234567'),
--(63, 'account63', 'password63', '901234567'),
--(64, 'account64', 'password64', '901234567'),
--(65, 'account65', 'password65', '901234567'),
--(66, 'account66', 'password66', '901234567'),
--(67, 'account67', 'password67', '901234567'),
--(68, 'account68', 'password68', '901234567'),
--(69, 'account69', 'password69', '901234567'),
--(70, 'account70', 'password70', '901234567'),
--(71, 'account71', 'password71', '901234567'),
--(72, 'account72', 'password72', '901234567'),
--(73, 'account73', 'password73', '901234567'),
--(74, 'account74', 'password74', '901234567'),
--(75, 'account75', 'password75', '901234567');
--
INSERT IGNORE INTO city (id, code_city, name_city, description, image) VALUES
(1, 29, 'Hà Nội', 'Thủ đô của Việt Nam', 'https://media.tacdn.com/media/attractions-content--1x-1/0b/18/9a/2c.jpg'),
(2, 30, 'Hải Phòng', 'Thành phố cảng lớn nhất miền Bắc','https://www.thecoth.com/wp-content/uploads/2021/08/thanh-pho-hai-phong-thuoc-tinh-nao.jpg' ),
(3, 12, 'Đà Nẵng', 'Thành phố biển nổi tiếng','https://touringhighlights.com/wp-content/uploads/2020/07/Cau-Rong-Dragon-Bridge-Da-Nang-Vietnam.jpg'),
(4, 45, 'TP. Hồ Chí Minh', 'Thành phố lớn nhất Việt Nam', 'https://tphcm.dangcongsan.vn/DATA/72/IMAGES/2021/04/ttxxvnktvn.jpg'),
(5, 34, 'Cần Thơ', 'Thành phố trực thuộc trung ương', 'https://static-images.vnncdn.net/files/publish/2023/5/2/507fc9a2936c4c32157d-177.jpg'),
(6, 25, 'Nha Trang', 'Thành phố biển nổi tiếng về du lịch','https://st.ielts-fighter.com/src/ielts-fighter-image/2023/03/21/86c908f5-ba02-4865-a79b-8c671ecd9981.jpg'),
(7, 12, 'Đà Lạt', 'Thành phố ngàn hoa','https://1.bp.blogspot.com/-K_dwkb40TU0/XzXcrAl7t0I/AAAAAAAAXBc/C-Pe3qkh5AUBfvNOUCpUWK-G082hVk5QQCNcBGAsYHQ/s1920/Da%2BLat4.jpg'),
(8, 11, 'Huế', 'Cố đô của Việt Nam', 'https://mettavoyage.com/wp-content/webp-express/webp-images/uploads/2023/05/image046_2021061710288.jpeg.webp'),
(9, 10, 'Vũng Tàu', 'Thành phố biển gần TP. Hồ Chí Minh', 'https://cdn3.ivivu.com/2022/09/T%E1%BB%95ng-quan-du-l%E1%BB%8Bch-V%C5%A9ng-T%C3%A0u-ivivu.jpg'),
(10, 39, 'Thành phố Bà Rịa', 'Thành phố thuộc tỉnh Bà Rịa - Vũng Tàu', 'https://ktmt.vnmediacdn.com/images/2022/09/19/33-1663548963-ba-ria-vung-tau-thuc-hien-dong-bo-nhieu-giai-phap-phat-trien1588170854.jpg'),
(11, 47, 'Hà Giang', 'Tỉnh cực Bắc của Việt Nam', 'https://impresstravel.com/wp-content/uploads/2021/04/Ha-Giang-Trekking-Tours.jpg'),
(12, 60, 'Lào Cai', 'Nơi có Sapa','https://media.truyenhinhdulich.vn/upload/news/923_thanh_pho_lao_cai_la_khu_du_lich_cap_tinh.png'),
(13, 50, 'Yên Bái', 'Tỉnh miền núi phía Bắc', 'https://mediaim.expedia.com/destination/1/00a0a4dd5b665d7275530d3313e3b1c1.jpg?impolicy=fcrop&w=1040&h=580&q=mediumHigh'),
(14, 51, 'Tuyên Quang', 'Nơi có nhiều truyền thống lịch sử', 'https://www.originvietnam.com/wp-content/uploads/Tuyen_Quang_Header.jpg'),
(15, 23, 'Thái Nguyên', 'Thành phố thuộc tỉnh Thái Nguyên','https://tourre.vn/wp-content/uploads/2020/12/ho-nui-coc-thai-nguyen.jpg');




INSERT IGNORE INTO hotel (id, address, description, email, hotline, total_room, name_hotel, star_point, city_id) VALUES
(1, '44B Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 'Khách sạn sang trọng với kiến trúc độc đáo', 'hotel1_hanoi@example.com', '02439330500', 268, 'Sofitel Legend Metropole Hanoi', 5, 1),
(2, '1 Phan Đình Phùng, Ba Đình, Hà Nội', 'Khách sạn cổ điển giữa lòng Hà Nội', 'hotel2_hanoi@example.com', '02438253355', 117, 'Hanoi Daewoo Hotel', 5, 1),
(3, '40 Cát Linh, Đống Đa, Hà Nội', 'Khách sạn 5 sao với hồ bơi ngoài trời', 'hotel3_hanoi@example.com', '02437330888', 300, 'Pullman Hanoi Hotel', 4, 1),
(4, '15 Ngọc Khánh, Ba Đình, Hà Nội', 'Khách sạn hiện đại với các tiện nghi cao cấp', 'hotel4_hanoi@example.com', '02437661000', 377, 'Lotte Hotel Hanoi', 5, 1),
(5, '83A Lý Thường Kiệt, Hoàn Kiếm, Hà Nội', 'Khách sạn boutique giữa trung tâm Hà Nội', 'hotel5_hanoi@example.com', '02438258888', 81, 'Hilton Hanoi Opera', 4, 1),
(6, '2 Công trường Lam Sơn, Quận 1, TP. Hồ Chí Minh', 'Khách sạn sang trọng với vị trí trung tâm', 'hotel1_hcm@example.com', '02838234999', 244, 'Park Hyatt Saigon', 5, 4),
(7, '76 Lê Lai, Quận 1, TP. Hồ Chí Minh', 'Khách sạn nổi tiếng với phong cách hiện đại', 'hotel2_hcm@example.com', '02838223888', 286, 'New World Saigon Hotel', 5, 4),
(8, '88 Đồng Khởi, Quận 1, TP. Hồ Chí Minh', 'Khách sạn cổ điển với tiện nghi hiện đại', 'hotel3_hcm@example.com', '02838292018', 533, 'Sheraton Saigon Hotel & Towers', 5, 4),
(9, '141 Nguyễn Huệ, Quận 1, TP. Hồ Chí Minh', 'Khách sạn 5 sao với tầm nhìn toàn cảnh thành phố', 'hotel4_hcm@example.com', '02838223366', 321, 'Rex Hotel Saigon', 5, 4),
(10, '39 Lê Duẩn, Quận 1, TP. Hồ Chí Minh', 'Khách sạn đẳng cấp quốc tế tại trung tâm thành phố', 'hotel5_hcm@example.com', '02838272828', 286, 'InterContinental Saigon', 5, 4),
(11, '36 Bạch Đằng, Hải Châu, Đà Nẵng', 'Khách sạn hiện đại bên sông Hàn', 'hotel1_danang@example.com', '02363655888', 323, 'Novotel Danang Premier Han River', 5, 3),
 (12, '118-120 Võ Nguyên Giáp, Ngũ Hành Sơn, Đà Nẵng', 'Khách sạn sang trọng với bãi biển riêng', 'hotel2_danang@example.com', '02363953707', 200, 'Furama Resort Danang', 5, 3),
(13, '210 Võ Nguyên Giáp, Sơn Trà, Đà Nẵng', 'Khách sạn 5 sao bên bờ biển', 'hotel3_danang@example.com', '02363885868', 187, 'TMS Hotel Da Nang Beach', 4, 3),
(14, '35 Trần Hưng Đạo, Sơn Trà, Đà Nẵng', 'Khách sạn với hồ bơi vô cực', 'hotel4_danang@example.com', '02363928866', 242, 'Hilton Da Nang', 5, 3),
(15, '38 Bạch Đằng, Hải Châu, Đà Nẵng', 'Khách sạn boutique giữa trung tâm thành phố', 'hotel5_danang@example.com', '02363882828', 234, 'Vinpearl Condotel Riverfront Da Nang', 4, 3),
(16, '60A Nguyễn Bỉnh Khiêm, Hải An, Hải Phòng', 'Khách sạn với tiện nghi cao cấp', 'hotel1_haiphong@example.com', '02253835100', 350, 'Melia Vinpearl Hai Phong Rivera', 5, 2),
(17, '4 Trần Phú, Ngô Quyền, Hải Phòng', 'Khách sạn phong cách cổ điển', 'hotel2_haiphong@example.com', '02253731121', 125, 'Avani Hai Phong Harbour View', 4, 2),
(18, '12 Trần Phú, Ngô Quyền, Hải Phòng', 'Khách sạn sang trọng tại trung tâm Hải Phòng', 'hotel3_haiphong@example.com', '02253827127', 152, 'Mercure Hai Phong', 4, 2),
(19, '275 Lạch Tray, Ngô Quyền, Hải Phòng', 'Khách sạn 4 sao với bể bơi ngoài trời', 'hotel4_haiphong@example.com', '02253823145', 100, 'Lac Long Hotel', 4, 2),
(20, '12 Hồng Bàng, Hồng Bàng, Hải Phòng', 'Khách sạn hiện đại với hồ bơi', 'hotel5_haiphong@example.com', '02253721114', 200, 'Manoir Des Arts Hotel', 5, 2),
(21, '209 Đường 30/4, Ninh Kiều, Cần Thơ', 'Khách sạn 5 sao ven sông Hậu', 'hotel1_cantho@example.com', '02923824306', 225, 'Vinpearl Hotel Can Tho', 5, 5),
(22, '208 Đường 30/4, Ninh Kiều, Cần Thơ', 'Khách sạn sang trọng bên bờ sông', 'hotel2_cantho@example.com', '02923812121', 165, 'Ninh Kieu Riverside Hotel', 4, 5),
(23, '22 Đường Hai Bà Trưng, Ninh Kiều, Cần Thơ', 'Khách sạn 4 sao với tầm nhìn đẹp', 'hotel3_cantho@example.com', '02923719999', 107, 'TTC Hotel Premium Can Tho', 4, 5),
(24, '61 Trần Văn Khéo, Ninh Kiều, Cần Thơ', 'Khách sạn hiện đại với hồ bơi ngoài trời', 'hotel4_cantho@example.com', '02923761001', 150, 'Muong Thanh Luxury Can Tho Hotel', 5, 5),
(25, '51 Đường 30/4, Ninh Kiều, Cần Thơ', 'Khách sạn boutique giữa trung tâm thành phố', 'hotel5_cantho@example.com', '02923827127', 90, 'International Hotel Can Tho', 4, 5),
(26, '32-34 Trần Phú, Nha Trang', 'Khách sạn 5 sao bên bờ biển Nha Trang', 'hotel1_nhatrang@example.com', '02583834444', 120, 'InterContinental Nha Trang', 5, 6),
(27, '96-98 Trần Phú, Nha Trang', 'Khách sạn sang trọng với bể bơi vô cực', 'hotel2_nhatrang@example.com', '02583818181', 200, 'Vinpearl Luxury Nha Trang', 5, 6),
(28, '38 Trần Phú, Nha Trang', 'Khách sạn hiện đại với vị trí trung tâm', 'hotel3_nhatrang@example.com', '02583856565', 300, 'Sheraton Nha Trang', 5, 6),
(29, '8 Hùng Vương, Nha Trang', 'Khách sạn 4 sao gần biển', 'hotel4_nhatrang@example.com', '02583866666', 150, 'Havana Nha Trang', 4, 6),
(30, '88/8 Trần Phú, Nha Trang', 'Khách sạn boutique hiện đại', 'hotel5_nhatrang@example.com', '02583877777', 100, 'Asia Paradise Hotel', 3, 6),
(31, '42 Nguyễn Văn Trỗi, Đà Lạt', 'Khách sạn đẹp giữa trung tâm Đà Lạt', 'hotel1_dalat@example.com', '02633551999', 100, 'Terracotta Hotel Dalat', 4, 7),
(32, '36 Đường Hùng Vương, Đà Lạt', 'Khách sạn 5 sao với tầm nhìn đẹp', 'hotel2_dalat@example.com', '02633554445', 120, 'Da Lat Palace Hotel', 5, 7),
(33, '19 Đường Trần Hưng Đạo, Đà Lạt', 'Khách sạn sang trọng gần hồ Xuân Hương', 'hotel3_dalat@example.com', '02633555555', 80, 'Novotel Dalat', 4, 7),
(34, '66 Đường Trần Quốc Toản, Đà Lạt', 'Khách sạn yên tĩnh với không gian thoáng đãng', 'hotel4_dalat@example.com', '02633533333', 60, 'Sofia Hotel Dalat', 3, 7),
(35, '39 Đường Đống Đa, Đà Lạt', 'Khách sạn giá rẻ với dịch vụ tốt', 'hotel5_dalat@example.com', '02633544332', 40, 'An Vui Hotel', 2, 7),
(36, '01 Lê Lợi, Phú Nhuận, Huế', 'Khách sạn 5 sao lịch sử gần sông Hương', 'hotel1_hue@example.com', '02343812222', 140, 'Azerai La Residence Hue', 5, 8),
(37, '94 Hùng Vương, Huế', 'Khách sạn sang trọng với hồ bơi', 'hotel2_hue@example.com', '02343816666', 100, 'Indochine Palace', 5, 8),
(38, '10 Trần Hưng Đạo, Huế', 'Khách sạn 4 sao với dịch vụ tốt', 'hotel3_hue@example.com', '02343814444', 120, 'Grand Hue Hotel', 4, 8),
(39, '15 Lê Lợi, Huế', 'Khách sạn hiện đại giữa trung tâm', 'hotel4_hue@example.com', '02343812111', 80, 'Moonlight Hotel Hue', 4, 8),
(40, '18 Đường Lê Ngô Cát, Huế', 'Khách sạn giá rẻ gần phố cổ', 'hotel5_hue@example.com', '02343815555', 60, 'Sunny Hotel Hue', 3, 8),
(41, '28 Thùy Vân, Vũng Tàu', 'Khách sạn 5 sao bên bờ biển', 'hotel1_vungtau@example.com', '02543895555', 200, 'Imperial Hotel Vung Tau', 5, 9),
(42, '1A Lê Hồng Phong, Vũng Tàu', 'Khách sạn hiện đại với tiện nghi đầy đủ', 'hotel2_vungtau@example.com', '02543896444', 150, 'Pullman Vung Tau', 5, 9),
(43, '31 Trần Phú, Vũng Tàu', 'Khách sạn 4 sao gần biển', 'hotel3_vungtau@example.com', '02543895111', 120, 'Vung Tau Intourco Resort', 4, 9),
(44, '92 Hạ Long, Vũng Tàu', 'Khách sạn sang trọng với bể bơi', 'hotel4_vungtau@example.com', '02543894222', 90, 'Lan Rung Resort & Spa', 4, 9),
(45, '33 Lê Hồng Phong, Vũng Tàu', 'Khách sạn giá rẻ với vị trí trung tâm', 'hotel5_vungtau@example.com', '02543892333', 50, 'Hai Duong Hotel', 2, 9),
(46, '123 Hùng Vương, Hà Giang', 'Khách sạn với tầm nhìn tuyệt đẹp', 'hotel1_hagiang@example.com', '02193854444', 60, 'Hà Giang Hotel', 3, 11),
(47, '45 Nguyễn Trãi, Hà Giang', 'Khách sạn bình dân với dịch vụ tốt', 'hotel2_hagiang@example.com', '02193853333', 50, 'Thien Ha Hotel', 2, 11),
(48, '88 Đường Trần Hưng Đạo, Hà Giang', 'Khách sạn sạch sẽ và tiện lợi', 'hotel3_hagiang@example.com', '02193852222', 40, 'Phu Linh Hotel', 2, 11),
(49, '10 Phố Mã Pì Lèng, Hà Giang', 'Khách sạn nằm trong khu du lịch', 'hotel4_hagiang@example.com', '02193851111', 30, 'Mã Pì Lèng Hotel', 1, 11),
(50 , '100 Đường Quang Trung, Hà Giang', 'Khách sạn gần trung tâm thành phố', 'hotel5_hagiang@example.com', '02193850000', 20, 'Hoa Cuong Hotel', 1, 11),
(51, '24 Trần Phú, Lào Cai', 'Khách sạn gần trung tâm, dịch vụ tốt.', 'info@laocaigroup.com', '0214 3838 888', 30, 'Khách sạn Mường Thanh Lào Cai', 4, 12),
(52, '06 Lê Lợi, Lào Cai', 'Khách sạn sang trọng, gần các điểm du lịch.', 'reservation@hotel.com', '0214 3838 999', 40, 'Khách sạn Victoria Sapa', 5, 12),
(53, '10 Hùng Vương, Lào Cai', 'Khách sạn hiện đại với không gian thoải mái.', 'contact@hotel.com', '0214 3838 777', 20, 'Khách sạn Sapa Jade Hill', 3, 12),
(54, '30 Phan Đình Phùng, Lào Cai', 'Khách sạn tiện nghi, phục vụ 24/7.', 'support@laocaigroup.com', '0214 3838 666', 25, 'Khách sạn Sapa Legend Hotel', 4, 12),
(55, '20 Lê Quý Đôn, Lào Cai', 'Khách sạn sạch sẽ, dịch vụ tận tình.', 'info@hotel.com', '0214 3838 555', 15, 'Khách sạn Green View', 3, 12),
(56, '1 Đường Mới, Yên Bái', 'Khách sạn mới xây, tiện nghi đầy đủ.', 'info@yenbaihotel.com', '0216 3838 888', 30, 'Khách sạn Muong Thanh Yên Bái', 4, 13),
(57, '5 Đường Phạm Ngũ Lão, Yên Bái', 'Khách sạn view đẹp, gần sông Hồng.', 'reservation@hotel.com', '0216 3838 999', 35, 'Khách sạn Hương Giang', 5, 13),
(58, '7 Đường Hoàng Quốc Việt, Yên Bái', 'Khách sạn tiện nghi, phục vụ chu đáo.', 'contact@hotel.com', '0216 3838 777', 20, 'Khách sạn Thảo Nguyên', 3, 13),
(59, '10 Đường 30/4, Yên Bái', 'Khách sạn gần các điểm du lịch nổi tiếng.', 'support@yenbaihotel.com', '0216 3838 666', 40, 'Khách sạn Quang Minh', 4, 13),
(60, '15 Đường Bến Lâm, Yên Bái', 'Khách sạn yên tĩnh, thoải mái.', 'info@hotel.com', '0216 3838 555', 25, 'Khách sạn Anh Đào', 3, 13),
(61, '1 Đường Phú Thịnh, Tuyên Quang', 'Khách sạn sang trọng với dịch vụ tốt.', 'info@tuyenquanghotel.com', '0217 3838 888', 30, 'Khách sạn Tuyên Quang 1', 4, 14),
(62, '3 Đường 30/4, Tuyên Quang', 'Khách sạn tiện nghi, dịch vụ chuyên nghiệp.', 'reservation@hotel.com', '0217 3838 999', 25, 'Khách sạn Tuyên Quang 2', 5, 14),
(63, '5 Đường Nguyễn Trãi, Tuyên Quang', 'Khách sạn hiện đại với không gian thoải mái.', 'contact@hotel.com', '0217 3838 777', 20, 'Khách sạn Tuyên Quang 3', 4, 14),
(64, '10 Đường Trường Chinh, Tuyên Quang', 'Khách sạn sạch sẽ, phục vụ 24/7.', 'support@tuyenquanghotel.com', '0217 3838 666', 15, 'Khách sạn Tuyên Quang 4', 3, 14),
(65, '15 Đường Lê Lợi, Tuyên Quang', 'Khách sạn gần khu vui chơi giải trí.', 'info@hotel.com', '0217 3838 555', 10, 'Khách sạn Tuyên Quang 5', 4, 14),

(66, '1 Đường Hoàng Văn Thụ, Thái Nguyên', 'Khách sạn hiện đại với dịch vụ tốt.', 'info@thainguyenhotel.com', '0218 3838 888', 30, 'Khách sạn Thái Nguyên 1', 4, 15),
(67, '3 Đường Lê Hồng Phong, Thái Nguyên', 'Khách sạn gần trung tâm thành phố.', 'reservation@hotel.com', '0218 3838 999', 25, 'Khách sạn Thái Nguyên 2', 5, 15),
(68, '5 Đường 3/2, Thái Nguyên', 'Khách sạn có nhiều dịch vụ tiện ích.', 'contact@hotel.com', '0218 3838 777', 20, 'Khách sạn Thái Nguyên 3', 3, 15),
(69, '10 Đường Trường Chinh, Thái Nguyên', 'Khách sạn gần các khu vui chơi.', 'support@thainguyenhotel.com', '0218 3838 666', 15, 'Khách sạn Thái Nguyên 4', 4, 15),
(70, '15 Đường Phan Đình Phùng, Thái Nguyên', 'Khách sạn với không gian thoải mái.', 'info@hotel.com', '0218 3838 555', 10, 'Khách sạn Thái Nguyên 5', 5, 15),
(71, '123 Trần Hưng Đạo, Bà Rịa', 'Khách sạn gần biển, dịch vụ chu đáo.', 'info@bariahotel.com', '0254 3838 888', 40, 'Khách sạn Hoàng Anh', 4, 10),
(72, '456 Lê Duẩn, Bà Rịa', 'Khách sạn sang trọng, view biển đẹp.', 'reservation@hotel.com', '0254 3838 999', 35, 'Khách sạn Paradise', 5, 10),
(73, '789 Nguyễn Thái Học, Bà Rịa', 'Khách sạn tiện nghi, phục vụ 24/7.', 'contact@hotel.com', '0254 3838 777', 30, 'Khách sạn Đại Dương', 3, 10),
(74, '321 Hùng Vương, Bà Rịa', 'Khách sạn gần trung tâm, dịch vụ tốt.', 'support@bariahotel.com', '0254 3838 666', 20, 'Khách sạn Thanh Bình', 4, 10),
(75, '654 Phạm Hồng Thái, Bà Rịa', 'Khách sạn sạch sẽ, thoải mái.', 'info@hotel.com', '0254 3838 555', 15, 'Khách sạn Bà Rịa', 3, 10);
----
--INSERT IGNORE INTO hotel_image (link_image, hotel_id) VALUES
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 1),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 2),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 3),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 4),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 5),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 6),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 7),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 8),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 9),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 10),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 11),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 12),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 13),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 14),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 15),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 16),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 17),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 18),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 19),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 20),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 21),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 22),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 23),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 24),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 25),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 26),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 27),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 28),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 29),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 30),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 31),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 32),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 33),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 34),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 35),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 36),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 37),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 38),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 39),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 40),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 41),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 45),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 46),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 47),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 48),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 49),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 50),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 51),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 52),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 53),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 54),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 55),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 56),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 57),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 58),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 59),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 60),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 61),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 62),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 63),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 64),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 65),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 66),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 67),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 68),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 69),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 70),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 71),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 72),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 73),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 74),
--('https://diadiemvietnam.vn/wp-content/uploads/2023/07/Nam-Cuong-Nam-Dinh-Hotel.jpg', 75);
--
--
----
----
--INSERT IGNORE  INTO hotel_room (id, amount_room, image, number_room_last, number_of_booking, number_people, price_per_night, status, type_room, hotel_id) VALUES
--(1, 10, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 10, 0, 2, 300000, 'CONPHONG', 'Standard', 1),
--(2, 10, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 10, 0, 2, 500000, 'CONPHONG', 'Deluxe', 1),
--(3, 10, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 10, 0, 2, 700000, 'CONPHONG', 'Suite', 1),
--
--(4, 15, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 15, 0, 2, 320000, 'CONPHONG', 'Standard', 2),
--(5, 15, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 15, 0, 2, 520000, 'CONPHONG', 'Deluxe', 2),
--(6, 15, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 15, 0, 2, 720000, 'CONPHONG', 'Suite', 2),
--
--(7, 20, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 20, 0, 2, 340000, 'CONPHONG', 'Standard', 3),
--(8, 20, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg',20, 0, 2, 540000, 'CONPHONG', 'Deluxe', 3),
--(9, 20, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 20, 0, 2, 740000, 'CONPHONG', 'Suite', 3),
--
--(10, 25, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 25, 0, 2, 360000, 'CONPHONG', 'Standard', 4),
--(11, 25, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 25, 0, 2, 560000, 'CONPHONG', 'Deluxe', 4),
--(12, 25, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 25, 0, 2, 760000, 'CONPHONG', 'Suite', 4),
--
--(13, 30, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 30, 0, 2, 380000, 'CONPHONG', 'Standard', 5),
--(14, 30, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 30, 0, 2, 580000, 'CONPHONG', 'Deluxe', 5),
--(15, 30, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 30, 0, 2, 780000, 'CONPHONG', 'Suite', 5),
--
--(16, 35, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 35, 0, 2, 400000, 'CONPHONG', 'Standard', 6),
--(17, 35, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 35, 0, 2, 600000, 'CONPHONG', 'Deluxe', 6),
--(18, 35, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 35, 0, 2, 800000, 'CONPHONG', 'Suite', 6),
--
--(19, 40, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 40, 0, 2, 420000, 'CONPHONG', 'Standard', 7),
--(20, 40, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 40, 0, 2, 620000, 'CONPHONG', 'Deluxe', 7),
--(21, 40, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 40, 0, 2, 820000, 'CONPHONG', 'Suite', 7),
--
--(22, 45, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 45, 0, 2, 440000, 'CONPHONG', 'Standard', 8),
--(23, 45, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 45, 0, 2, 640000, 'CONPHONG', 'Deluxe', 8),
--(24, 45, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 45, 0, 2, 840000, 'CONPHONG', 'Suite', 8),
--
--(25, 50, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 50, 0, 2, 460000, 'CONPHONG', 'Standard', 9),
--(26, 50, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 50, 0, 2, 660000, 'CONPHONG', 'Deluxe', 9),
--(27, 50, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 50, 0, 2, 860000, 'CONPHONG', 'Suite', 9),
--
--(28, 55, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 55, 0, 2, 480000, 'CONPHONG', 'Standard', 10),
--(29, 55, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 55, 0, 2, 680000, 'CONPHONG', 'Deluxe', 10),
--(30, 55, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 55, 0, 2, 880000, 'CONPHONG', 'Suite', 10),
--
--(31, 60, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 60, 0, 2, 500000, 'CONPHONG', 'Standard', 11),
--(32, 60, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 60, 0, 2, 700000, 'CONPHONG', 'Deluxe', 11),
--(33, 60, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 60, 0, 2, 900000, 'CONPHONG', 'Suite', 11),
--
--(34, 65, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 65, 0, 2, 520000, 'CONPHONG', 'Standard', 12),
--(35, 65, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 65, 0, 2, 720000, 'CONPHONG', 'Deluxe', 12),
--(36, 65, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 65, 0, 2, 920000, 'CONPHONG', 'Suite', 12),
--
--(37, 70, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 70, 0, 2, 540000, 'CONPHONG', 'Standard', 13),
--(38, 70, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 70, 0, 2, 740000, 'CONPHONG', 'Deluxe', 13),
--(39, 70, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 70, 0, 2, 940000, 'CONPHONG', 'Suite', 13),
--
--(40, 75, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 75, 0, 2, 560000, 'CONPHONG', 'Standard', 14),
--(41, 75, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 75, 0, 2, 760000, 'CONPHONG', 'Deluxe', 14),
--(42, 75, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 75, 0, 2, 960000, 'CONPHONG', 'Suite', 14),
--
--(43, 80, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 80, 0, 2, 580000, 'CONPHONG', 'Standard', 15),
--(44, 80, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 80, 0, 2, 780000, 'CONPHONG', 'Deluxe', 15),
--(45, 80, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 80, 0, 2, 1080000, 'CONPHONG', 'Suite', 15),
--
--(46, 85, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 46, 0, 2, 600000, 'CONPHONG', 'Standard', 16),
--(47, 85, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 47, 0, 2, 800000, 'CONPHONG', 'Deluxe', 16),
--(48, 85, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 48, 0, 2, 1000000, 'CONPHONG', 'Suite', 16),
--
--(49, 90, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 49, 0, 2, 620000, 'CONPHONG', 'Standard', 17),
--(50, 90, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 50, 0, 2, 820000, 'CONPHONG', 'Deluxe', 17),
--(51, 90, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 51, 0, 2, 1020000, 'CONPHONG', 'Suite', 17),
--
--(52, 95, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 52, 0, 2, 640000, 'CONPHONG', 'Standard', 18),
--(53, 95, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 53, 0, 2, 840000, 'CONPHONG', 'Deluxe', 18),
--(54, 95, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 54, 0, 2, 1040000, 'CONPHONG', 'Suite', 18),
--
--(55, 100, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 55, 0, 2, 660000, 'CONPHONG', 'Standard', 19),
--(56, 100, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 56, 0, 2, 860000, 'CONPHONG', 'Deluxe', 19),
--(57, 100, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 57, 0, 2, 1060000, 'CONPHONG', 'Suite', 19),
--
--(58, 105, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 58, 0, 2, 680000, 'CONPHONG', 'Standard', 20),
--(59, 105, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 59, 0, 2, 880000, 'CONPHONG', 'Deluxe', 20),
--(60, 105, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 60, 0, 2, 1080000, 'CONPHONG', 'Suite', 20),
--(61, 110, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 61, 0, 2, 700000, 'CONPHONG', 'Standard', 21),
--(62, 110, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 62, 0, 2, 900000, 'CONPHONG', 'Deluxe', 21),
--(63, 110, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 63, 0, 2, 1100000, 'CONPHONG', 'Suite', 21),
--
--(64, 115, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 64, 0, 2, 720000, 'CONPHONG', 'Standard', 22),
--(65, 115, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 65, 0, 2, 920000, 'CONPHONG', 'Deluxe', 22),
--(66, 115, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 66, 0, 2, 1120000, 'CONPHONG', 'Suite', 22),
--
--(67, 120, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 67, 0, 2, 740000, 'CONPHONG', 'Standard', 23),
--(68, 120, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 68, 0, 2, 940000, 'CONPHONG', 'Deluxe', 23),
--(69, 120, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 69, 0, 2, 1140000, 'CONPHONG', 'Suite', 23),
--
--(70, 125, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 70, 0, 2, 760000, 'CONPHONG', 'Standard', 24),
--(71, 125, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 71, 0, 2, 960000, 'CONPHONG', 'Deluxe', 24),
--(72, 125, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 72, 0, 2, 1160000, 'CONPHONG', 'Suite', 24),
--
--(73, 130, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 73, 0, 2, 780000, 'CONPHONG', 'Standard', 25),
--(74, 130, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 74, 0, 2, 980000, 'CONPHONG', 'Deluxe', 25),
--(75, 130, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 75, 0, 2, 1180000, 'CONPHONG', 'Suite', 25),
--(76, 135, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 76, 0, 2, 800000, 'CONPHONG', 'Standard', 26),
--(77, 135, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 77, 0, 2, 1000000, 'CONPHONG', 'Deluxe', 26),
--(78, 135, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 78, 0, 2, 1200000, 'CONPHONG', 'Suite', 26),
--
--(79, 140, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 79, 0, 2, 820000, 'CONPHONG', 'Standard', 27),
--(80, 140, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 80, 0, 2, 1020000, 'CONPHONG', 'Deluxe', 27),
--(81, 140, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 81, 0, 2, 1220000, 'CONPHONG', 'Suite', 27),
--
--(82, 145, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 82, 0, 2, 840000, 'CONPHONG', 'Standard', 28),
--(83, 145, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 83, 0, 2, 1040000, 'CONPHONG', 'Deluxe', 28),
--(84, 145, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 84, 0, 2, 1240000, 'CONPHONG', 'Suite', 28),
--
--(85, 150, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 85, 0, 2, 860000, 'CONPHONG', 'Standard', 29),
--(86, 150, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 86, 0, 2, 1060000, 'CONPHONG', 'Deluxe', 29),
--(87, 150, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 87, 0, 2, 1260000, 'CONPHONG', 'Suite', 29),
--
--(88, 155, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 88, 0, 2, 880000, 'CONPHONG', 'Standard', 30),
--(89, 155, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 89, 0, 2, 1080000, 'CONPHONG', 'Deluxe', 30),
--(90, 155, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 90, 0, 2, 1280000, 'CONPHONG', 'Suite', 30),
--(91, 160, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 91, 0, 2, 900000, 'CONPHONG', 'Standard', 31),
--(92, 160, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 92, 0, 2, 1100000, 'CONPHONG', 'Deluxe', 31),
--(93, 160, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 93, 0, 2, 1300000, 'CONPHONG', 'Suite', 31),
--
--(94, 165, 'hhttps://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 94, 0, 2, 920000, 'CONPHONG', 'Standard', 32),
--(95, 165, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 95, 0, 2, 1120000, 'CONPHONG', 'Deluxe', 32),
--(96, 165, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 96, 0, 2, 1320000, 'CONPHONG', 'Suite', 32),
--
--(97, 170, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 97, 0, 2, 940000, 'CONPHONG', 'Standard', 33),
--(98, 170, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 98, 0, 2, 1140000, 'CONPHONG', 'Deluxe', 33),
--(99, 170, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 99, 0, 2, 1340000, 'CONPHONG', 'Suite', 33),
--
--(100, 175, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 100, 0, 2, 960000, 'CONPHONG', 'Standard', 34),
--(101, 175, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 101, 0, 2, 1160000, 'CONPHONG', 'Deluxe', 34),
--(102, 175, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 102, 0, 2, 1360000, 'CONPHONG', 'Suite', 34),
--
--(103, 180, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 103, 0, 2, 980000, 'CONPHONG', 'Standard', 35),
--(104, 180, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 104, 0, 2, 1180000, 'CONPHONG', 'Deluxe', 35),
--(105, 180, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 105, 0, 2, 1380000, 'CONPHONG', 'Suite', 35),
--(106, 185, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 106, 0, 2, 1000000, 'CONPHONG', 'Standard', 36),
--(107, 185, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 107, 0, 2, 1200000, 'CONPHONG', 'Deluxe', 36),
--(108, 185, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 108, 0, 2, 1400000, 'CONPHONG', 'Suite', 36),
--
--(109, 190, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 109, 0, 2, 1020000, 'CONPHONG', 'Standard', 37),
--(110, 190, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 110, 0, 2, 1220000, 'CONPHONG', 'Deluxe', 37),
--(111, 190, 'hhttps://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 111, 0, 2, 1420000, 'CONPHONG', 'Suite', 37),
--
--(112, 195, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 112, 0, 2, 1040000, 'CONPHONG', 'Standard', 38),
--(113, 195, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 113, 0, 2, 1240000, 'CONPHONG', 'Deluxe', 38),
--(114, 195, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 114, 0, 2, 1440000, 'CONPHONG', 'Suite', 38),
--
--(115, 200, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 115, 0, 2, 1060000, 'CONPHONG', 'Standard', 39),
--(116, 200, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 116, 0, 2, 1260000, 'CONPHONG', 'Deluxe', 39),
--(117, 200, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 117, 0, 2, 1460000, 'CONPHONG', 'Suite', 39),
--
--(118, 205, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 118, 0, 2, 1080000, 'CONPHONG', 'Standard', 40),
--(119, 205, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 119, 0, 2, 1280000, 'CONPHONG', 'Deluxe', 40),
--(120, 205, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 120, 0, 2, 1480000, 'CONPHONG', 'Suite', 40),
--(121, 210, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 121, 0, 2, 1100000, 'CONPHONG', 'Standard', 41),
--(122, 210, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 122, 0, 2, 1300000, 'CONPHONG', 'Deluxe', 41),
--(123, 210, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 123, 0, 2, 1500000, 'CONPHONG', 'Suite', 41),
--
--(124, 215, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 124, 0, 2, 1120000, 'CONPHONG', 'Standard', 42),
--(125, 215, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 125, 0, 2, 1320000, 'CONPHONG', 'Deluxe', 42),
--(126, 215, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 126, 0, 2, 1520000, 'CONPHONG', 'Suite', 42),
--
--(127, 220, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 127, 0, 2, 1140000, 'CONPHONG', 'Standard', 43),
--(128, 220, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 128, 0, 2, 1340000, 'CONPHONG', 'Deluxe', 43),
--(129, 220, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 129, 0, 2, 1540000, 'CONPHONG', 'Suite', 43),
--
--(130, 225, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 130, 0, 2, 1160000, 'CONPHONG', 'Standard', 44),
--(131, 225, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 131, 0, 2, 1360000, 'CONPHONG', 'Deluxe', 44),
--(132, 225, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 132, 0, 2, 1560000, 'CONPHONG', 'Suite', 44),
--
--(133, 230, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 133, 0, 2, 1180000, 'CONPHONG', 'Standard', 45),
--(134, 230, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 134, 0, 2, 1380000, 'CONPHONG', 'Deluxe', 45),
--(135, 230, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 135, 0, 2, 1580000, 'CONPHONG', 'Suite', 45),
--(136, 235, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 136, 0, 2, 1200000, 'CONPHONG', 'Standard', 46),
--(137, 235, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 137, 0, 2, 1400000, 'CONPHONG', 'Deluxe', 46),
--(138, 235, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 138, 0, 2, 1600000, 'CONPHONG', 'Suite', 46),
--
--(139, 240, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 139, 0, 2, 1220000, 'CONPHONG', 'Standard', 47),
--(140, 240, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 140, 0, 2, 1420000, 'CONPHONG', 'Deluxe', 47),
--(141, 240, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 141, 0, 2, 1620000, 'CONPHONG', 'Suite', 47),
--
--(142, 245, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 142, 0, 2, 1240000, 'CONPHONG', 'Standard', 48),
--(143, 245, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 143, 0, 2, 1440000, 'CONPHONG', 'Deluxe', 48),
--(144, 245, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 144, 0, 2, 1640000, 'CONPHONG', 'Suite', 48),
--
--(145, 250, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 145, 0, 2, 1260000, 'CONPHONG', 'Standard', 49),
--(146, 250, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 146, 0, 2, 1460000, 'CONPHONG', 'Deluxe', 49),
--(147, 250, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 147, 0, 2, 1660000, 'CONPHONG', 'Suite', 49),
--
--(148, 255, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 148, 0, 2, 1280000, 'CONPHONG', 'Standard', 50),
--(149, 255, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 149, 0, 2, 1480000, 'CONPHONG', 'Deluxe', 50),
--(150, 255, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 150, 0, 2, 1680000, 'CONPHONG', 'Suite', 50),
--(151, 260, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 151, 0, 2, 1300000, 'CONPHONG', 'Standard', 51),
--(152, 260, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 152, 0, 2, 1500000, 'CONPHONG', 'Deluxe', 51),
--(153, 260, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 153, 0, 2, 1700000, 'CONPHONG', 'Suite', 51),
--
--(154, 265, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 154, 0, 2, 1320000, 'CONPHONG', 'Standard', 52),
--(155, 265, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 155, 0, 2, 1520000, 'CONPHONG', 'Deluxe', 52),
--(156, 265, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 156, 0, 2, 1720000, 'CONPHONG', 'Suite', 52),
--
--(157, 270, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 157, 0, 2, 1340000, 'CONPHONG', 'Standard', 53),
--(158, 270, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 158, 0, 2, 1540000, 'CONPHONG', 'Deluxe', 53),
--(159, 270, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 159, 0, 2, 1740000, 'CONPHONG', 'Suite', 53),
--
--(160, 275, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 160, 0, 2, 1360000, 'CONPHONG', 'Standard', 54),
--(161, 275, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 161, 0, 2, 1560000, 'CONPHONG', 'Deluxe', 54),
--(162, 275, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 162, 0, 2, 1760000, 'CONPHONG', 'Suite', 54),
--
--(163, 280, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 163, 0, 2, 1380000, 'CONPHONG', 'Standard', 55),
--(164, 280, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 164, 0, 2, 1580000, 'CONPHONG', 'Deluxe', 55),
--(165, 280, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 165, 0, 2, 1780000, 'CONPHONG', 'Suite', 55),
--(166, 285, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 166, 0, 2, 1400000, 'CONPHONG', 'Standard', 56),
--(167, 285, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 167, 0, 2, 1600000, 'CONPHONG', 'Deluxe', 56),
--(168, 285, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 168, 0, 2, 1800000, 'CONPHONG', 'Suite', 56),
--
--(169, 290, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 169, 0, 2, 1420000, 'CONPHONG', 'Standard', 57),
--(170, 290, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 170, 0, 2, 1620000, 'CONPHONG', 'Deluxe', 57),
--(171, 290, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 171, 0, 2, 1820000, 'CONPHONG', 'Suite', 57),
--
--(172, 295, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 172, 0, 2, 1440000, 'CONPHONG', 'Standard', 58),
--(173, 295, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 173, 0, 2, 1640000, 'CONPHONG', 'Deluxe', 58),
--(174, 295, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 174, 0, 2, 1840000, 'CONPHONG', 'Suite', 58),
--
--(175, 300, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 175, 0, 2, 1460000, 'CONPHONG', 'Standard', 59),
--(176, 300, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 176, 0, 2, 1660000, 'CONPHONG', 'Deluxe', 59),
--(177, 300, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 177, 0, 2, 1860000, 'CONPHONG', 'Suite', 59),
--
--(178, 305, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 178, 0, 2, 1480000, 'CONPHONG', 'Standard', 60),
--(179, 305, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 179, 0, 2, 1680000, 'CONPHONG', 'Deluxe', 60),
--(180, 305, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 180, 0, 2, 1880000, 'CONPHONG', 'Suite', 60),
--(181, 310, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 181, 0, 2, 1500000, 'CONPHONG', 'Standard', 61),
--(182, 310, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 182, 0, 2, 1700000, 'CONPHONG', 'Deluxe', 61),
--(183, 310, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 183, 0, 2, 1900000, 'CONPHONG', 'Suite', 61),
--
--(184, 315, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 184, 0, 2, 1520000, 'CONPHONG', 'Standard', 62),
--(185, 315, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 185, 0, 2, 1720000, 'CONPHONG', 'Deluxe', 62),
--(186, 315, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 186, 0, 2, 1920000, 'CONPHONG', 'Suite', 62),
--
--(187, 320, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 187, 0, 2, 1540000, 'CONPHONG', 'Standard', 63),
--(188, 320, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 188, 0, 2, 1740000, 'CONPHONG', 'Deluxe', 63),
--(189, 320, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 189, 0, 2, 1940000, 'CONPHONG', 'Suite', 63),
--
--(190, 325, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 190, 0, 2, 1560000, 'CONPHONG', 'Standard', 64),
--(191, 325, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 191, 0, 2, 1760000, 'CONPHONG', 'Deluxe', 64),
--(192, 325, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 192, 0, 2, 1960000, 'CONPHONG', 'Suite', 64),
--
--(193, 330, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 193, 0, 2, 1580000, 'CONPHONG', 'Standard', 65),
--(194, 330, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 194, 0, 2, 1780000, 'CONPHONG', 'Deluxe', 65),
--(195, 330, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 195, 0, 2, 1980000, 'CONPHONG', 'Suite', 65),
--(196, 335, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 196, 0, 2, 1600000, 'CONPHONG', 'Standard', 66),
--(197, 335, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 197, 0, 2, 1800000, 'CONPHONG', 'Deluxe', 66),
--(198, 335, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 198, 0, 2, 2000000, 'CONPHONG', 'Suite', 66),
--
--(199, 340, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 199, 0, 2, 1620000, 'CONPHONG', 'Standard', 67),
--(200, 340, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 200, 0, 2, 1820000, 'CONPHONG', 'Deluxe', 67),
--(201, 340, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 201, 0, 2, 2020000, 'CONPHONG', 'Suite', 67),
--
--(202, 345, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 202, 0, 2, 1640000, 'CONPHONG', 'Standard', 68),
--(203, 345, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 203, 0, 2, 1840000, 'CONPHONG', 'Deluxe', 68),
--(204, 345, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 204, 0, 2, 2040000, 'CONPHONG', 'Suite', 68),
--
--(205, 350, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 205, 0, 2, 1660000, 'CONPHONG', 'Standard', 69),
--(206, 350, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 206, 0, 2, 1860000, 'CONPHONG', 'Deluxe', 69),
--(207, 350, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 207, 0, 2, 2060000, 'CONPHONG', 'Suite', 69),
--
--(208, 355, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 208, 0, 2, 1680000, 'CONPHONG', 'Standard', 70),
--(209, 355, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 209, 0, 2, 1880000, 'CONPHONG', 'Deluxe', 70),
--(210, 355, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 210, 0, 2, 2080000, 'CONPHONG', 'Suite', 70),
--(211, 360, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 211, 0, 2, 1700000, 'CONPHONG', 'Standard', 71),
--(212, 360, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 212, 0, 2, 1900000, 'CONPHONG', 'Deluxe', 71),
--(213, 360, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 213, 0, 2, 2100000, 'CONPHONG', 'Suite', 71),
--
--(214, 365, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 214, 0, 2, 1720000, 'CONPHONG', 'Standard', 72),
--(215, 365, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 215, 0, 2, 1920000, 'CONPHONG', 'Deluxe', 72),
--(216, 365, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 216, 0, 2, 2120000, 'CONPHONG', 'Suite', 72),
--
--(217, 370, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 217, 0, 2, 1740000, 'CONPHONG', 'Standard', 73),
--(218, 370, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 218, 0, 2, 1940000, 'CONPHONG', 'Deluxe', 73),
--(219, 370, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 219, 0, 2, 2140000, 'CONPHONG', 'Suite', 73),
--
--(220, 375, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 220, 0, 2, 1760000, 'CONPHONG', 'Standard', 74),
--(221, 375, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 221, 0, 2, 1960000, 'CONPHONG', 'Deluxe', 74),
--(222, 375, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 222, 0, 2, 2160000, 'CONPHONG', 'Suite', 74),
--
--(223, 380, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 223, 0, 2, 1780000, 'CONPHONG', 'Standard', 75),
--(224, 380, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 224, 0, 2, 1980000, 'CONPHONG', 'Deluxe', 75),
--(225, 380, 'https://asiky.com/files/images/Article/tin-tuc/chup-anh-khach-san.jpg', 225, 0, 2, 2180000, 'CONPHONG', 'Suite', 75);
--
--INSERT IGNORE INTO hotel_policy (id, description, is_free, is_related_fee, name_policy) VALUES
--(1, 'Free cancellation within 24 hours', 1, 0, 'Free Cancellation'),
--(2, 'No refund for non-refundable bookings', 0, 1, 'No Refund'),
--(3, 'Breakfast included', 1, 0, 'Breakfast Included'),
--(4, 'Late check-out available', 1, 0, 'Late Check-out'),
--(5, 'Early check-in available', 1, 0, 'Early Check-in'),
--(6, 'Pets allowed with fee', 0, 1, 'Pet Policy');
----ALTER TABLE  hotel_facility ADD description varchar(255);
--INSERT IGNORE INTO hotel_facility (id, description, name_hotel_facility) VALUES
--(1, 'Free Wi-Fi in all areas', 'Wi-Fi'),
--(2, '24-hour front desk service', 'Front Desk'),
--(3, 'Swimming pool available', 'Pool'),
--(4, 'Fitness center available', 'Fitness Center'),
--(5, 'Spa and wellness center', 'Spa'),
--(6, 'Conference room available', 'Conference Room');
--INSERT IGNORE INTO hotel_room_features (id, description, name_features) VALUES
--(1, 'Air conditioning and heating', 'Air Conditioning'),
--(2, 'Flat-screen TV with cable channels', 'TV'),
--(3, 'Minibar and refrigerator', 'Minibar'),
--(4, 'Coffee maker available', 'Coffee Maker'),
--(5, 'Hairdryer and toiletries', 'Bathroom Amenities'),
--(6, 'Safety deposit box', 'Safety Deposit Box');
--INSERT IGNORE INTO hotel_room_features_room (hotel_room_features_id, hotel_room_id) VALUES
--(1, 1),
--(2, 1),
--(3, 1),
--(1, 2),
--(2, 2),
--(3, 2),
--(1, 3),
--(2, 3),
--(4, 3),
--(1, 4),
--(2, 4),
--(5, 4),
--(1, 5),
--(2, 5),
--(6, 5),
--(1, 6),
--(3, 6);
--INSERT IGNORE INTO hotel_policy_hotel (hotel_policy_id, hotel_id) VALUES
--(1, 1),
--(2, 1),
--(3, 2),
--(4, 3),
--(1, 3);
--
--
--INSERT IGNORE INTO hotel_hotel_facility_hotel (hotel_facility_id, hotel_id) VALUES
--(1, 1),
--(2, 1),
--(3, 2),
--(4, 3);




