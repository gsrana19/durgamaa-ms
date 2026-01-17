#!/bin/bash

# EC2 Setup Script for Durga Maa Mandir Backend
# Run this on fresh Ubuntu 22.04 EC2 instance
# Usage: sudo bash setup-ec2.sh

set -e

echo "========================================="
echo " Durga Maa Mandir - EC2 Setup Script"
echo "========================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if running as root
if [[ $EUID -ne 0 ]]; then
   echo -e "${RED}This script must be run as root (use sudo)${NC}" 
   exit 1
fi

echo -e "${GREEN}Step 1: Updating system packages...${NC}"
apt update && apt upgrade -y

echo -e "${GREEN}Step 2: Installing Java 17...${NC}"
apt install -y openjdk-17-jdk
java -version

echo -e "${GREEN}Step 3: Installing MySQL Server...${NC}"
apt install -y mysql-server
systemctl start mysql
systemctl enable mysql

echo -e "${GREEN}Step 4: Installing Nginx...${NC}"
apt install -y nginx
systemctl start nginx
systemctl enable nginx

echo -e "${GREEN}Step 5: Creating application directories...${NC}"
mkdir -p /home/ubuntu/durgamaa/{jars,logs,uploads/{events,team,updates},backups}
chown -R ubuntu:ubuntu /home/ubuntu/durgamaa
chmod 755 /home/ubuntu/durgamaa

echo -e "${GREEN}Step 6: Configuring MySQL...${NC}"
echo -e "${YELLOW}You need to manually configure MySQL:${NC}"
echo "  1. Run: sudo mysql_secure_installation"
echo "  2. Run: sudo mysql -u root -p"
echo "  3. Execute these SQL commands:"
echo "     CREATE DATABASE durgamaa CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
echo "     CREATE USER 'durgamaa_user'@'localhost' IDENTIFIED BY 'YOUR_SECURE_PASSWORD';"
echo "     GRANT ALL PRIVILEGES ON durgamaa.* TO 'durgamaa_user'@'localhost';"
echo "     FLUSH PRIVILEGES;"
echo "     EXIT;"

echo -e "${GREEN}Step 7: Configuring environment variables...${NC}"
echo -e "${YELLOW}Add these to /etc/environment:${NC}"
echo "DB_HOST=localhost"
echo "DB_PORT=3306"
echo "DB_USERNAME=durgamaa_user"
echo "DB_PASSWORD=YOUR_SECURE_PASSWORD"
echo ""
echo -e "${YELLOW}After editing, run: source /etc/environment${NC}"

echo -e "${GREEN}Step 8: Optimizing MySQL for t3.micro...${NC}"
cat > /etc/mysql/mysql.conf.d/durgamaa.cnf << 'EOF'
[mysqld]
# Optimization for t3.micro (1GB RAM)
innodb_buffer_pool_size = 128M
max_connections = 50
query_cache_size = 8M
query_cache_limit = 1M
key_buffer_size = 16M
thread_cache_size = 8
sort_buffer_size = 512K
read_buffer_size = 256K
read_rnd_buffer_size = 512K
EOF

systemctl restart mysql

echo -e "${GREEN}Step 9: Configuring Nginx...${NC}"
cat > /etc/nginx/nginx.conf << 'EOF'
user www-data;
worker_processes 1;
pid /run/nginx.pid;

events {
    worker_connections 512;
}

http {
    sendfile on;
    tcp_nopush on;
    tcp_nodelay on;
    keepalive_timeout 65;
    types_hash_max_size 2048;

    include /etc/nginx/mime.types;
    default_type application/octet-stream;

    access_log /var/log/nginx/access.log;
    error_log /var/log/nginx/error.log;

    gzip on;
    gzip_disable "msie6";

    include /etc/nginx/conf.d/*.conf;
    include /etc/nginx/sites-enabled/*;
}
EOF

nginx -t

echo -e "${GREEN}Step 10: Setting up firewall...${NC}"
ufw allow 22/tcp
ufw allow 80/tcp
ufw allow 443/tcp
echo "y" | ufw enable

echo "========================================="
echo -e "${GREEN}✅ EC2 setup completed!${NC}"
echo "========================================="
echo ""
echo "Next steps:"
echo "  1. Configure MySQL database (see Step 6 above)"
echo "  2. Set environment variables in /etc/environment"
echo "  3. Upload JAR file to /home/ubuntu/durgamaa/jars/"
echo "  4. Install systemd service: sudo cp durgamaa-backend.service /etc/systemd/system/"
echo "  5. Configure Nginx: sudo cp nginx-durgamaa.conf /etc/nginx/sites-available/durgamaa"
echo "  6. Start backend: sudo systemctl start durgamaa-backend"
echo "  7. Upload frontend build to /home/ubuntu/durgamaa/frontend/"
echo ""
echo "Useful commands:"
echo "  - Check backend: sudo systemctl status durgamaa-backend"
echo "  - View logs: tail -f /home/ubuntu/durgamaa/logs/backend.log"
echo "  - Check health: curl http://localhost:8081/actuator/health"
echo ""

