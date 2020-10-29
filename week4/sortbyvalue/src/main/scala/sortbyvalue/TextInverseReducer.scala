package sortbyvalue

import java.lang

import org.apache.hadoop.io.{Text}
import org.apache.hadoop.mapreduce.Reducer

/**
 * this flips key and value of input k-v pairs
 */
class TextInverseReducer extends Reducer[Text, Text, Text, Text] {

  override def reduce(key: Text, values: lang.Iterable[Text], context: Reducer[Text, Text, Text, Text]#Context): Unit =
    {
      values.forEach(context.write(_, key))
    }

}
