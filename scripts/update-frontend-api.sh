#!/bin/bash

# Frontend API Update Script
# Updates frontend API calls to work with monolithic backend
# Run from project root: bash durgamaa-ms/scripts/update-frontend-api.sh

set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo "========================================="
echo " Frontend API Update Script"
echo "========================================="

# Check if frontend directory exists
if [ ! -d "frontend/src" ]; then
    echo -e "${RED}Error: frontend/src directory not found${NC}"
    exit 1
fi

# Backup api.js
if [ -f "frontend/src/services/api.js" ]; then
    echo -e "${GREEN}Backing up api.js...${NC}"
    cp frontend/src/services/api.js frontend/src/services/api.js.backup
    echo "Backup saved to: frontend/src/services/api.js.backup"
fi

echo -e "${GREEN}Updating API base URL...${NC}"

# Update API base URL in api.js
if [ -f "frontend/src/services/api.js" ]; then
    # Change from http://localhost:8082/api to /api
    sed -i.bak "s|http://localhost:8082/api|/api|g" frontend/src/services/api.js
    sed -i.bak "s|http://localhost:8081/api|/api|g" frontend/src/services/api.js
    
    # Update donation endpoints to include /donation prefix
    sed -i.bak "s|/api/donations|/api/donation/donations|g" frontend/src/services/api.js
    sed -i.bak "s|/api/events|/api/donation/events|g" frontend/src/services/api.js
    sed -i.bak "s|/api/updates|/api/donation/updates|g" frontend/src/services/api.js
    sed -i.bak "s|/api/locations|/api/donation/locations|g" frontend/src/services/api.js
    sed -i.bak "s|/api/sankalpam|/api/donation/sankalpam|g" frontend/src/services/api.js
    sed -i.bak "s|/api/prasad-sponsorships|/api/donation/prasad-sponsorships|g" frontend/src/services/api.js
    sed -i.bak "s|/api/admin|/api/admin|g" frontend/src/services/api.js
    
    # Update booking endpoints to include /booking prefix
    sed -i.bak "s|/api/bookings|/api/booking/bookings|g" frontend/src/services/api.js
    sed -i.bak "s|/api/services|/api/booking/services|g" frontend/src/services/api.js
    sed -i.bak "s|/api/seva-bookings|/api/booking/seva-bookings|g" frontend/src/services/api.js
    
    rm frontend/src/services/api.js.bak
    echo -e "${GREEN}✅ api.js updated${NC}"
else
    echo -e "${YELLOW}⚠️  api.js not found, skipping${NC}"
fi

# Update .env file
echo -e "${GREEN}Updating .env file...${NC}"
if [ -f "frontend/.env" ]; then
    cp frontend/.env frontend/.env.backup
    sed -i.bak "s|REACT_APP_API_URL=http://localhost:8082/api|REACT_APP_API_URL=/api|g" frontend/.env
    sed -i.bak "s|REACT_APP_API_URL=http://localhost:8081/api|REACT_APP_API_URL=/api|g" frontend/.env
    rm frontend/.env.bak
    echo -e "${GREEN}✅ .env updated${NC}"
else
    echo -e "${YELLOW}⚠️  .env not found, creating new one${NC}"
    cat > frontend/.env << 'EOF'
# API Configuration - Use relative URL (Nginx proxies to backend)
REACT_APP_API_URL=/api

# Google Analytics
REACT_APP_GA_MEASUREMENT_ID=G-XXXXXXXXXX
EOF
fi

echo ""
echo -e "${YELLOW}⚠️  MANUAL VERIFICATION REQUIRED:${NC}"
echo ""
echo "1. Check frontend/src/services/api.js for correct endpoints:"
echo "   - Donations: /api/donation/donations"
echo "   - Events: /api/donation/events"
echo "   - Bookings: /api/booking/services"
echo ""
echo "2. Update any hardcoded API URLs in components"
echo ""
echo "3. Test locally with backend running:"
echo "   - Start backend: java -jar durgamaa-ms/target/durgamaa-backend.jar"
echo "   - Start frontend: cd frontend && npm start"
echo "   - Test API calls in browser DevTools"
echo ""
echo "4. Build for production:"
echo "   cd frontend"
echo "   npm run build"
echo ""
echo -e "${GREEN}✅ Frontend API update completed!${NC}"
echo ""
echo "Original files backed up with .backup extension"

