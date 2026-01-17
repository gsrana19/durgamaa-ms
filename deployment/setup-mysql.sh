#!/bin/bash
# MySQL Database Setup Script for DurgaMaa Application
# Run this on EC2 after installing MySQL

set -e

echo "======================================"
echo "DurgaMaa Database Setup"
echo "======================================"

# Database Configuration
DB_NAME="durgamaa_db"
DB_USER="durgamaa_user"
DB_PASSWORD="${DB_PASSWORD:-DurgaMaa@2024!}"  # Change this!

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${YELLOW}This script will:${NC}"
echo "  1. Create database: $DB_NAME"
echo "  2. Create user: $DB_USER"
echo "  3. Grant privileges"
echo ""
echo -e "${RED}IMPORTANT: Change the default password!${NC}"
echo ""
read -p "Press Enter to continue or Ctrl+C to cancel..."

# Check if MySQL is installed
if ! command -v mysql &> /dev/null; then
    echo -e "${RED}MySQL is not installed!${NC}"
    echo "Install it with: sudo apt-get install mysql-server"
    exit 1
fi

# Create database and user
echo -e "${GREEN}Creating database and user...${NC}"

sudo mysql -e "
-- Create database if not exists
CREATE DATABASE IF NOT EXISTS ${DB_NAME} CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user if not exists
CREATE USER IF NOT EXISTS '${DB_USER}'@'localhost' IDENTIFIED BY '${DB_PASSWORD}';

-- Grant privileges
GRANT ALL PRIVILEGES ON ${DB_NAME}.* TO '${DB_USER}'@'localhost';

-- Flush privileges
FLUSH PRIVILEGES;

-- Show databases
SHOW DATABASES LIKE '${DB_NAME}';
"

echo -e "${GREEN}✓ Database setup complete!${NC}"
echo ""
echo "Database Details:"
echo "  Name: $DB_NAME"
echo "  User: $DB_USER"
echo "  Password: $DB_PASSWORD"
echo ""
echo -e "${YELLOW}Add these to /etc/environment:${NC}"
echo "DB_HOST=localhost"
echo "DB_PORT=3306"
echo "DB_USERNAME=$DB_USER"
echo "DB_PASSWORD=$DB_PASSWORD"
echo ""
echo -e "${GREEN}Tables will be created automatically by Hibernate on first run.${NC}"

