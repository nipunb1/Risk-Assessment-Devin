#!/bin/bash

echo "Setting up Enterprise Risk Intelligence Platform infrastructure..."

echo "Starting Docker Compose services..."
docker-compose up -d

echo "Waiting for services to start..."
sleep 30

echo "Creating Kafka topics..."
docker exec kafka kafka-topics --create --topic market-data-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
docker exec kafka kafka-topics --create --topic risk-alert-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

echo "Waiting for Elasticsearch to be ready..."
until curl -s http://localhost:9200/_cluster/health | grep -q '"status":"green\|yellow"'; do
  echo "Waiting for Elasticsearch..."
  sleep 5
done

echo "Creating Elasticsearch index template..."
curl -X PUT "localhost:9200/_index_template/risk-intelligence-template" -H 'Content-Type: application/json' -d'
{
  "index_patterns": ["risk-intelligence-*"],
  "template": {
    "settings": {
      "number_of_shards": 1,
      "number_of_replicas": 0
    },
    "mappings": {
      "properties": {
        "riskId": { "type": "long" },
        "riskDate": { "type": "date" },
        "riskType": { "type": "keyword" },
        "riskProbability": { "type": "keyword" },
        "riskDesc": { "type": "text", "analyzer": "standard" },
        "riskStatus": { "type": "keyword" },
        "riskRemarks": { "type": "text" },
        "source": { "type": "keyword" },
        "indexedAt": { "type": "date" }
      }
    }
  }
}'

echo "Creating initial Elasticsearch index..."
curl -X PUT "localhost:9200/risk-intelligence-index"

echo "Infrastructure setup complete!"
echo "Services available at:"
echo "- Kafka: localhost:9092"
echo "- Elasticsearch: http://localhost:9200"
echo "- Kibana: http://localhost:5601"
echo "- Redis: localhost:6379"
echo "- Hadoop NameNode: http://localhost:9870"
echo "- MySQL: localhost:3306"
