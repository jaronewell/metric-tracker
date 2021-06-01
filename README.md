# metric-tracker

Steps to build and deploy
1. Have maven and docker installed
2. Open shell and navigate to project directory
3. Run "mvn clean install" to build jar in target folder
4. Run "docker build -t metric-tracker ." to build and tag docker image
5. Run "docker run -d -p 8082:8082 metric-tracker" to run application

 
 Can test using below endpoints and sample payloads
 
 POST /metrics  
 {
     "metricName": "test-metric",
     "values": [
         7.0, 2.0, 6.0
     ]
 }
 
 POST /metrics/{metric-name}/values 
 [8, 10.2]
 
GET /metrics
 
GET /metrics/{metric-name}/mean

GET /metrics/{metric-name}/median

GET /metrics/{metric-name}/min

GET /metrics/{metric-name}/max