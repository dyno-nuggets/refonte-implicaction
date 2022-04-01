describe('profile related', () => {

  beforeEach(() => {
    cy.login('mathusha', 'password')
  })

  it('go to the current profile', () => {
    // cy.login('dylan', 'password')
    cy.get('#dropdownUser2').click()
    cy.get('#headerProfileBtn').click()
    cy.url().should('include', 'users/5/profile')
  })

  it('go to a user profile via community', () => {
    // cy.login('dylan', 'password')
    cy.get('ul.nav li a').contains('Communauté').click()
    cy.get('span.link-profile a').then(($li) => {
      const items = $li.toArray()
      return Cypress._.sample(items)
    }).click()
    cy.url().should('include', 'profile')
  })

  it('try to found the admin tab in the nav bar with an user profile but cannot find it', () => {
    // cy.login('mathusha', 'password')
    cy.get('ul.nav li a').contains('Admin').should('not.exist')
  })

  it('try to edit other user profiles with the current profile with no success', () => {
    // cy.login('mathusha', 'password')
    cy.get('ul.nav li a').contains('Communauté').click()
    cy.get('span.link-profile a').then(($li) => {
      const items = $li.toArray()
      return Cypress._.sample(items)
    }).click()
    cy.get('.card-options').should('exist')
  })
})
