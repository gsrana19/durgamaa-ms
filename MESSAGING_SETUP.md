# SMS/WhatsApp Messaging Setup

This application uses Twilio to send SMS and WhatsApp messages to donors when their donations are verified or rejected.

## Configuration

### 1. Get Twilio Credentials

1. Sign up for a Twilio account at https://www.twilio.com
2. Get your Account SID and Auth Token from the Twilio Console
3. Get a Twilio phone number (for SMS)
4. Set up WhatsApp Business API (for WhatsApp messages)

### 2. Configure Environment Variables

Add these environment variables to your system or `.env` file:

```bash
TWILIO_ACCOUNT_SID=your_account_sid_here
TWILIO_AUTH_TOKEN=your_auth_token_here
TWILIO_FROM_NUMBER=+1234567890  # Your Twilio phone number
TWILIO_WHATSAPP_FROM=whatsapp:+14155238886  # Twilio WhatsApp number (format: whatsapp:+1234567890)
```

### 3. Application Configuration

The messaging is configured in `application.yml`:

```yaml
app:
  messaging:
    enabled: true  # Set to false to disable messaging
    temple-name: Durga Maa Temple
    twilio:
      account-sid: ${TWILIO_ACCOUNT_SID:}
      auth-token: ${TWILIO_AUTH_TOKEN:}
      from-number: ${TWILIO_FROM_NUMBER:}
      whatsapp-from: ${TWILIO_WHATSAPP_FROM:}
```

### 4. How It Works

- When admin verifies or rejects a donation, the system:
  1. Tries to send WhatsApp message first (if number format supports it)
  2. Falls back to SMS if WhatsApp fails or number doesn't support it
  3. The message includes the admin's note

### 5. Message Format

**Verified:**
```
Namaste from Durga Maa Temple!

✅ Your donation of ₹1000 has been verified.

Note: [Admin's note here]

Thank you for your contribution!
Jai Maa Durga 🙏
```

**Rejected:**
```
Namaste from Durga Maa Temple!

❌ Your donation of ₹1000 has been rejected.

Note: [Admin's note here]

Thank you for your contribution!
Jai Maa Durga 🙏
```

### 6. Testing Without Twilio

If you don't have Twilio credentials yet, the system will:
- Still process verify/reject actions
- Log warnings about missing credentials
- Not send actual messages

You can test the full flow once Twilio is configured.

### 7. Cost Considerations

- Twilio SMS: ~$0.0075 per message (varies by country)
- Twilio WhatsApp: ~$0.005 per message (varies)
- Consider enabling messaging only in production

### 8. Disable Messaging

To disable messaging temporarily, set in `application.yml`:

```yaml
app:
  messaging:
    enabled: false
```

