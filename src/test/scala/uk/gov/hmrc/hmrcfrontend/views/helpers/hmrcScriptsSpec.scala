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

package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.jsoup.Jsoup
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import play.api.test.Helpers.contentAsString
import play.twirl.api.Html
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.{HmrcScripts, HmrcTimeoutDialogHelper}

class hmrcScriptsSpec extends WordSpec with Matchers with GuiceOneAppPerSuite with JsoupHelpers with MessagesSupport {
  implicit val request = FakeRequest("GET", "/foo")

  "hmrcScripts" should {
    "include the hmrc-frontend script tag" in {
      val hmrcScripts = app.injector.instanceOf[HmrcScripts]
      val scripts = hmrcScripts().select("script")

      scripts should have size 1
      scripts.first.attr("src") should fullyMatch regex
        """/assets/hmrc-frontend-\d+.\d+.\d+.min.js""".r
    }

    "include the supplied scriptsBlock after the hmrc-frontend script tag" in {
      val hmrcScripts = app.injector.instanceOf[HmrcScripts]
      val content = hmrcScripts(scriptsBlock = Some(Html("<script id='foo-script-tag' src='/foo'></script>")))

      val scripts = content.select("script")
      scripts should have size 2
      scripts.get(0).attr("src") should include ("hmrc-frontend")
      scripts.get(1).attr("id") should be ("foo-script-tag")
    }

    "render the correct url even if AssetsConfig has already been instantiated" in {
      val hmrcScripts = app.injector.instanceOf[HmrcScripts]
      hmrcfrontend.RoutesPrefix.setPrefix("/foo-service/hmrc-frontend")

      val scripts = hmrcScripts().select("script")

      scripts should have size 1
      scripts.first.attr("src") should fullyMatch regex
        """/foo-service/hmrc-frontend/assets/hmrc-frontend-\d+.\d+.\d+.min.js""".r
    }
  }
}
