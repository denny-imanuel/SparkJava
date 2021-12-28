package com.sparkjava;

import org.apache.spark.sql.*;
import org.apache.spark.sql.Dataset;

public class Main {

    public static void main(String[] args) {

        // create spark session
        SparkSession spark = new SparkSession
                .Builder()
                .appName("SparkJava")
                .getOrCreate();

        // create initial dataframe
        Dataset<Row> dataset = spark.read().text("input.txt");

        // count words
        Dataset<Row> words = dataset
                .select(functions.split(functions.col("value"), " ").alias("words"))
                .select(functions.explode(functions.col("words")).alias("word"))
                .groupBy("word")
                .count()
                .orderBy(functions.col("count").desc());
        words.show();
        spark.stop();
    }

}
