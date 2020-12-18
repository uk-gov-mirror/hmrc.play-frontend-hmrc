/*
 * Copyright 2020 HM Revenue & Customs
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
import org.scalatest.{Matchers, WordSpecLike}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.Lang
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.helpers._
import scala.collection.immutable.List

class hmrcStandardHeaderSpec extends WordSpecLike with Matchers with MessagesSupport with GuiceOneAppPerSuite {
  implicit val fakeRequest = FakeRequest("GET", "/foo")

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder().configure(Map("play.allowGlobalApplication" -> "true")).build()

  def buildApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(Map("play.i18n.langs" -> List("en", "cy")) ++ properties)
      .build()

  "HmrcStandardHeader" should {
    "generate the header" in {
      implicit val app = buildApp()

      val content  = contentAsString(HmrcStandardHeader()(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val logoTexts    = document.select(".govuk-header__logotype-text")

      logoTexts.first.text should be("GOV.UK")
    }

    "show the service name if defined in messages" in {
      implicit val app = buildApp()

      val messages = stubMessagesApi(
        Map(
          "en" -> Map("service.name" -> "foo")
        )
      ).preferred(Seq(Lang("en")))

      val content  = contentAsString(HmrcStandardHeader()(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val serviceNames    = document.select(".govuk-header__link--service-name")

      serviceNames should have size 1
      serviceNames.first.text should be("foo")
    }

    "show the service url if defined in messages" in {
      implicit val app = buildApp()

      val messages = stubMessagesApi(
        Map(
          "en" -> Map("service.name" -> "foo")
        )
      ).preferred(Seq(Lang("en")))

      val content  = contentAsString(HmrcStandardHeader(serviceUrl = Some("/tax"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val serviceNames    = document.select(".govuk-header__link--service-name")

      serviceNames should have size 1
      serviceNames.first.attr("href") should be("/tax")
    }

    "not show the service name if not defined in messages" in {
      implicit val app = buildApp()

      val messages = stubMessagesApi().preferred(Seq(Lang("en")))

      val content  = contentAsString(HmrcStandardHeader()(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val serviceNames    = document.select(".govuk-header__link--service-name")

      serviceNames should have size 0
    }

    "render the sign out link in Welsh" in {
      implicit val app  = buildApp()
      val welshMessages = messagesApi.preferred(Seq(Lang("cy")))

      val content  = contentAsString(HmrcStandardHeader(signOutUrl = Some("/signout"))(welshMessages, fakeRequest))
      val document = Jsoup.parse(content)
      val logoTexts    = document.select(".hmrc-sign-out-nav__link")

      logoTexts.first.text should be(
        "Allgofnodi")
    }
  }
}
