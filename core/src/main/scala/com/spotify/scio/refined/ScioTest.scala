package com.spotify.scio.refined

import com.spotify.scio.ContextAndArgs
import com.spotify.scio.bigquery.types.BigQueryType
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import org.apache.beam.runners.dataflow.DataflowRunner
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions

object ScioTest {

  type Name = String Refined NonEmpty

  // @BigQueryType.toTable
  case class RefinedRow(name: Name)

  def main(args: Array[String]): Unit = {
    val (sc, _) = ContextAndArgs(args)
    sc.options.setRunner(classOf[DataflowRunner])
    sc.options.setTempLocation("gs://shameera_test_eu/dataflow_temp/")
    sc.optionsAs[DataflowPipelineOptions]
      .setServiceAccount("shameera-sa@scio-playground.iam.gserviceaccount.com")
//
//    sc.parallelize(Seq("A", "B", "C"))
//      .map(s => RefinedRow(Refined.unsafeApply(s)))
//      .saveAsTypedBigQueryTable(Table.Spec("scio-playgound:shameera.refined_type_1"))
//
//    sc.run()
//      .waitUntilDone()

    println("Hey Refinement!")
  }

}
