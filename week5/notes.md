### Spark Linages and Physical Execution plan

The lineage of an RDD that we see by using .toDebugString consists of the logical plan for the execution of that RDD.
Spark takes that logical plan and turns it into a physical plan that can actually run on the cluster.
This physical plan is divided into stages and stages are divided into tasks.  Each transformation that requires
a shuffle starts a new stage (and are tasks in that stage).  Transformations that don't require a shuffle are tasks within a stage.

Each partition of the input data corresponds to one task.  If our someline.txt is split into 4 partitions to begin with, it will spawn 4 tasks.  Each task will operate on its own partition, and pass its output to the next task in the stage.
When we shuffle, we change the partitions across the cluster, almost always redistributing the values inside them and changing the number of partitions.  Each partition output from the shuffle corresponds to one task, the first task in the new stage.

### Running Spark on YARN

When we run Spark on YARN, we have 2 options: cluster mode and client mode
Cluster mode: The driver program in Spark runs inside an ApplicationMaster in Yarn.  Output from our driver program will appear in the container spun up for the ApplicationMaster by the ApplicationsManager.
Client mode: The driver program runs outside YARN (often on YARN's master node) and communicates with an ApplicationMaster.  Output from our driver program will appear on the YARN master node, or wherever the driver program is running.

The machines used to execute Spark jobs are called *executors*, in YARN these run as containers.

### AWS

Amazon Web Services provides virtual machines and services built on top of them as a service, meaning you pay monthly/daily/hourly for the use of their computing machinery.  Almost everything on AWS is built on top of EC2s, which stands for Elastic Compute Cloud and is like a virtual server.  If you want a relational databse on AWS you can use RDS (Relational Database Service), which will get you your MySQL or similar database, managed by Amazon, running on an EC2 under the hood.  If you want a Hadoop cluster on AWS you can use EMR (Elastic MapReduce), which will you a preconfigured and managed Hadoop cluster, running on EC2s under the hood.

Another important component of AWS, used all other the place, is S3: Simple Storage Service.  S3 lets your store objects in buckets on Amazon's servers.  You can retrieve these objects yourself with access keys, or you make them public.  You can tweak many setting relating to access and efficiency.  Ultimately, S3 is kinda like google drive or similar -- we upload files to access them later or share them.  S3 is important because it's common to write to s3 or read from s3 in many different contexts.

We're seeing this term *elastic* in a few places.  AWS's services are elastic meaning they can expand and contract, based on use.  With EMR, we can set our cluster to expand/contract based on the amount of processing we're asking of it.  This behaviour is configurable.  The tagline is "scale out to meet demand, scale in to reduce costs" and is one of the major value propositions of the cloud.

### Tuesday afternoon assignment:

Write a Scala app that runs a Spark job for EMR.  You can write a basic app that reads from "s3://rev-big-data/somelines.txt" and does something.  Feel free to write an app that does something else, but probably avoid I/O tasks.  If you're not reading from somelines.txt, parallelize a Scala collection to produce your first RDD. Make sure the Scala collection is decently sized if you do this.

You don't need to do anything complicated, feel free to just write a wordcount if you like, or do something simple and different.  If your application takes too long we may skip the step.

Send Adam the .jar once you've packaged it.

Find the template for the job in week5 folder in the git repo.

### Alphabetize debugging

interesting facts:
- Spark doesn't have any tasks marked as complete in Stage 2
- EMR's YARN application history has 3 of the stage 4 tasks marked as active and 1 as completed
- The stderr for executor 1 outputs messages that say tasks with TID 9 and 11 are complete
- The stderr for executor 2 outputs messages that say tasks with TID 8 and 10 are complete.  It then
  logs an Exception due to the driver program resetting the connection.  We get an exception for the "heartbeater"
- Both executors end with executor self-exiting due to driver IP disassociated
- The stdout from the driver application gives us a java.lang.OutOfMemoryError

Adam's current pitch:
everything went well, including tasks on executors, until the collect occurred.  While sending the output of tasks in the collect stage to the driver, the driver ran out of memory and crashed.  The executors then failed due to lack of communication from the driver.

Note on the above: The executors had GC allocation failures, which mean they didn't have enough memory, which triggered Garbage Collection to free some up.  The driver had an OutOfMemoryError, which was fatal.
On that same, note in memory processing on the executors can "spill" to disk if necessary.  While our executors are processing tasks, if they come close to running out of memory they can write some of their memory to disk, and process smaller amounts at any one time.  

Potential problems with that pitch? Not sure.

Leftover questions:
- If driver has GB of mem by default how did driver have OOM with much smaller data? possibly just Java objects being larger, but not sure
- Can we get an EMR to use like swapfile to alleviate OOM? Good question, I believe yes, but Spark tools will most likely be better since they have more information about the data being temporarily stored on disk.
- Can we break the processing up into chunks?  for collect, no.  We could instead take() to just retrieve a manageable amount of the RDD.  On the cluster, yes we break processing up into chunks by partitioning it.
- Why are the Spark history server and the YARN application history in EMR inconsistent?  Why exactly are both listed as still running?  Possibly cluster vs client would affect consistency.

Next steps:
- Try running in cluster mode instead of client mode, explore results.
- Try to rewrite the application to not use collect on a large RDD like this, probably writing to file.
- spark.driver.memory, which defaults to 1g, can be changed.  Since we're running in client mode we'll want to do this with an argument to
  our spark submit. (there's a UI in EMR for this)  This can provide more memory to the driver application, hopefully fixing the problem.
- spark.driver.maxResultSize can be changed.  This will abort jobs which which provide serialized results to the driver over a certain size.  
  The default cutoff is 1g.  We could lower this value to make the job be aborted rather than crash due to OOM Error.  In general, aborting the 
  job is preferable to having the job crash.

### Tuning spark job notes:

- To begin with, each action is going to spawn a Job.  RDDs aren't shared between jobs, so it can be fruitful to attempt to put job together.  This is common refrain when optimizing spark jobs, we want to minimize the number of times we go over the data.
  - A caveat here is that the files written to local disk as part of a shuffle *can* be used in future jobs, Spark will skip stages if the output of those stages already exists on local disk.
- The number of partitions in a job is important.  If we have too few partitions, we need to worry about not effective using all the resources on our cluster.  If you have 4 partitions and 20 machines, only 4 of those machines can possibly run at one time.  We can specify a minimum number of partitions when we're reading out sc.textFile(filename, minPartitions)
- The number of partitions remains important after shuffles, if the data is still large.  It's possible to start out with a reasonable number of partitions and reduceByKey into a bad number of partitions (most often too low).
- Guidance for partitions is to have at least twice as many as cores on your cluster as a lower bound, and to have few enough that tasks take at least 100ms to complete as an upper bound.
- Similar to spark.driver.memory above, we can tweak spark.executor.memory to provide more/less memory to each executor.  This can be useful to prevent "spills", which incur an additional cost of Disk I/O.








