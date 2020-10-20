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