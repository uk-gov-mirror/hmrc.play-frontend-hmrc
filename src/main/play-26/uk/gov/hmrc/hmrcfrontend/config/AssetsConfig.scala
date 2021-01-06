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

package uk.gov.hmrc.hmrcfrontend.config

import javax.inject.Inject
import play.api.Configuration
import uk.gov.hmrc.hmrcfrontend.controllers.routes.Assets
import uk.gov.hmrc.hmrcfrontend.views.HmrcFrontendDependency

class AssetsConfig @Inject()(configuration: Configuration) {
  val isOnPlatform: Boolean =
    configuration.has("platform.frontend.host")

  private val govukFrontendPath     = configuration.get[String]("assets.govuk-frontend-path")
  private val govukFrontendVersion = HmrcFrontendDependency.govukFrontendVersion
  private val hmrcFrontendPath     = configuration.get[String]("assets.hmrc-frontend-path")
  private val hmrcFrontendVersion = HmrcFrontendDependency.hmrcFrontendVersion

  val govukFrontendAssetsPrefix: String  = s"$govukFrontendPath/$govukFrontendVersion"
  val hmrcFrontendAssetsPrefix: String  = s"$hmrcFrontendPath/$hmrcFrontendVersion"

  val html5ShivJsFilename = "html5shiv.min.js"

  val govukFrontendIe8CssFilename: String = s"govuk-frontend-ie8-$govukFrontendVersion.min.css"
  val govukFrontendCssFilename: String = s"govuk-frontend-$govukFrontendVersion.min.css"
  val govukFrontendJsFilename: String = s"govuk-frontend-$govukFrontendVersion.min.js"

  val hmrcFrontendIe8CssFilename: String = s"hmrc-frontend-ie8-$hmrcFrontendVersion.min.css"
  val hmrcFrontendCssFilename: String = s"hmrc-frontend-$hmrcFrontendVersion.min.css"
  val hmrcFrontendJsFilename: String = s"hmrc-frontend-$hmrcFrontendVersion.min.js"

  val html5ShivJsUrl: String = Assets.at(html5ShivJsFilename).url

  val govukFrontendIe8CssUrl: String = govukFrontendAssetUrl(govukFrontendIe8CssFilename)
  val govukFrontendCssUrl: String = govukFrontendAssetUrl(govukFrontendCssFilename)
  val govukFrontendJsUrl: String = govukFrontendAssetUrl(govukFrontendJsFilename)

  val hmrcFrontendIe8CssUrl: String = hmrcFrontendAssetUrl(hmrcFrontendIe8CssFilename)
  val hmrcFrontendCssUrl: String = hmrcFrontendAssetUrl(hmrcFrontendCssFilename)
  val hmrcFrontendJsUrl: String = hmrcFrontendAssetUrl(hmrcFrontendJsFilename)

  val initAllJsUrl: String = Assets.at("init-all.js").url

  private def assetUrl(prefix: String, filename: String) = {
    if (isOnPlatform) s"$prefix/$filename" else Assets.at(filename).url
  }

  private def govukFrontendAssetUrl(filename: String) = assetUrl(govukFrontendAssetsPrefix, filename)
  private def hmrcFrontendAssetUrl(filename: String) = assetUrl(hmrcFrontendAssetsPrefix, filename)
}
