/// <reference types="cypress" />

context('Connection', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200')
  })
  it('cy.go() - Testing navbar', () => {
    cy.get('.navbar-expand-lg').contains('Se connecter').click()
    cy.get('.navbar-expand-lg').contains('S\'inscrire').click()
  })

  it('cy.reload() - reload the page', () => {
    // https://on.cypress.io/reload
    cy.reload()

    // reload the page without using the cache
    cy.reload(true)
  })

  it('cy.inscription() - go to inscription form', () => {
    cy.get('.navbar-expand-lg').contains('S\'inscrire').click()
    cy.get('#floatingUsername').type('test')
    cy.get('#floatingEmail').type('test@test.com')
    cy.get('.d-grid > .btn').click()
    cy.get('#floatingPassword').type('Azerty00')
    cy.get('#floatingConfirmPassword').type('Azerty00')
    cy.get('#floatingFirstname').type('testtt')
    cy.get('#floatingLastname').type('tesdfsdfttt')
    cy.get('.d-grid > .btn').click()
  })

  it('cy.connect() - test login and logout', () => {
    cy.get('.navbar-expand-lg').contains('Se connecter').click()
    cy.get('#floatingUsername').type('admin')
    cy.get('#floatingPassword').type('password')
    cy.get('.d-grid > .btn').click()
    cy.get('#dropdownUser2').click()
    cy.get(':nth-child(7) > .dropdown-item').click()
  })
})

context('Navigation', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200')
    cy.get('.navbar-expand-lg').contains('Se connecter').click()
    cy.get('#floatingUsername').type('admin')
    cy.get('#floatingPassword').type('password')
    cy.get('.d-grid > .btn').click()
  })

  it('cy.entreprise() - testing Espace entreprise', () => {
    cy.get('.navbar-expand-lg').contains('Espace entreprise').click()
    cy.get(':nth-child(2) > :nth-child(1) > :nth-child(4)').contains('recrutement@implicaction.eu')

  })

  it('cy.navbar() - Testing navbar when connected', () => {
    cy.get('.navbar-expand-lg > .d-none').should(($p) => {
      // make sure the first contains some text content
      expect($p.first()).to.contain('Accueil')
      expect($p.first()).to.contain('Espace entreprise')
      expect($p.first()).to.contain('Communauté')
      expect($p.first()).to.contain('Offres d\'emploi')
      expect($p.first()).to.contain('Forum')
      expect($p.first()).to.contain('Admin')

    })
  })

  it('cy.community() - Testing Communauté', () => {
    cy.get('.navbar-expand-lg').contains('Communauté').click()
    cy.get(':nth-child(1) > .card > .card-body > .d-flex > .btn').click()
    cy.get(':nth-child(1) > .card > .card-body > .h5 > .link-profile > .text-decoration-none').click()
    cy.get('.sidebar-profile ')
    cy.get('app-experience-list > .card')
    cy.get('app-training-list > .card')
  })

  // it('cy.addOffer() - Add offer', () => {
  //   cy.get('.navbar-expand-lg').contains('Offres d\'emploi').click()
  //   cy.get('button.btn.btn-primary.fw-bold.w-100.px-xxl-4').click()
  //   cy.get('#titleInput').type('Une offre')
  //   cy.get('#locationInput').type('Un endroit')
  //   cy.get('#salaryInput').type('10000')
  //   cy.get(':nth-child(2) > .row > .col-12 > .ng-pristine > .w-100').click().find('ul li > span').contains('CDI').check()
  //   // cy.get('span.ng-star-inserted').contains('CDI').click()
  //   // cy.get('.form-floating.col-12 > .row > .col-12 > .ng-pristine > .w-100').click()
  //   // cy.get('[ng-reflect-label="Agroalimentaire"] > .p-dropdown-item').click()
  //   // cy.get(':nth-child(5) > .ng-pristine > .w-100').click()
  //   // cy.get('[ng-reflect-label="Amazon"] > .p-dropdown-item').click()
  //   // cy.get('.my-2 > .ng-untouched > .p-editor-container > .p-editor-content > .ql-editor').type('C\'est une description courte')
  //   // cy.get(':nth-child(7) > .ng-untouched > .p-editor-container > .p-editor-content > .ql-editor').type('C\'est une description pas courte')
  //   // cy.get('button.btn.btn-primary.w-50').click()
  // })

  it('cy.offers() - Testing filters', () => {
    cy.get('.navbar-expand-lg').contains('Offres d\'emploi').click()
    cy.get('#CDIRadio').click()
    cy.get('#CDDRadio').click()
    cy.get('#INTERIMRadio').click()
    cy.get('#ALTERNRadio').click()
  })

  it('cy.admin() - Testing admin page', () => {
    cy.get('.navbar-expand-lg').contains('Admin').click()
    cy.get('[ng-reflect-router-link="/admin/users"]').click()
    cy.get('[ng-reflect-router-link="/admin/jobs"]').click()
    cy.get('[ng-reflect-router-link="/admin/companies"]').click()
    cy.get('button.btn.btn-success.fw-bold').click()
    cy.get('form.ng-untouched > :nth-child(1) > #nameInput').type('Gologole')
    cy.get('#logoInput').type('Deh')
    cy.get('#urlInput').type('https://gologole.fr')
    cy.get('.ql-editor').type('Desc de l\'entreprise')
    cy.get('button.btn.btn-primary.w-50').click()
    cy.get('#nameInput').type('Gologole{enter}')
  })
})
