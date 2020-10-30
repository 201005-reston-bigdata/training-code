name := "hello-spark"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.1"

// we'd also want some hadoop common dependencies here for a real app so it
// could interact with hdfs + some other data sources.