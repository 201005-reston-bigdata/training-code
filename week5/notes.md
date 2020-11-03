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

Another important component of AWS, used all other the place, is S3: Simple Storage Service.  S3 lets your store objects in buckets on AMazon's servers.  You can retrieve these objects yourself with access keys, or you make them public.  You can tweak many setting relating to access and efficiency.  Ultimately, S3 is kinda like good drive or similar -- we upload files to access them later or share them.  S3 is important because it's common to write to s3 or read from s3 in many different contexts.

We're seeing this term *elastic* in a few places.  AWS's services are elastic meaning they can expand and contract, based on use.  With EMR, we can set our cluster to expand/contract based on the amount of processing we're asking of it.  This behaviour is configurable.  The tagline is "scale out to meet demand, scale in to reduce costs" and is one of the major value propositions of the cloud.

### Tuesday afternoon assignment:

Write a Scala app that runs a Spark job for EMR.  You can write a basic app that reads from "s3://rev-big-data/somelines.txt" and does something.  Feel free to write an app that does something else, but probably avoid I/O tasks.  If you're not reading from somelines.txt, parallelize a Scala collection to produce your first RDD. Make sure the Scala collection is decently sized if you do this.

You don't need to do anything complicated, feel free to just write a wordcount if you like, or do something simple and different.  If your application takes too long we may skip the step.

Send Adam the .jar once you've packaged it.


