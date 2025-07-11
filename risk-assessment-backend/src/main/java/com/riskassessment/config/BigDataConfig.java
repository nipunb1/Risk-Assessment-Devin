package com.riskassessment.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@org.springframework.context.annotation.Configuration
public class BigDataConfig {

    @Value("${spark.app.name:RiskAssessmentAnalytics}")
    private String sparkAppName;

    @Value("${spark.master:local[*]}")
    private String sparkMaster;

    @Value("${hadoop.namenode.url:hdfs://localhost:9000}")
    private String hadoopNameNodeUrl;

    @Bean
    @Primary
    public SparkSession sparkSession() {
        SparkConf conf = new SparkConf()
                .setAppName(sparkAppName)
                .setMaster(sparkMaster)
                .set("spark.sql.adaptive.enabled", "true")
                .set("spark.sql.adaptive.coalescePartitions.enabled", "true");

        return SparkSession.builder()
                .config(conf)
                .getOrCreate();
    }

    @Bean
    public JavaSparkContext javaSparkContext(SparkSession sparkSession) {
        return new JavaSparkContext(sparkSession.sparkContext());
    }

    @Bean
    public Configuration hadoopConfiguration() {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hadoopNameNodeUrl);
        conf.set("dfs.client.use.datanode.hostname", "true");
        return conf;
    }

    @Bean
    public FileSystem hadoopFileSystem(Configuration hadoopConfiguration) {
        try {
            return FileSystem.get(hadoopConfiguration);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Hadoop FileSystem", e);
        }
    }
}
