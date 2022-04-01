describe('post related', () => {

  beforeEach(() => {
    cy.login('melanie', 'password')
  })

  it('go to the forum page', () => {
    // cy.login('dylan', 'password')
    cy.get('ul.nav li a').contains('Forum').click()
    cy.url().should('include', 'forum')
    cy.get('app-post-tile').should('exist')
    cy.get('app-option-menu').should('exist')
    cy.get('app-top-group-listing').should('exist')
  })

  it('go to a post from the forum homepage, and leave a comment', () => {
    // cy.login('melanie', 'password')
    cy.get('ul.nav li a').contains('Forum').click()
    cy.get('app-post-tile .post-title').then(($li) => {
      const items = $li.toArray()
      return Cypress._.sample(items)
    }).click()
    const comment = 'The comment of the test is here ' + new Date().toString()
    cy.get('p-editor[formControlName="text"]').type(comment)
    cy.get('button').contains('Commenter').click().then(button => {
      cy.contains(comment).should('exist')
    })
  })
})
