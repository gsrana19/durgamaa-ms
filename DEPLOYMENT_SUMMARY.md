# Deployment Summary - Durga Maa Mandir Monolith

## Files Created

### Core Application Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven dependencies and build configuration |
| `src/main/java/com/durgamandir/DurgaMandirApplication.java` | Main Spring Boot application class |
| `src/main/resources/application.yml` | Default configuration (H2 database) |
| `src/main/resources/application-prod.yml` | Production configuration (MySQL) |

### Configuration Files

| File | Purpose |
|------|---------|
| `src/main/java/com/durgamandir/common/config/SecurityConfig.java` | Spring Security configuration |
| `src/main/java/com/durgamandir/common/config/PasswordEncoderConfig.java` | Password encoder bean |
| `src/main/java/com/durgamandir/common/config/WebConfig.java` | CORS and static resource handlers |

### Deployment Files

| File | Purpose |
|------|---------|
| `deployment/durgamaa-backend.service` | Systemd service file for EC2 |
| `deployment/nginx-durgamaa.conf` | Nginx reverse proxy configuration |
| `deployment/setup-ec2.sh` | Automated EC2 setup script |

### Documentation

| File | Purpose |
|------|---------|
| `README.md` | Main project documentation |
| `MIGRATION_GUIDE.md` | Detailed migration instructions |
| `DEPLOYMENT_SUMMARY.md` | This file - quick reference |

### Scripts

| File | Purpose |
|------|---------|
| `scripts/migrate-code.sh` | Automates code copying from old services |
| `scripts/update-frontend-api.sh` | Updates frontend API calls |

---

## Quick Start Commands

### Build Application
```bash
cd durgamaa-ms
mvn clean package -DskipTests
```
**Output**: `target/durgamaa-backend.jar`

### Run Locally (H2)
```bash
java -jar target/durgamaa-backend.jar
```
**Access**: http://localhost:8081

### Run with MySQL
```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_USERNAME=durgamaa_user
export DB_PASSWORD=your_password

java -jar target/durgamaa-backend.jar --spring.profiles.active=prod
```

### Build Frontend
```bash
cd frontend
npm install
npm run build
```
**Output**: `build/` directory

---

## Deployment Checklist

### Pre-Deployment

- [ ] Code migrated from old services
- [ ] Controllers updated with new paths (`/api/donation/**`, `/api/booking/**`)
- [ ] Eureka/Gateway dependencies removed
- [ ] Application builds successfully: `mvn clean package`
- [ ] Frontend updated with new API endpoints
- [ ] Frontend builds successfully: `npm run build`

### EC2 Setup

- [ ] EC2 instance running (Ubuntu 22.04)
- [ ] Java 17 installed
- [ ] MySQL 8 installed
- [ ] Nginx installed
- [ ] Directories created: `/home/ubuntu/durgamaa/{jars,logs,uploads,backups}`

### Database Configuration

- [ ] MySQL database `durgamaa` created
- [ ] MySQL user `durgamaa_user` created with permissions
- [ ] Environment variables set in `/etc/environment`:
  - `DB_HOST=localhost`
  - `DB_PORT=3306`
  - `DB_USERNAME=durgamaa_user`
  - `DB_PASSWORD=your_secure_password`

### Backend Deployment

- [ ] JAR uploaded to `/home/ubuntu/durgamaa/jars/durgamaa-backend.jar`
- [ ] Systemd service installed: `/etc/systemd/system/durgamaa-backend.service`
- [ ] Service enabled: `sudo systemctl enable durgamaa-backend`
- [ ] Service started: `sudo systemctl start durgamaa-backend`
- [ ] Health check passed: `curl http://localhost:8081/actuator/health`

### Frontend Deployment

- [ ] Frontend build uploaded to `/home/ubuntu/durgamaa/frontend/`
- [ ] Nginx config installed: `/etc/nginx/sites-available/durgamaa`
- [ ] Symlink created: `/etc/nginx/sites-enabled/durgamaa`
- [ ] Nginx config tested: `sudo nginx -t`
- [ ] Nginx reloaded: `sudo systemctl reload nginx`
- [ ] Website accessible: `curl http://localhost/`

### Post-Deployment Verification

- [ ] Backend running: `sudo systemctl status durgamaa-backend`
- [ ] Frontend loading correctly
- [ ] API calls working (check browser DevTools Network tab)
- [ ] Login functionality working
- [ ] File uploads working
- [ ] Database persisting data
- [ ] Logs being written: `/home/ubuntu/durgamaa/logs/`

---

## Key Configuration Changes

### API Endpoint Changes

| Old (Microservices) | New (Monolith) |
|---------------------|----------------|
| `http://localhost:8082/api/donations` | `/api/donation/donations` |
| `http://localhost:8082/api/events` | `/api/donation/events` |
| `http://localhost:8082/api/updates` | `/api/donation/updates` |
| `http://localhost:8082/api/bookings` | `/api/booking/bookings` |
| `http://localhost:8082/api/services` | `/api/booking/services` |

### Environment Variables Required

```bash
# Add to /etc/environment on EC2
DB_HOST=localhost
DB_PORT=3306
DB_USERNAME=durgamaa_user
DB_PASSWORD=YOUR_SECURE_PASSWORD_HERE
```

### JVM Configuration (t3.micro)

```bash
JAVA_OPTS="-Xms128m -Xmx256m -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+UseStringDeduplication"
```

---

## Database Schema

**Database Name**: `durgamaa`

**Key Tables**:
- `users` - Admin authentication
- `donations` - Donation records
- `events` - Temple events
- `event_media` - Event images
- `updates` - Construction updates
- `expenses` - Expense tracking
- `team_members` - Team member profiles
- `seva_bookings` - Seva bookings
- `special_puja` - Special puja bookings
- `abhishekam_bookings` - Abhishekam bookings
- `flower_offerings` - Flower offering bookings
- `morning_aarti_visits` - Morning aarti visits
- `countries`, `states`, `districts`, `thanas`, `villages` - Location hierarchy

