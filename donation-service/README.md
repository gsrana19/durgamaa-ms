# Durga Mandir Donation Service

Backend microservice for managing Mandir Nirmaan Seva (temple construction donations).

## Tech Stack

- Java 17+
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Security (HTTP Session based)
- H2 Database (default) / MySQL (profile-based)
- Maven

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Running the Application

1. **Build the project:**
   ```bash
   cd backend/donation-service
   mvn clean install
   ```

2. **Run with H2 (default):**
   ```bash
   mvn spring-boot:run
   ```
   Server will start on port 8083

3. **Run with MySQL profile:**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=mysql
   ```
   Make sure to configure MySQL in `application-mysql.yml` first.

### H2 Console

- URL: http://localhost:8083/h2-console
- JDBC URL: `jdbc:h2:mem:donationdb`
- Username: `sa`
- Password: (leave empty)

### Default Admin User

The application creates a default admin user on startup (via data.sql):
- **User ID:** `admin`
- **Password:** `admin123`

### API Endpoints

#### Public Endpoints
- `POST /api/donations` - Create donation
  - Request body: `{ name, email?, phone, amount, countryId, stateId, districtId, thanaId, villageId?, customVillageName?, showPublic }`
  - Either `villageId` OR `customVillageName` must be provided
- `GET /api/donations/public` - Get public donations (includes resolved location names)
- `GET /api/donations/stats` - Get donation statistics
- `GET /api/updates/public` - Get public updates

#### Location Endpoints (Public)
- `GET /api/locations/countries` - Get all countries
- `GET /api/locations/states?countryId={id}` - Get states for a country
- `GET /api/locations/districts?stateId={id}` - Get districts for a state
- `GET /api/locations/thanas?districtId={id}` - Get thanas for a district
- `GET /api/locations/villages?thanaId={id}` - Get villages for a thana (returns empty array if none)

#### Auth Endpoints
- `POST /api/auth/login` - Admin login
- `POST /api/auth/logout` - Logout
- `GET /api/auth/check` - Check authentication status
- `POST /api/auth/admin/signup` - Create admin (only if no admin exists)

#### Admin Endpoints (Require ROLE_ADMIN)
- `GET /api/admin/donations` - Get all donations
- `PATCH /api/admin/donations/{id}` - Update donation
- `GET /api/admin/stats` - Get admin statistics
- `GET /api/admin/updates` - Get all updates
- `POST /api/admin/updates` - Create update
- `PUT /api/admin/updates/{id}` - Update update
- `DELETE /api/admin/updates/{id}` - Delete update

### Database Configuration

#### H2 (Default)
Configured in `application-h2.yml`. Uses in-memory database.

#### MySQL
1. Uncomment and configure in `application-mysql.yml`
2. Create database: `durgamandir_db`
3. Run with MySQL profile:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=mysql
   ```

### Sample Data

The application includes sample data in `data.sql`:
- 1 admin user (admin/admin123)
- Location hierarchy:
  - Country: India
  - States: Jharkhand, Bihar, West Bengal, Uttar Pradesh
  - Districts: Hazaribag, Ranchi, Dhanbad (Jharkhand), Patna, Gaya (Bihar)
  - Thanas: Ichak, Hazaribag (Hazaribag district), Kanke, Ranchi (Ranchi district)
  - Villages: Mangura, Ichak, Katkamsandi (Ichak thana)
- 5 sample donations (with location IDs)
- 3 sample construction updates

## Security

- Uses Spring Security with HTTP Session
- Password hashing with BCrypt
- Role-based access control (ROLE_ADMIN)
- CORS enabled for frontend integration

