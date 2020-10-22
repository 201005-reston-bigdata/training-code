package wordcount

import java.lang

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

/**
 * Defines a reduce task for a basic wordcount mapreduce, aggregates counts for words
 *
 * Our reducer is similar to our mapper, we need 4 generics and to write a reduce method
 */
class WordReducer extends Reducer[Text, IntWritable, Text, IntWritable] {

  /**
   * reduce runs once for each key received from the shuffle
   * and sort phase of MapReduce.
   *
   * @param key     word to aggregate counts for
   * @param values  an iterable over values associates with the word from the mapper -- these
   *                will all be 1 (unless we run a combiner)
   * @param context same as mapper context, the way we write output from reduce
   */
  override def reduce(key: Text, values: lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
    var count = 0

    // for each value, add it to count
    values.forEach(count += _.get())

    context.write(key, new IntWritable(count))
  }

}
