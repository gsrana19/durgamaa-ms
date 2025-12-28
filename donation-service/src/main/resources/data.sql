-- Dummy data for testing
-- Note: Passwords are BCrypt hashed (password = "admin123")
-- H2 doesn't support ON CONFLICT, so we use MERGE or check first
MERGE INTO users (id, user_id, password_hash, role, created_at) 
KEY(id)
VALUES (1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lsWy', 'ROLE_ADMIN', CURRENT_TIMESTAMP);

-- Location Data: Country
MERGE INTO countries (id, name, code) 
KEY(id)
VALUES (1, 'India', 'IN');

-- Location Data: States (Jharkhand and a few more for sample)
MERGE INTO states (id, name, code, country_id) 
KEY(id)
VALUES (1, 'Jharkhand', 'JH', 1),
       (2, 'Bihar', 'BR', 1),
       (3, 'West Bengal', 'WB', 1),
       (4, 'Uttar Pradesh', 'UP', 1);

-- Location Data: Districts
MERGE INTO districts (id, name, state_id) 
KEY(id)
VALUES (1, 'Hazaribag', 1),
       (2, 'Ranchi', 1),
       (3, 'Dhanbad', 1),
       (4, 'Patna', 2),
       (5, 'Gaya', 2);

-- Location Data: Thanas
MERGE INTO thanas (id, name, district_id) 
KEY(id)
VALUES (1, 'Ichak', 1),
       (2, 'Hazaribag', 1),
       (3, 'Kanke', 2),
       (4, 'Ranchi', 2);

-- Location Data: Villages
MERGE INTO villages (id, name, thana_id, active) 
KEY(id)
VALUES (1, 'Mangura', 1, true),
       (2, 'Ichak', 1, true),
       (3, 'Katkamsandi', 1, true);

-- Sample donations (only if table is empty) - Updated with location IDs
INSERT INTO donations (name, email, phone, amount, country_id, state_id, district_id, thana_id, village_id, custom_village_name, city, show_public, created_at, updated_at) 
SELECT * FROM (
  SELECT 'Ram Kumar' as name, 'ram@example.com' as email, '+91-9876543210' as phone, 5000.00 as amount, 1 as country_id, 1 as state_id, 1 as district_id, 1 as thana_id, 1 as village_id, NULL as custom_village_name, 'Hazaribag' as city, true as show_public, CURRENT_TIMESTAMP as created_at, CURRENT_TIMESTAMP as updated_at
  UNION ALL
  SELECT 'Priya Sharma', 'priya@example.com', '+91-9876543211', 10000.00, 1, 1, 1, 1, 1, NULL, 'Hazaribag', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
  UNION ALL
  SELECT 'Anonymous Devotee', NULL, '+91-9876543212', 2500.00, 1, 1, 1, 1, 1, NULL, 'Hazaribag', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
  UNION ALL
  SELECT 'Sita Devi', 'sita@example.com', '+91-9876543213', 15000.00, 1, 1, 1, 1, 1, NULL, 'Hazaribag', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
  UNION ALL
  SELECT 'Krishna Das', 'krishna@example.com', '+91-9876543214', 7500.00, 1, 1, 1, 1, 1, NULL, 'Hazaribag', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
) WHERE NOT EXISTS (SELECT 1 FROM donations);

-- Sample updates (only if table is empty)
INSERT INTO updates (title, message, image_url, created_at)
SELECT * FROM (
  SELECT 'Foundation Stone Laid' as title, 'The foundation stone of Durga Mandir was laid with great devotion and ceremony. We are grateful for all the support.' as message, NULL as image_url, CURRENT_TIMESTAMP as created_at
  UNION ALL
  SELECT 'Construction Progress', 'The construction is progressing well. The main structure is taking shape beautifully.', NULL, CURRENT_TIMESTAMP
  UNION ALL
  SELECT 'First Puja Performed', 'The first puja was performed at the construction site. Maa Durga''s blessings are with us.', NULL, CURRENT_TIMESTAMP
) WHERE NOT EXISTS (SELECT 1 FROM updates);
