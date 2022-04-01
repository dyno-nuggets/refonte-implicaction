describe('Connect to the application', () => {
  it('log out', () => {
    cy.login('dylan', 'password').then(el => {
      cy.get('#dropdownUser2').click()
      cy.get('.dropdown-user').contains('Se déconnecter').click()
    })
  })

  it('try to log in with the wrong password', () => {
    cy.login('melanie', 'pass', 'not.exist')
    cy.get('app-alert p').contains('Nom d\'utilisateur ou mot de passe incorrect.').should('exist')
  })

  it('log in with admin user', () => {
    cy.login('admin', 'password')
  })

})
