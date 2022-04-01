// Cypress.Commands.add('login', (username, password) => {
//   cy.session([username, password], () => {
//     cy.visit('/auth/login')
//     cy.get('#floatingUsername').type(username)
//     cy.get('#floatingPassword').type(password)
//     cy.get('#floatingConnection').click()
//     cy.get('#dropdownUser2').should('exist')
//   })
// })

//
// describe('"Click on the Tout voir" posts button in homepage', () => {
//   it('Go to the "Forum" page', () => {
//     cy.login('dylan', 'password')
//     // cy.get('.d-flex.align-items-start.justify-content-between.mb-3').should('have.css', 'color', 'rgb(118, 133, 145)')
//     cy.get('#home-all-posts-btn').click()
//     cy.url().should('include', 'forum')
//   })
// })
//
// describe('Log in with admin user', () => {
//   it('We should log in with admin user', () => {
//     cy.login('admin', 'password')
//   })
// })
//
// describe('Access to the current profile', () => {
//   it('We should access to the current profile', () => {
//     cy.login('dylan', 'password')
//     cy.get('#dropdownUser2').click()
//     cy.get('#headerProfileBtn').click()
//     cy.url().should('include', 'users/2/profile')
//   })
// })
//
// describe('Access to a user profile via community', () => {
//   it('We should access to a user profile via community', () => {
//     cy.login('dylan', 'password')
//     cy.get('ul.nav li a').contains('Communauté').click()
//     cy.get('span.link-profile:first a').click()
//     cy.url().should('include', 'profile')
//   })
// })
//
// describe('Access to a job via job ads', () => {
//   it('We should access to a job via job ads', () => {
//     cy.login('dylan', 'password')
//     cy.get('ul.nav li a').contains('Offres d\'emploi').click()
//     cy.get('div.job-infos:first a').invoke('attr', 'href').as('jobAdHref')
//     cy.get('@jobAdHref').then(href => {
//       cy.log(`href : ${href}`)
//       cy.get('div.job-infos:first a').click()
//       cy.url().should('include', href)
//     })
//   })
// })

// describe('Add a job to the job board', () => {
//   it('We should be able to add a job via job ads and see it available in the Job Board', () => {
//     cy.login('dylan', 'password')
//     cy.get('ul.nav li a').contains('Offres d\'emploi').click()
//     cy.get('div.job-options button').first().as('addToJobBoardBtn')
//     cy.get('@addToJobBoardBtn').invoke('attr', 'id').as('jobAdButtonId')
//     cy.get('@jobAdButtonId').then(buttonId => {
//       let buttonIdStr = buttonId.toString()
//       let id = buttonIdStr.slice(buttonIdStr.lastIndexOf('-') + 1)
//       cy.get('@addToJobBoardBtn').click()
//       cy.get('ul.nav li a').contains('Job Board').click()
//       cy.get(`#applyCard-${id}`).should('exist')
//     })
//   })
// })

// describe('Filter the job ads by "CDI"', () => {
//   it('We should be able to filter the job ads and only see the corresponding jobs', () => {
//     cy.login('dylan', 'password')
//     cy.get('ul.nav li a').contains('Offres d\'emploi').click()
//     cy.get('#CDIRadio').check()
//     cy.url().should('include', 'CDI')
//     cy.get('app-job-card').then(element => {
//       let jobAdsCount = element.length
//       cy.get('app-contract-type span')
//         .each(($el, index, $list) => {
//           return $el.html() === 'CDI'
//         })
//         .then($contractTypes => {
//           expect($contractTypes).to.have.length(jobAdsCount)
//         })
//     })
//   })
// })

// describe('Access to the forum', () => {
//   it('We should be able to access to the forum page', () => {
//     cy.login('dylan', 'password')
//     cy.get('ul.nav li a').contains('Forum').click()
//     cy.url().should('include', 'forum')
//     cy.get('app-post-tile').should('exist')
//     cy.get('app-option-menu').should('exist')
//     cy.get('app-top-group-listing').should('exist')
//   })
// })

describe('Leave a comment on a post', () => {
  it('We should be able to access a post from the forum homepage, and leave a comment', () => {
    cy.login('melanie', 'password')
    cy.get('ul.nav li a').contains('Forum').click()
    cy.get('app-post-tile a').first().click()
    const comment = 'The comment of the test is here ' + new Date().toString()
    // cy.get('.ql-editor').type(comment)
    cy.get('p-editor[formControlName="text"]').type(comment)
    cy.get('button').contains('Commenter').click()
    cy.get('app-comment-tile p p').each(($el, index, $list) => {
      cy.log($el.html())
      return $el.html() === comment
    }).then()
  })
})
