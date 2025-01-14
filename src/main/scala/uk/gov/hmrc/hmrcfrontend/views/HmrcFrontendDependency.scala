/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.hmrcfrontend.views

import scala.util.matching.Regex
import hmrcfrontendbuildinfo.BuildInfo

object HmrcFrontendDependency {
  val hmrcFrontendVersionRegex: Regex = """org\.webjars\.npm:hmrc-frontend:(\d+\.\d+\.\d+)""".r

  val hmrcFrontendVersion: String =
    findFirstMatch(hmrcFrontendVersionRegex, BuildInfo.libraryDependencies)
      .getOrElse(throw new RuntimeException("Unable to find the hmrc-frontend dependency"))
      .group(1)

  def findFirstMatch(regex: Regex, xs: Seq[String]): Option[Regex.Match] =
    for {
      x          <- xs.find {
                      case regex(_*) => true
                      case _         => false
                    }
      firstMatch <- regex.findFirstMatchIn(x)
    } yield firstMatch
}
