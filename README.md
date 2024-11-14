# SMS Filter App

This Android app filters incoming SMS messages based on a custom regular expression (regex) you provide and sends matched messages to a specified API endpoint.

## Features

- **Customizable Filters**: Define your own regex pattern to filter relevant SMS messages.
- **API Integration**: Send filtered SMS content to an API endpoint of your choice.
- **Simple Setup**: Easy-to-configure settings for regex and API URL.

## How It Works

1. Set up the regex pattern you want to use to filter SMS messages.
2. Specify the API endpoint URL where you want to send the filtered messages.
3. The app continuously monitors incoming SMS messages and applies the regex filter.
4. If a message matches the regex, the app sends the message content to your API endpoint.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/sms-filter-app.git
   cd sms-filter-app
   ```
2. Open the project in Android Studio.

3. Build and install the app on your Android device.

## Usage

1. Launch the app.
2. Enter the desired regex pattern in the **Filter Regex** field.
3. Enter the API URL in the **API Endpoint** field.
4. Start the SMS filter service to begin monitoring.

The app will now filter incoming messages and send any matching SMS to your specified API.

## Configuration

- **Regex Pattern**: Use any valid regex to match the SMS content.
- **API URL**: Provide a valid HTTP endpoint that accepts POST requests to receive SMS data.

## API Structure

Filtered SMS messages are sent to the API as JSON objects. Here’s an example structure:

```json
{
  "name": "+1234567890",
  "message": "Your OTP is 123456",
}
```