---

## Monitoring Commands

### Application Status
```bash
sudo systemctl status durgamaa-backend
sudo systemctl restart durgamaa-backend
sudo systemctl stop durgamaa-backend
```

### View Logs
```bash
# Application logs
tail -f /home/ubuntu/durgamaa/logs/backend.log
tail -f /home/ubuntu/durgamaa/logs/backend.err

# Nginx logs
tail -f /var/log/nginx/access.log
tail -f /var/log/nginx/error.log
```

### Health Check
```bash
# Direct backend
curl http://localhost:8081/actuator/health

# Via Nginx
curl http://localhost/actuator/health
```

### Resource Usage
```bash
# Memory usage
free -h
htop

# Disk usage
df -h

# Check Java process
ps aux | grep java
```

---

## Common Issues and Solutions

### Issue: Application won't start
```bash
# Check Java version
java -version  # Must be 17+

# Check environment variables
printenv | grep DB_

# Check logs
tail -f /home/ubuntu/durgamaa/logs/backend.err
```

### Issue: Database connection failed
```bash
# Test MySQL connection
mysql -u durgamaa_user -p durgamaa

# Check MySQL is running
sudo systemctl status mysql

# Check user permissions
mysql -u root -p -e "SHOW GRANTS FOR 'durgamaa_user'@'localhost';"
```

### Issue: Nginx 502 Bad Gateway
```bash
# Check backend is running
curl http://localhost:8081/actuator/health

# Check Nginx error log
sudo tail -f /var/log/nginx/error.log

# Verify proxy_pass configuration
sudo nginx -t
```

### Issue: Out of Memory
```bash
# Check current memory usage
free -h

# Reduce JVM heap size
# Edit /etc/systemd/system/durgamaa-backend.service
# Change -Xmx256m to -Xmx200m

sudo systemctl daemon-reload
sudo systemctl restart durgamaa-backend
```

### Issue: Frontend API calls failing
```bash
# Check browser DevTools Console for errors
# Verify API_BASE_URL in frontend/.env is "/api"
# Check Nginx proxy configuration
# Verify backend is accessible: curl http://localhost:8081/api/donation/donations
```

---

## Backup Procedures

### Database Backup
```bash
# Create backup
mysqldump -u durgamaa_user -p durgamaa > \
  /home/ubuntu/durgamaa/backups/durgamaa_$(date +%Y%m%d_%H%M%S).sql

# Restore backup
mysql -u durgamaa_user -p durgamaa < \
  /home/ubuntu/durgamaa/backups/durgamaa_20260111_120000.sql
```

### Application Backup
```bash
# Backup everything
tar -czf /home/ubuntu/durgamaa-backup-$(date +%Y%m%d).tar.gz \
  /home/ubuntu/durgamaa/jars \
  /home/ubuntu/durgamaa/uploads \
  /home/ubuntu/durgamaa/logs

# Restore
tar -xzf /home/ubuntu/durgamaa-backup-20260111.tar.gz -C /
```

---

## Performance Optimization

### MySQL Optimization (t3.micro)
```ini
# /etc/mysql/mysql.conf.d/durgamaa.cnf
[mysqld]
innodb_buffer_pool_size = 128M
max_connections = 50
query_cache_size = 8M
```

### Nginx Optimization
```nginx
# /etc/nginx/nginx.conf
worker_processes 1;
worker_connections 512;
gzip on;
gzip_types text/css application/javascript application/json;
```

### JVM Optimization
```bash
# Already configured in systemd service
-Xms128m -Xmx256m
-XX:+UseG1GC
-XX:MaxGCPauseMillis=100
-XX:+UseStringDeduplication
```

---

## Security Checklist

- [ ] Changed default admin password
- [ ] MySQL root password set
- [ ] Database user has limited permissions (not root)
- [ ] Firewall configured (UFW)
- [ ] HTTPS configured with SSL certificate (optional)
- [ ] Session timeout configured (5 minutes)
- [ ] File upload size limited (10MB)
- [ ] Nginx security headers configured

---

## Rollback Plan

If deployment fails:

1. **Stop new backend**:
   ```bash
   sudo systemctl stop durgamaa-backend
   ```

2. **Restore old microservices** (if still available):
   ```bash
   # Start old services on different ports
   ```

3. **Restore database from backup**:
   ```bash
   mysql -u durgamaa_user -p durgamaa < backup.sql
   ```

4. **Revert Nginx configuration**:
   ```bash
   sudo rm /etc/nginx/sites-enabled/durgamaa
   sudo systemctl reload nginx
   ```

---

## Support Contacts

- **Application Logs**: `/home/ubuntu/durgamaa/logs/`
- **Database Logs**: `/var/log/mysql/error.log`
- **Nginx Logs**: `/var/log/nginx/`

---

## Next Steps After Deployment

1. **Monitor performance** for 24-48 hours
2. **Check logs** regularly for errors
3. **Set up automated backups** (cron job)
4. **Configure CloudWatch** for alerts
5. **Set up SSL certificate** (Let's Encrypt)
6. **Test disaster recovery** procedure
7. **Document any custom configurations**
8. **Archive old microservices** code

---

## Success Criteria

✅ **Deployment is successful when**:
- Backend health check returns 200 OK
- Frontend loads without errors
- API calls work in browser
- Admin login works
- File uploads work
- Data persists after restart
- Logs are being written
- Memory usage is stable (<800MB)

---

**Last Updated**: 2026-01-11
**Version**: 1.0.0

