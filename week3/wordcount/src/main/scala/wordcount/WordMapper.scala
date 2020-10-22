package wordcount

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

/** WordMapper defines the map task for a basic wordcount mapreduce
 *
 * To define a map function, we need to specify the input types for key value
 * pairs and output types for key value pairs.  This is accomplished with generics
 * We'll use hadoop io types: Text instead of String, IntWritable instead of Int...
 * */
class WordMapper extends Mapper[LongWritable, Text, Text, IntWritable] {

  /**
   * map takes a line number, text content key value pair and a context,
   * and writes output key value pairs consisting of word, 1 to the context
   *
   * @param key     line number
   * @param value   text content of line
   * @param context context that output is written to
   */

  override def map(key: LongWritable, value: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {

    val line = value.toString

    // split line on all non-word characters to get array of words
    // filter out 0 length words
    // convert them all to uppercase
    // write each in a key value pair to the context
    line.split("\\W+").filter(_.length > 0).map(_.toUpperCase).foreach(
      (word:String) => { context.write(new Text(word), new IntWritable(1))}
    )

  }

}
