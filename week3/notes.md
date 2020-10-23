### Week 3

Todos for this week / future:
- Hadoop and HDFS (and YARN, a bit), just basic MapReduce, no bells + whistles (Cloudera/HortonWorks)
    - After Hadoop became popular, we had the "Hadoop Explosion" where many many specific tools were created to use MapReduce in different contexts.
    - Cloudera Distribution Hadoop (CDH) provided a product that bundled these tools together alongside cluster management tools.
    - We aren't going to make use of most of these tools.
- Basic Unix-like OSs and commands on those
- Hive, part of the Hadoop explosion, SQL queries on Hadoop, still used all over the place.
- Spark + ...

Options for this week/future
- CDH quickstart VM, everything already set up, we don't need most of it, learn some cloudera manager
- Ubuntu (or similar) VM, we'd install Hadoop + more in Unix-like environment on our machine
- WSL2 another option
- Unix-like OS on AWS, install big data tools on Windows.

## Computers
So our computers have some computing resources: processors (CPU, cores + threads), memory (RAM + caches), long term storage (hard disk, SSD, HDD).  Procesors actually do operations, every single thing our computer does goes through the processor.  Kind of like the brain of the machine.  Memory (RAM) is short term memory, used to hold the values that go through the processor.  This includes holding our programs in memory, so the operations that go though the processor are stored here as well.  Storage is long term storage, it's the only part of the machine that remembers anything once the machine is turned off.  Files and folders go here, OS goes here.

## Virtual Machines (w/ VirtualBox)
A virtual machine is like your physical computer, but virtual.  It doesn't physically exist, it only virtually exists.  A physical computer somewhere needs to provide virtual resources to a virtual machine, which it can then use to run.  We call these two machines the *Host* and *Guest*.  There is some software called a *Hypervisor* that is responsible for creating and managing VMs.  VMs use their virtual resources the same way real machines use physical resources, for the most part VMs don't need to know that they're running on virtual resources.  We start up VMs from an *image* that contains the OS they are going to use and any files they should begin with.  We can create lightweight images that are mostly just an OS, or we can create heavy images that start the VM with many files/applications/etc on top of an OS.  

## AWS (Amazon Web Services) very basics
AWS provides computing resources to us in the cloud.  If we used AWS to explore Unix-like OSs, we'd have AWS spin up a server for us (called an EC2) and we'd connect to that server using ssh (Secure SHell).  We would then be able to interface with the Unix-like OS running on that server via the command line, ssh would pipe our commands from our local machine to Amazon's machine in a data center somewhere.  The potential benefit to us from this one is we're going to want to learn about Amazon EMR (Elastic MapReduce) in the future.

## Unix and Unix-like OSs
Unix is an OS originally developed at Bell Labs in the 70s.  Unix became popular among hobbyists and academics.  Originally, Bell sold Unix in such a way that the peoiple who purchased Unix could modify its source code, expand its functionality, and share those changes with others.  Users in communities creates different versions of *distributions* of Unix, the one most notable to us today is BSD: Berkeley Software Distribution.  BSD is still around, still popular, and there's some BSD code in MacOS.

In the 80s, Bell attempts to sell Unix without granting these rights to modify, improve, and share its code.  This kills the market for Unix, with hobbyists and academics who use Unix keeping the older versions and working on those rather than getting new, closed Unix versions.  Another response to this move by Bell was the creation of the GNU project.  GNU is a cute acronym for GNU's Not Unix, the GNU project worked to recreate a Unix-like OS without using any actual Unix source code.

Development on GNU continues through the 80s, but GNU kernel development is not complete, Linus TOrvalds creates the Linux kernel which finishes out a complete OS GNU/Linux.  This is an OS with GNU project tools/files/programs and a Linux kernel.  From here, work is still happening on the Linux kernel, many Linux distributions are built, supported, forked, rebuilt, etc...

All of these OSs: Unix, BSD, and GNU/Linux are Unix-like, though only some of them contain actual Unix code.

## Open and closed source
Open source software is free (as in freedom) to be used for any purpose, users of the software can modify, redistribute, sell, etc.  Open Source software.  Notably, software that is Open source we sometimes have to pay for.  Free (libre, as in freedom) doesn't imply free (gratis, as in free beer), or vice versa.

