# Cinema Booking Management System API

A Java Spring Boot REST API prototype for managing cinema bookings with movie titles, seating maps, and ticket reservations.

## Features

- **Movie Management**: Create movies with custom seating configurations
- **Dynamic Seating Maps**: Flexible row and seat arrangements
- **Ticket Booking**: Reserve seats with customer information
- **Real-time Availability**: Track seat status and availability
- **RESTful API**: Complete CRUD operations with JSON responses

## API Endpoints

### Movies
- `POST /api/movies` - Create a new movie
- `GET /api/movies` - Get all movies
- `GET /api/movies/{movieId}` - Get movie by ID
- `GET /api/movies/{movieId}/seats` - Get available seats for a movie

### Bookings
- `POST /api/movies/{movieId}/bookings` - Create a booking
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{bookingId}` - Get booking by ID
- `GET /api/movies/{movieId}/bookings` - Get bookings for a movie

### System
- `GET /api/system/health` - Health check
- `POST /api/system/reset` - Reset all data

## Usage Examples

### Create a Movie
```bash
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Matrix",
    "totalRows": 10,
    "seatsPerRow": 12
  }'
```

### Book Tickets
```bash
curl -X POST http://localhost:8080/api/movies/{movieId}/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "seatIds": ["A1", "A2", "A3"]
  }'
```

### Check Available Seats
```bash
curl http://localhost:8080/api/movies/{movieId}/seats
```

## Running the Application

The application will start on port 8080. Access the API at `http://localhost:8080`