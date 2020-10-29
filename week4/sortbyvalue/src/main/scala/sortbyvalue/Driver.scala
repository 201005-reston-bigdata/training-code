package sortbyvalue

import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text, WritableComparable, WritableComparator}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, KeyValueTextInputFormat}
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.mapreduce.lib.map.InverseMapper

object Driver extends App {

  if(args.length != 2) {
    println("Usage: sort by value takes input and output dirs")
    System.exit(-1)
  }

  val job = Job.getInstance()

  job.setJarByClass(Driver.getClass)
  job.setJobName("Sort by value")

  job.setInputFormatClass(classOf[KeyValueTextInputFormat])
  FileInputFormat.setInputPaths(job, new Path(args(0)))
  FileOutputFormat.setOutputPath(job, new Path(args(1)))

  //using the appropriate built in InverseMapper
  job.setMapperClass(classOf[InverseMapper[Text, Text]])
  //use our InverseReducer
  job.setReducerClass(classOf[TextInverseReducer])

  // We define this class to specify how to sort keys in shuffle + sort
  // a Comparator just compares two other objects, in this case Text keys
  class TextToLongComparator extends WritableComparator(classOf[Text], true) {
    override def compare(a: WritableComparable[_], b: WritableComparable[_]): Int = {
      a.toString.toLong.compareTo(b.toString.toLong)
    }
  }
  // and we set it here
  job.setSortComparatorClass(classOf[TextToLongComparator])

  job.setOutputKeyClass(classOf[Text])
  job.setOutputValueClass(classOf[Text])

  val success = job.waitForCompletion(true)
  System.exit(if (success) 0 else 1)

}
