### Joins

A join combines two different datasets by combining their records.  Whenever you join two datasets together, a common occurence across data processing and storage, you are take the records from one dataset and combine each record with 0 or more records from the other dataset to produce an output dataset containing these larger records.  Joins make your dataset "wider", each output record will contain more information.

Calling join on an RDD will combine the records in two RDDs.  .join expects that each RDD contains k,v pairs in a tuple.  The output will be an RDD containing a k,v pair with the same key and the values of both RDDs combined for the value.

If we join an RDD containing (String, Double) and an RDD containing (String, String) the output will be an RDD
 containing (String, (Double, String))
 
.join on our RDDs will join two records if their keys are equal, joins generally allow us to specify a join condition.

### Joining to create paths

our RDD contains:
(List_of_women_CEOs_of_Fortune_500_companies,(Veritiv,0.00239741816505302))
(Veritiv, (example_page, 0.001))

we want:
(List_of_..., (List(Veritiv, example_page), 0.00000239...)

Let's produce:
(last_page_in_series, (series, fraction))

TODO: remove -, main page?