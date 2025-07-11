package com.riskassessment.service;

import com.riskassessment.dto.RiskDTO;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SparkAnalyticsService {
    
    private static final Logger logger = LoggerFactory.getLogger(SparkAnalyticsService.class);
    
    @Autowired
    private SparkSession sparkSession;
    
    @Autowired
    private RiskService riskService;
    
    public Map<String, Object> performRiskAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        
        try {
            List<RiskDTO> risks = riskService.getAllRisks();
            
            if (risks.isEmpty()) {
                logger.warn("No risks found for analytics");
                analytics.put("message", "No risks available for analysis");
                return analytics;
            }
            
            Dataset<Row> riskDataset = createRiskDataset(risks);
            
            analytics.put("totalRisks", riskDataset.count());
            analytics.put("risksByType", analyzeRisksByType(riskDataset));
            analytics.put("risksByProbability", analyzeRisksByProbability(riskDataset));
            analytics.put("risksByStatus", analyzeRisksByStatus(riskDataset));
            analytics.put("riskTrends", analyzeRiskTrends(riskDataset));
            
            logger.info("Completed Spark analytics for {} risks", riskDataset.count());
            
        } catch (Exception e) {
            logger.error("Failed to perform Spark analytics: {}", e.getMessage(), e);
            analytics.put("error", "Failed to perform analytics: " + e.getMessage());
        }
        
        return analytics;
    }
    
    public Map<String, Object> predictRiskTrends() {
        Map<String, Object> predictions = new HashMap<>();
        
        try {
            List<RiskDTO> risks = riskService.getAllRisks();
            Dataset<Row> riskDataset = createRiskDataset(risks);
            
            predictions.put("highRiskPrediction", predictHighRiskEvents(riskDataset));
            predictions.put("riskGrowthRate", calculateRiskGrowthRate(riskDataset));
            predictions.put("criticalAreas", identifyCriticalAreas(riskDataset));
            
            logger.info("Generated risk predictions using Spark ML");
            
        } catch (Exception e) {
            logger.error("Failed to predict risk trends: {}", e.getMessage(), e);
            predictions.put("error", "Failed to generate predictions: " + e.getMessage());
        }
        
        return predictions;
    }
    
    private Dataset<Row> createRiskDataset(List<RiskDTO> risks) {
        return sparkSession.createDataFrame(risks, RiskDTO.class);
    }
    
    private Map<String, Long> analyzeRisksByType(Dataset<Row> dataset) {
        Map<String, Long> typeAnalysis = new HashMap<>();
        
        try {
            Dataset<Row> typeGroups = dataset.groupBy("riskType").count();
            List<Row> typeRows = typeGroups.collectAsList();
            
            for (Row row : typeRows) {
                String riskType = row.getAs("riskType").toString();
                Long count = row.getAs("count");
                typeAnalysis.put(riskType, count);
            }
        } catch (Exception e) {
            logger.warn("Failed to analyze risks by type: {}", e.getMessage());
        }
        
        return typeAnalysis;
    }
    
    private Map<String, Long> analyzeRisksByProbability(Dataset<Row> dataset) {
        Map<String, Long> probabilityAnalysis = new HashMap<>();
        
        try {
            Dataset<Row> probabilityGroups = dataset.groupBy("riskProbability").count();
            List<Row> probabilityRows = probabilityGroups.collectAsList();
            
            for (Row row : probabilityRows) {
                String probability = row.getAs("riskProbability").toString();
                Long count = row.getAs("count");
                probabilityAnalysis.put(probability, count);
            }
        } catch (Exception e) {
            logger.warn("Failed to analyze risks by probability: {}", e.getMessage());
        }
        
        return probabilityAnalysis;
    }
    
    private Map<String, Long> analyzeRisksByStatus(Dataset<Row> dataset) {
        Map<String, Long> statusAnalysis = new HashMap<>();
        
        try {
            Dataset<Row> statusGroups = dataset.groupBy("riskStatus").count();
            List<Row> statusRows = statusGroups.collectAsList();
            
            for (Row row : statusRows) {
                String status = row.getAs("riskStatus").toString();
                Long count = row.getAs("count");
                statusAnalysis.put(status, count);
            }
        } catch (Exception e) {
            logger.warn("Failed to analyze risks by status: {}", e.getMessage());
        }
        
        return statusAnalysis;
    }
    
    private Map<String, Object> analyzeRiskTrends(Dataset<Row> dataset) {
        Map<String, Object> trends = new HashMap<>();
        
        try {
            Dataset<Row> dateGroups = dataset.groupBy("riskDate").count().orderBy("riskDate");
            trends.put("dailyRiskCounts", dateGroups.collectAsList());
            trends.put("totalDays", dateGroups.count());
            
            if (dateGroups.count() > 0) {
                Row avgRow = dataset.agg(org.apache.spark.sql.functions.avg("riskId")).head();
                trends.put("averageRiskId", avgRow.getDouble(0));
            }
        } catch (Exception e) {
            logger.warn("Failed to analyze risk trends: {}", e.getMessage());
        }
        
        return trends;
    }
    
    private Map<String, Object> predictHighRiskEvents(Dataset<Row> dataset) {
        Map<String, Object> prediction = new HashMap<>();
        
        try {
            long highRiskCount = dataset.filter("riskProbability = 'HIGH'").count();
            long totalRisks = dataset.count();
            
            double highRiskRatio = totalRisks > 0 ? (double) highRiskCount / totalRisks : 0.0;
            
            prediction.put("currentHighRiskRatio", highRiskRatio);
            prediction.put("predictedIncrease", highRiskRatio * 1.1);
            prediction.put("recommendation", highRiskRatio > 0.3 ? "HIGH_ALERT" : "NORMAL");
            
        } catch (Exception e) {
            logger.warn("Failed to predict high risk events: {}", e.getMessage());
        }
        
        return prediction;
    }
    
    private Map<String, Object> calculateRiskGrowthRate(Dataset<Row> dataset) {
        Map<String, Object> growthRate = new HashMap<>();
        
        try {
            Dataset<Row> monthlyRisks = dataset
                .withColumn("month", org.apache.spark.sql.functions.month(org.apache.spark.sql.functions.col("riskDate")))
                .groupBy("month")
                .count()
                .orderBy("month");
            
            List<Row> monthlyData = monthlyRisks.collectAsList();
            
            if (monthlyData.size() >= 2) {
                Row lastMonth = monthlyData.get(monthlyData.size() - 1);
                Row previousMonth = monthlyData.get(monthlyData.size() - 2);
                
                long lastCount = lastMonth.getAs("count");
                long previousCount = previousMonth.getAs("count");
                
                double growthRate_value = previousCount > 0 ? 
                    ((double) (lastCount - previousCount) / previousCount) * 100 : 0.0;
                
                growthRate.put("monthlyGrowthRate", growthRate_value);
                growthRate.put("trend", growthRate_value > 0 ? "INCREASING" : "DECREASING");
            } else {
                growthRate.put("message", "Insufficient data for growth rate calculation");
            }
            
        } catch (Exception e) {
            logger.warn("Failed to calculate risk growth rate: {}", e.getMessage());
        }
        
        return growthRate;
    }
    
    private Map<String, Object> identifyCriticalAreas(Dataset<Row> dataset) {
        Map<String, Object> criticalAreas = new HashMap<>();
        
        try {
            Dataset<Row> highRiskTypes = dataset
                .filter("riskProbability = 'HIGH'")
                .groupBy("riskType")
                .count()
                .orderBy(org.apache.spark.sql.functions.desc("count"));
            
            List<Row> criticalTypesList = highRiskTypes.collectAsList();
            criticalAreas.put("criticalRiskTypes", criticalTypesList);
            
            long openHighRiskCount = dataset
                .filter("riskStatus = 'OPEN' AND riskProbability = 'HIGH'")
                .count();
            
            criticalAreas.put("openHighRiskCount", openHighRiskCount);
            
        } catch (Exception e) {
            logger.warn("Failed to identify critical areas: {}", e.getMessage());
        }
        
        return criticalAreas;
    }
}
