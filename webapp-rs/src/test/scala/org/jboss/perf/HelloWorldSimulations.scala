package org.jboss.perf

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.ByteArrayBody
import io.gatling.http.request.builder.Http

/**
  * @author John O'Hara &ltjohara@redhat.com&gt;
  * */
object HelloWorldSimulations {
  class Get extends BaseSimulation with AppHtml {
    def run(http: Http) = {
      http.get("/HelloWorldTest").check(status.is(200), bodyBytes.exists)
    }
  }

}

