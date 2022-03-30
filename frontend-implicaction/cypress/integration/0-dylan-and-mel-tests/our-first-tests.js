// Cypress.Commands.add('login', (username, password) => {
//   cy.session([username, password], () => {
//     cy.visit('/auth/login')
//     cy.get('#floatingUsername').type(username)
//     cy.get('#floatingPassword').type(password)
//     cy.get('#floatingConnection').click()
//     cy.get('#dropdownUser2').should('exist')
//   })
// })


describe('"Tout voir" posts button in homepage', () => {
  it('Go to the "Forum" page', () => {
    cy.login('dylan', 'password')
    cy.get('.d-flex.align-items-start.justify-content-between.mb-3').should('have.css', 'color', 'rgb(118, 133, 145)')
    cy.get('#home-all-posts-btn').click()
    cy.url().should('include', 'forum')
  })
})
