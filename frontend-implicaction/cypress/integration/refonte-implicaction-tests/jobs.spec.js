describe('jobs related', () => {
  beforeEach(() => {
    cy.login('dylan', 'password')
  })

  it('Go to a job via job ads', () => {
    // cy.login('dylan', 'password')
    cy.get('ul.nav li a').contains('Offres d\'emploi').click()
    cy.get('div.job-infos a').invoke('attr', 'href').as('jobAdHref')
    cy.get('@jobAdHref').then(href => {
      cy.log(`href : ${href}`)
      cy.get('div.job-infos:first a').click()
      cy.url().should('include', href)
    })
  })

  it('add a job to the job board', () => {
    // cy.login('dylan', 'password')
    cy.get('ul.nav li a').contains('Offres d\'emploi').click()
    cy.get('div.job-options button').first().as('addToJobBoardBtn')
    cy.get('@addToJobBoardBtn').invoke('attr', 'id').as('jobAdButtonId')
    cy.get('@jobAdButtonId').then(buttonId => {
      let buttonIdStr = buttonId.toString()
      let id = buttonIdStr.slice(buttonIdStr.lastIndexOf('-') + 1)
      cy.get('@addToJobBoardBtn').click()
      cy.get('ul.nav li a').contains('Job Board').click()
      cy.get(`#applyCard-${id}`).should('exist')
    })
  })

  it('cancel a job application from the Job Board', () => {
    // cy.login('dylan', 'password')
    cy.get('ul.nav li a').contains('Job Board').click()
    cy.get('app-card-menu button').then(($li) => {
      const items = $li.toArray()
      return Cypress._.sample(items)
    }).click()
    cy.get('app-card-menu ul.dropdown-menu a').contains('Annuler la candidature').click()
      .then(element => {
        cy.get('app-card-menu ul.dropdown-menu a').contains('Annuler la candidature').should('not.exist')
      })
  })

  it('filter the job ads by "CDI"', () => {
    // cy.login('dylan', 'password')
    cy.get('ul.nav li a').contains('Offres d\'emploi').click()
    cy.get('#CDIRadio').check()
    cy.url().should('include', 'CDI')
    cy.get('app-job-card').then(element => {
      let jobAdsCount = element.length
      cy.get('app-contract-type span')
        .each(($el, index, $list) => {
          return $el.html() === 'CDI'
        })
        .then($contractTypes => {
          expect($contractTypes).to.have.length(jobAdsCount)
        })
    })
  })
})

