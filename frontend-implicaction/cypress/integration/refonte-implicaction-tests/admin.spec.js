describe('admin page related', () => {
  it('try to found the Job Board tab in the nav bar but should not be able to', () => {
    cy.login('admin', 'password')
    cy.get('ul.nav li a').contains('Job Board').should('not.exist')
  })
})
