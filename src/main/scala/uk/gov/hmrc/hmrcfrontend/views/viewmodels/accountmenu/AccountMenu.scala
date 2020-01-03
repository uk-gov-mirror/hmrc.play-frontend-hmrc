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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.accountmenu

import play.api.libs.json._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.JsonDefaultValueFormatter
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Language.En
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Language

case class AccountMenu(
                        accountHome: AccountHome = AccountHome(),
                        messages: Messages = Messages(),
                        checkProgress: CheckProgress = CheckProgress(),
                        paperlessSettings: PaperlessSettings = PaperlessSettings(),
                        personalDetails: PersonalDetails = PersonalDetails(),
                        signOut: SignOut = SignOut(),
                        language: Language = En
                      )

object AccountMenu extends JsonDefaultValueFormatter[AccountMenu] {

  override def defaultObject: AccountMenu = AccountMenu()

  override def defaultReads: Reads[AccountMenu] = Json.reads[AccountMenu]

  override implicit def jsonWrites: OWrites[AccountMenu] = Json.writes[AccountMenu]
}