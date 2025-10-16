#!/bin/bash

# Coffee Vending System Deployment Script

echo "☕ Coffee Vending System - Deployment Script"
echo "============================================="

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

echo "🔍 Checking system requirements..."
echo "✅ Docker: $(docker --version)"
echo "✅ Docker Compose: $(docker-compose --version)"

echo ""
echo "🏗️ Building and starting services..."

# Build and start services
docker-compose up --build -d

echo ""
echo "⏳ Waiting for services to start..."
sleep 30

# Check if services are running
if docker-compose ps | grep -q "Up"; then
    echo "✅ Services started successfully!"
    echo ""
    echo "📋 Service Status:"
    docker-compose ps
    echo ""
    echo "🌐 Application Access:"
    echo "   - MySQL Database: localhost:3306"
    echo "   - Application: Running in container"
    echo ""
    echo "🔧 Management Commands:"
    echo "   - View logs: docker-compose logs -f"
    echo "   - Stop services: docker-compose down"
    echo "   - Restart: docker-compose restart"
else
    echo "❌ Failed to start services. Check logs:"
    docker-compose logs
    exit 1
fi