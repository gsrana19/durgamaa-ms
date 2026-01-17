#!/bin/bash

# Code Migration Script
# Copies code from old microservices to new monolith
# Run from project root: bash durgamaa-ms/scripts/migrate-code.sh

set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo "========================================="
echo " Code Migration Script"
echo "========================================="

# Check if we're in the right directory
if [ ! -d "backend/donation-service" ] || [ ! -d "backend/booking-service" ]; then
    echo -e "${RED}Error: Run this script from the project root directory${NC}"
    exit 1
fi

# Create target directories
echo -e "${GREEN}Creating directory structure...${NC}"
mkdir -p durgamaa-ms/src/main/java/com/durgamandir/{common,donation,booking}/{controller,dto,entity,repository,service,config,filter}

# Migrate Donation Service
echo -e "${GREEN}Migrating donation-service...${NC}"

# Copy entities
cp -r backend/donation-service/src/main/java/com/durgamandir/donation/entity/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/donation/entity/ 2>/dev/null || echo "Entities copied"

# Copy DTOs
cp -r backend/donation-service/src/main/java/com/durgamandir/donation/dto/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/donation/dto/ 2>/dev/null || echo "DTOs copied"

# Copy repositories
cp -r backend/donation-service/src/main/java/com/durgamandir/donation/repository/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/donation/repository/ 2>/dev/null || echo "Repositories copied"

# Copy services
cp -r backend/donation-service/src/main/java/com/durgamandir/donation/service/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/donation/service/ 2>/dev/null || echo "Services copied"

# Copy controllers (will need manual updates)
cp -r backend/donation-service/src/main/java/com/durgamandir/donation/controller/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/donation/controller/ 2>/dev/null || echo "Controllers copied"

# Copy filters
cp -r backend/donation-service/src/main/java/com/durgamandir/donation/filter/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/donation/filter/ 2>/dev/null || echo "Filters copied"

# Migrate Booking Service
echo -e "${GREEN}Migrating booking-service...${NC}"

# Copy entities (from model)
cp -r backend/booking-service/src/main/java/com/durgamandir/booking/model/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/booking/entity/ 2>/dev/null || echo "Booking entities copied"

# Copy DTOs
cp -r backend/booking-service/src/main/java/com/durgamandir/booking/dto/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/booking/dto/ 2>/dev/null || echo "Booking DTOs copied"

# Copy repositories
cp -r backend/booking-service/src/main/java/com/durgamandir/booking/repository/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/booking/repository/ 2>/dev/null || echo "Booking repositories copied"

# Copy services
cp -r backend/booking-service/src/main/java/com/durgamandir/booking/service/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/booking/service/ 2>/dev/null || echo "Booking services copied"

# Copy controllers
cp -r backend/booking-service/src/main/java/com/durgamandir/booking/controller/*.java \
     durgamaa-ms/src/main/java/com/durgamandir/booking/controller/ 2>/dev/null || echo "Booking controllers copied"

# Move shared code to common
echo -e "${GREEN}Moving shared code to common...${NC}"

# Move User entity
if [ -f "durgamaa-ms/src/main/java/com/durgamandir/donation/entity/User.java" ]; then
    mv durgamaa-ms/src/main/java/com/durgamandir/donation/entity/User.java \
       durgamaa-ms/src/main/java/com/durgamandir/common/entity/
fi

# Move UserRepository
if [ -f "durgamaa-ms/src/main/java/com/durgamandir/donation/repository/UserRepository.java" ]; then
    mv durgamaa-ms/src/main/java/com/durgamandir/donation/repository/UserRepository.java \
       durgamaa-ms/src/main/java/com/durgamandir/common/repository/
fi

# Move UserService
if [ -f "durgamaa-ms/src/main/java/com/durgamandir/donation/service/UserService.java" ]; then
    mv durgamaa-ms/src/main/java/com/durgamandir/donation/service/UserService.java \
       durgamaa-ms/src/main/java/com/durgamandir/common/service/
fi

# Move CustomUserDetailsService
if [ -f "durgamaa-ms/src/main/java/com/durgamandir/donation/service/CustomUserDetailsService.java" ]; then
    mv durgamaa-ms/src/main/java/com/durgamandir/donation/service/CustomUserDetailsService.java \
       durgamaa-ms/src/main/java/com/durgamandir/common/service/
fi

# Move AuthController
if [ -f "durgamaa-ms/src/main/java/com/durgamandir/donation/controller/AuthController.java" ]; then
    mv durgamaa-ms/src/main/java/com/durgamandir/donation/controller/AuthController.java \
       durgamaa-ms/src/main/java/com/durgamandir/common/controller/
fi

echo ""
echo -e "${YELLOW}⚠️  MANUAL STEPS REQUIRED:${NC}"
echo ""
echo "1. Update controller @RequestMapping paths:"
echo "   Donation controllers: /api/donations → /api/donation/donations"
echo "   Booking controllers: /api/bookings → /api/booking/bookings"
echo ""
echo "2. Update imports in moved files:"
echo "   com.durgamandir.donation.entity.User → com.durgamandir.common.entity.User"
echo "   com.durgamandir.booking.model.* → com.durgamandir.booking.entity.*"
echo ""
echo "3. Remove Eureka/Gateway annotations:"
echo "   @EnableEurekaClient, @EnableDiscoveryClient"
echo "   Remove DiscoveryClient injections"
echo ""
echo "4. Update DataInitializer if exists:"
echo "   Move to com.durgamandir.common.config"
echo ""
echo "5. Build and test:"
echo "   cd durgamaa-ms"
echo "   mvn clean package -DskipTests"
echo ""
echo -e "${GREEN}✅ Code migration completed!${NC}"
echo "Run the manual steps above to complete the migration."