There is some grey area, really what you can software for depends on its license.  Much closed source software out there offers the end user no freedom, just functionality.  A few licenses : 
- MIT license, classic open source, use this software for whatever you like.
- GPL (GNU's General Public License), this is a "copyleft" license that permits you to do anything with the software other than use it in closed source projects or sell it.

## Unix-like OS shell utilities

- ls : list files in the current directory
- cd .. : change directory to the parent directory
- cd dirname : change directory into a dirname
- two directories on your system are represented with symbols: ~ for your user's home directory and / for the root of the filesystem (the very top)
- pwd : print working directory, prints your current directory
- In Unix-like systems, each user's home directory is found in /home/username
- mkdir : makes a directory
- touch : makes a file (one of many ways)
- nano : command line text editor, easy and simple
- man <command> : shows you the manual for a given command
- command --help similar to man, print help output
- less filename : reads content of file, works well with large files
- cat filename : prints content of file to screen
- mv : moves a file from one location to another
  - refer to current directory with a .
  - also use mv to rename files
- cp : copy a file from one location to another
- rm : remove files.  Use the -r flag to remove directories recursively
- history : show history of commands
- clear : clear the screen
- ls -l : display contents of directory in long form, with more detail
  - In Unix-like operating systems, we can have *symbolic links* where one location in the filesystem links to another location. These are shown when we use ls -l.

All of the above commands, any any commands we run, are resolved by the shell the same way our 'java' command is resolved on Windows: the shell checks the PATH environment variables, searches directories on the PATH for the appropriate program, and runs that program.  Most/all of the command we listed above are programs found in /usr/bin (usr is Unix System Resources, bin is binaries)

Any time we're referring to a file or directory we can use its *absolute path* or its *relative path*.  An absolute path starts with / or ~ and specifies the exact location, regardless of where you are.  A relative path doesn't start with one of those, and it specifies where the file/dir is from your current location.

## Package managers

Every Linux distribution comes with a package manager that makes it easy to install, maintain, and keep applications up to date.  The one we have on Ubuntu is apt : Advanced Packaging Tool.  To make sure our installed applications are up to date, we can run:
- sudo apt-get update
- sudo apt-get upgrade
The sudo in each of those commands runs the command with elevated permissions.  Our default user has sudo permissions, which means they're allowed to use sudo (with a password) to run commands as the "super user" or "root", we use sudo when we're installing or removing applications and when we're modifying system files.

## Users, Groups, and permissions

In Unix-like OSs, we manage access that our users and applications have to files by specifying permissions based on users and groups and by placing users into the appropriate group(s).  We started off on WSL2 with a default user set up.  This user has to follow some permission rules when interacting with files/folders.  Each user is part of one or more group(s), and users in a group have additional permissions based on their membership.  For instance, my user "adam" is in the sudo group, which means I'm allowed to use sudo*.  Each user is at least in the group with their same name, i.e. adam is in group adam.

*sudoer permission is more granularly managed in a sudoers file (use visudo to access) that we probably won't need to touch.

Information about users and groups is found two files: /etc/passwd and /etc/group.  passwd contains user information, group contains group information.  We can also user commands like useradd and usermod to manipulate users and groups, instead of editing the files directly.

Each file has an associated user (owner) and an associated group.  Any files that the user "adam" creates will be default be associated with the user "adam" and the group "adam".  We are able to specify permissions on a file to be different based on owner/group/public.  For each of those categories, we specify whether read access, write access, and eXecute access are allowed.

We can modify the permissions on a file using the chmod command.  chmod takes 3 numbers to specify permissions for owner, group, public, along with the anme of the file/dir to modify.  Each number ranges from 0 to 7.  start at 0 for no permissions, add 4 if you want read permissions, add 2 if you want write permissions and add 1 if you want execute permissions.

Example: 777 is read+write+execute for all owner, group, and public.
Example: 755 is read+write+execute for owner, read+execute for group and public.

You can also see this as writing the number in binary, with each 1 providing a permission out of rwx and each 0 denying that permission.

Example: 777 is 111 111 111 which means full rwx rwx rwx for owner, group, public
Example: 755 is 111 101 101 which means rwx r-x r-x for owner, group, public
Example: 644 is 110 100 100 which means rw- r-- r--

## SSH : Secure SHell

SSH lets us make secure connections between machines over an insecure network.  To achieve this, we use keypairs with SSH.  We're going to need to run some related commands when setting up Hadoop, I just don't want us to be totally in the dark.  For now, a keypair lets two computers send traffic to each other that only they can read.
This works because each machine has both a public key that they share with the world, and a private key that they keep hidden.  Anyone can encrypt messages using a public key, only the holder of the private key will be able to decrypt them.

## installation commands for WSL2 Hadoop:

https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SingleCluster.html#Standalone_Operation

sudo apt-get update
sudo apt-get upgrade
sudo apt-get install openjdk-8-jdk
sudo apt-get install pdsh

wget "https://mirror.olnevhost.net/pub/apache/hadoop/common/hadoop-3.2.1/hadoop-3.2.1.tar.gz"
tar -xvf hadoop-3.2.1.tar.gz

Then follow directions from apache.hadoop.org

## Big Data : 3 Vs

"Big data is the problem, Hadoop is the solution" -- though at this point there are quite a few solutions, like Apache Spark as well.
As data grows, the tools we use to store and process it become inadequate.  Solutions that work for small amounts of data (<1TB) on one machine
fail to work for large amounts of data, and we need both a cluster to store the data and a cluster to process it.  As we start storing large 
amounts of data + using lots of hard disks, rare hardware failures become routine.  This means our cluster storage needs to account for 
potentially failing hardware and must store our data redundantly.  As we start processing large amounts of data single or multithreaded 
operations on one or a few machines becomes untenable -- it just isn't fast enough.  When we scale out and process data across the cluster, we 
run into similar problems, where rare runtime failures become commonplace and our data processing must be fault tolerant.

3 Vs of big data :
- Volume : data in large amounts, at least >1TB
- Velocity : data being generated rapidly, often online content or application logs.
- Variety : data being in different formats with differing amounts of structure.

Variety of data :
- Unstructured : raw text, image, pdf, ...
- semi-structured : XML, CSV, email, ...
- structured : relational DB tables (we have explicit size, typed content, indexed) (probably Mongo goes here)

## A bit of Hadoop Evolution

Hadoop is Apache Hadoop, an OS project.  Built based on Google's MapReduce, the paper we read earlier this week.  Hadoop v1 was just MapReduce, running on HDFS (Hadoop Distributed FileSystem).  Hadoop v2 introduced YARN (Yet Another Resource Negotiator) which runs MapReduce jobs but can also run other types of jobs on a cluster, more flexible.  Still built on HDFS.  Notably in the "other types of jobs" category, we can run Spark jobs on a YARN cluster.

YARN and HDFS both run on a cluster, and in a typical deployment they're running on the same cluster, using exactly the same machines.  HDFS is used for distributed data storage on the cluster, where YARN is used for distributed data processing on the cluster.  Each machine in the cluster will both store HDFS data and run YARN tasks.  One major benefit is *data locality*, which is very important for efficient processing.  "Move the computation to the data"

When we talk about YARN and HDFS running on a cluster, what we're really talking are various YARN and HDFS *daemons* running on the machines in that cluster.  A *daemon* is just a long running process.  The daemons included in YARN and HDFS talk with each other over the network and enable cluster processing and storage.

## HDFS Daemons

Two main HDFS daemons:
- NameNode : This is the master, there is one (or very few) per cluster.  The NN keeps the image of the distributed filesystem.  It knows the names and directories, and it knows where to find their contents on the cluster, but it doesn't store any actual data.
- DataNode : This is the worker, there is one per machine in the cluster in a typical deployment.  The DN keeps actual data, and communicates its status to the NN.  Doesn't know anything about the actual files its storing on the filesystem.

Data nodes run on "commodity hardware", just meaning regular servers, not specialized hardware. 
All the files stored in HDFS are stored in 1 or more blocks.  The default blocksize (in Hadoop 2+) is 128MB.  A 1MB file will be stored in 1 block, a 127MB file will be stored in 1 block, a 255MB file will be stored in 2 blocks and a 129MB file will be stored in 2 blocks.
Each file/block in HDFS is replicated across the cluster.  The default number of replications is 3, though this can be changed.  Replication information and other metadata about the files/blocks is going to be stored on the NameNode.
Each datanode periodically sends a heartbeat to the NN, including a blockreport so the NN knows whether problems have occurred.  The NN makes all decisions about replication, so if some block has failed on a datanode, the NN will decide where to put the new replication required.
The NN never actually sends out traffic without prompting.  Its the datanodes responsibility to report to the NN, rather than the NN checking up on all datanodes.

NameNode details:
- stores all information about the filesystem in FSImage, a file stored on the local machine, the machine that's running the NameNode daemon.
- records edits to the filesystem in a log called EditLog, also stored in the local filesystem.

## HDFS Daemon Fault Tolerance
- For Datanodes, their fault tolerance is handled by the Namenode.  If a Data Node goes down, the Name Node stops receiving heartbeats and will use replications of all the lost data to make copies across the remaining datanodes and achieve the target replication factor.
- For Namenode, in Hadoop v1 the NameNode was a Single Point of Failure, so if the namenode went down the filesystem went down at least temporarily.  We have a few options for fault tolerance in Hadoop v2+
  - The NameNode writes its state and transactions to file (FSImage and EditLog), so the application going down + coming back up is just a very temporary interruption.  It writes to these periodically, making *checkpoints* based on elapsed time or elapsed transactions.
  - We can have a *Secondary NameNode* that periodically (every hour) keeps backups of namenode metadata.  The secondary namenode is not capable of stepping in as a new NameNode, it just makes backup copies of metadata files to avoid catastrophic data loss.
  - We can instead have a *Standby NameNode*, this works like a secondary in a replicaset in mongo.  The standby will step if in the NameNode fails, and while the NameNode is running the Standby replicates all its actions so they have the same state.

## Rack Awareness

The NameNode makes decisions about replication.  The default strategy is "rack aware", which means it makes those decisions knowing how your datacenter is set up, in order to achieve a balance of reliability and network traffic.  If all our replications are on one server rack, the failure of that rack will mean we lose access to our data, which is bad.  If all our replications are on different racks, then we're producing a lot of inter-rack network traffic.  The default setup is 2 replications on one rack, and 1 replication on another.  1 copy across racks for reliability, 1 copy within a rack to limit traffic.

"Rack" is just server rack, meaning around ~10 computers networked together (check google images).

## Scaling HDFS

HDFS works well on up to 1000s of nodes.  If we need to go further than this, we used HDFS Federations to create multiple linked HDFS clusters, each with its own NameNode.

## YARN Daemons

Two main YARN Daemons:
- Resource Manager : one per cluster, responsible for providing resources for jobs.  Resources here is RAM, CPU, disk, network, .. the computing resources required to do tasks like MapReduce.
- Node Manager : one per node/machine, Node Managers manage bundles of computing resources called *containers* and report to ResourceManager on progress/health.

We discuss computations the cluster is doing using the terms *job* and *task*.  A *job* is large, submitted to the cluster, and is something like an entire MapReduce.  Your *task* is something that Jobs are made of, is run on an individual machine (an individual Container), and is something like one Map task.

Our RM consists of two pieces:
- Scheduler : scheduler is reponsible for actually allocating resource (containers) based on requests made to it.  It doesn't know much about the computations done on those or the larger jobs.
- ApplicationsManager : Accepts job submissions, and creates the ApplicationMaster for each submitted job.  Also responsible for fault tolerance of ApplicationMasters.

Each job submitted to the cluster gets an ApplicationMaster.  The ApplicationMaster is what requests resources for each task in the job from the Scheduler.

Example : we have a MapReduce job that has 300 Map tasks and 4 Reduce tasks.  The Resource Manager ensures we get our 304 containers across the cluster to make this job happen.  Each machine has a NodeManager that reports on its containers to the ResourceManager

sudo ssh-keygen -A
sudo service ssh start
ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
chmod 0600 ~/.ssh/authorized_keys

ssh localhost should work, it will log you in to your own user on Ubuntu, via ssh.

keypair: private and public key
I keep my private key very secret, share public key with people I want to communicate with.
Anyone who has my public key can send me encrypted messages that I decode with my private key,
I can encrypt messages with my private key that can be decoded using my public, this verifies my identity.
The way this works is I send out "This is a neat sentence" and I also send out "This is a neat sentence" encrypted using my private key.  Anyone with my public key can decrypt + verify that the sentences match.

fix rcmd socket permission denied by running:
export PDSH_RCMD_TYPE=ssh