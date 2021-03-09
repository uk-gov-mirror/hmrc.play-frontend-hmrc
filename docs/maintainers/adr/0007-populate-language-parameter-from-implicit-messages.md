# Populate language parameter from implicit messages

* Status: proposed
* Date: 2020-03-09

Technical Story: PLATUI-531

## Context and Problem Statement

In [ADR-0001](0001-play-frontend-hmrc-mirrors-hmrc-frontend.md) we established 
the principle that play-frontend-hmrc should mirror hmrc-frontend
in providing a pure Twirl port of the underlying hmrc-frontend Nunjucks library. We decided that the only
exception to that would be a helpers package containing wrappers that enhance the underlying components with
platform and Play framework aware functionality.

One common enhancement has been to populate the language parameter found on many of hmrc-frontend's
components with the language associated with the Messages object in implicit scope. Requiring consuming
services to manually provide this parameter is a form of repetitious boilerplate we are actively
aiming to minimise.

In PLATUI-531, a story to develop a version of govukCharacterCount supporting the Welsh
language, we faced the need to create and maintain a wrapper for hmrcCharacterCount whose sole purpose would be to supply
this parameter. Given [ADR-0003](0003-use-the-suffix-helpers-for-helper-components.md), this wrapper would have to be named
hmrcCharacterCountHelper, a rather non-intuitive name bearing in mind we would generally be expecting 
teams to use this component rather than the more appropriately named hmrcCharacterCount.

Should we therefore refine ADR-0001 by allowing components to be Play I18N aware and take into consideration,
the language of any implicit Messages object?

## Decision Drivers

* the need for component naming to be unsurprising and intuitive
* the need for components to justify their cost of maintenance
* play-frontend-hmrc should be Play-framework aware and not require manual wiring of parameters
that can be found in implicit scope 
* the importance of maintaining our test strategy and for it not to be undermined.

## Considered Options

* Allow components to take an implicit Messages object and used to populate the language parameter
* Do nothing

## Decision Outcome

Chosen option: "Allow components to take an implicit Messages object", because we can do this without
undermining our test strategy and doing so will improve the developer experience.

### Positive Consequences

* There would be need to create and maintain a separate wrapper for hmrcCharacterCount and
future similar components.
* Naming clashes requiring the use of the `Helper` component name suffix minimised

### Negative Consequences

* hmrcCharacterCount would only be usable with a Messages object in implicit scope
* hmrcCharacterCount would have functionality not present in the underlying Nunjucks component, 
requiring additional tests

## Links

* Refines [ADR-0001](0001-play-frontend-hmrc-mirrors-hmrc-frontend.md)
* Refines [ADR-0003](0003-use-the-suffix-helpers-for-helper-components.md)
